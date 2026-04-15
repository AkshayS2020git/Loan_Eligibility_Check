package com.loancheck.service;

import com.loancheck.model.EligibilityResult;
import com.loancheck.model.EmploymentStatus;
import com.loancheck.model.LoanFormData;
import com.loancheck.model.LoanRiskBreakdown;
import com.loancheck.model.LoanSummary;
import com.loancheck.model.LoanType;
import com.loancheck.model.LoanTypeInfo;
import com.loancheck.model.RiskFactor;
import com.loancheck.model.EligibilityStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityService {

    private static final Map<LoanType, Double> BASE_INTEREST_RATES = Map.of(
            LoanType.HOME, 8.5,
            LoanType.CAR, 10.5,
            LoanType.EDUCATION, 9.0,
            LoanType.PERSONAL, 13.0
    );

    private static final Map<LoanType, Integer> MAX_LOAN_MULTIPLIERS = Map.of(
            LoanType.HOME, 40,
            LoanType.CAR, 20,
            LoanType.EDUCATION, 15,
            LoanType.PERSONAL, 10
    );

    public List<LoanTypeInfo> getLoanTypes() {
        List<LoanTypeInfo> loanTypes = new ArrayList<>();
        loanTypes.add(new LoanTypeInfo(LoanType.HOME, "Home Loan", 8.5, 40));
        loanTypes.add(new LoanTypeInfo(LoanType.CAR, "Car Loan", 10.5, 20));
        loanTypes.add(new LoanTypeInfo(LoanType.EDUCATION, "Education Loan", 9.0, 15));
        loanTypes.add(new LoanTypeInfo(LoanType.PERSONAL, "Personal Loan", 13.0, 10));
        return loanTypes;
    }

    public EligibilityResult checkEligibility(LoanFormData formData) {
        int age = formData.getPersonal().getAge();
        EmploymentStatus employmentStatus = formData.getPersonal().getEmploymentStatus();
        double monthlyIncome = formData.getFinancial().getMonthlyIncome().doubleValue();
        double monthlyDebt = formData.getFinancial().getExistingMonthlyDebt().doubleValue();
        double creditScore = formData.getFinancial().getCreditScore().doubleValue();
        LoanType loanType = formData.getLoan().getLoanType();
        double loanAmountRequested = formData.getLoan().getLoanAmountRequested().doubleValue();
        double loanTenure = formData.getLoan().getLoanTenure().doubleValue();

        List<String> rejectionReasons = new ArrayList<>();
        if (age < 18 || age > 65) {
            rejectionReasons.add("Age must be between 18 and 65 years.");
        }
        if (employmentStatus == EmploymentStatus.UNEMPLOYED) {
            rejectionReasons.add("Must be employed or self-employed to qualify.");
        }
        if (creditScore < 600) {
            rejectionReasons.add(String.format("Credit score of %.0f is below the minimum required 600.", creditScore));
        }
        double dtiRatio = monthlyIncome > 0 ? monthlyDebt / monthlyIncome : 1.0;
        if (dtiRatio > 0.5) {
            rejectionReasons.add(String.format("Debt-to-income ratio of %.0f%% exceeds the 50%% limit.", dtiRatio * 100));
        }
        if (monthlyIncome < 15000) {
            rejectionReasons.add(String.format("Monthly income of ₹%.0f is below the minimum ₹15,000.", monthlyIncome));
        }

        LoanRiskBreakdown riskBreakdown = calculateRiskBreakdown(creditScore, monthlyIncome, monthlyDebt, age, employmentStatus);

        if (!rejectionReasons.isEmpty()) {
            return new EligibilityResult(EligibilityStatus.REJECTED, rejectionReasons, riskBreakdown, null, formData);
        }

        double maxByMultiplier = monthlyIncome * MAX_LOAN_MULTIPLIERS.getOrDefault(loanType, 0);
        double maxEligibleAmount = Math.min(maxByMultiplier, loanAmountRequested);
        double annualRate = getAdjustedInterestRate(loanType, creditScore);
        int tenureMonths = (int) Math.round(loanTenure * 12);
        double emi = calculateEMI(maxEligibleAmount, annualRate, tenureMonths);
        double totalRepayment = emi * tenureMonths;
        double totalInterest = totalRepayment - maxEligibleAmount;

        LoanSummary loanSummary = new LoanSummary(maxEligibleAmount, emi, totalInterest, totalRepayment, annualRate, tenureMonths);

        return new EligibilityResult(EligibilityStatus.APPROVED, Collections.emptyList(), riskBreakdown, loanSummary, formData);
    }

    private double getAdjustedInterestRate(LoanType loanType, double creditScore) {
        double base = BASE_INTEREST_RATES.getOrDefault(loanType, 0.0);
        if (creditScore >= 750) {
            return base - 1;
        }
        if (creditScore >= 700) {
            return base;
        }
        if (creditScore >= 650) {
            return base + 1;
        }
        return base + 2;
    }

    private double calculateEMI(double principal, double annualRate, int tenureMonths) {
        if (principal <= 0 || tenureMonths <= 0) {
            return 0.0;
        }
        double r = annualRate / 100.0 / 12.0;
        if (r == 0) {
            return principal / tenureMonths;
        }
        double pow = Math.pow(1 + r, tenureMonths);
        return (principal * r * pow) / (pow - 1);
    }

    private LoanRiskBreakdown calculateRiskBreakdown(double creditScore, double monthlyIncome, double monthlyDebt, double age, EmploymentStatus employmentStatus) {
        double creditContrib = ((900 - creditScore) / 600.0) * 40.0;
        double dtiRatio = monthlyIncome > 0 ? monthlyDebt / monthlyIncome : 1.0;
        double dtiContrib = Math.min(dtiRatio * 100.0 * 0.3, 30.0);

        double ageContrib = 0.0;
        if (age < 25) {
            ageContrib = ((25 - age) / 7.0) * 15.0;
        } else if (age > 60) {
            ageContrib = ((age - 60) / 5.0) * 15.0;
        }
        ageContrib = Math.min(ageContrib, 15.0);

        double employmentContrib;
        switch (employmentStatus) {
            case EMPLOYED:
                employmentContrib = 0.0;
                break;
            case SELF_EMPLOYED:
                employmentContrib = 7.5;
                break;
            default:
                employmentContrib = 15.0;
                break;
        }

        double totalRiskScore = Math.min(100.0, Math.max(0.0, creditContrib + dtiContrib + ageContrib + employmentContrib));

        return new LoanRiskBreakdown(
                new RiskFactor("Credit Score", 40, (int) Math.round(creditScore), (int) Math.round(creditContrib), describeCreditScore(creditScore)),
                new RiskFactor("Debt-to-Income Ratio", 30, (int) Math.round(dtiRatio * 100.0), (int) Math.round(dtiContrib), Math.round(dtiRatio * 100.0) + "% of income in debt"),
                new RiskFactor("Age Factor", 15, (int) Math.round(age), (int) Math.round(ageContrib), age < 25 ? "Below optimal lending age" : age > 60 ? "Above optimal lending age" : "Within prime lending age"),
                new RiskFactor("Employment Status", 15, employmentStatus == EmploymentStatus.EMPLOYED ? 0 : employmentStatus == EmploymentStatus.SELF_EMPLOYED ? 50 : 100, (int) Math.round(employmentContrib), employmentStatus == EmploymentStatus.EMPLOYED ? "Salaried employment — lowest risk" : employmentStatus == EmploymentStatus.SELF_EMPLOYED ? "Self-employed — moderate risk" : "Unemployed — highest risk"),
                (int) Math.round(totalRiskScore)
        );
    }

    private String describeCreditScore(double creditScore) {
        if (creditScore >= 750) {
            return String.format("Score of %.0f (Excellent)", creditScore);
        }
        if (creditScore >= 700) {
            return String.format("Score of %.0f (Good)", creditScore);
        }
        if (creditScore >= 650) {
            return String.format("Score of %.0f (Fair)", creditScore);
        }
        return String.format("Score of %.0f (Poor)", creditScore);
    }
}
