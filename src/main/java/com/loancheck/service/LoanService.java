package com.loancheck.service;

import com.loancheck.model.FinancialDetails;
import com.loancheck.model.LoanDetails;
import com.loancheck.model.EmploymentStatus;
import com.loancheck.model.LoanEligibilityRequest;
import com.loancheck.model.LoanEligibilityResponse;
import com.loancheck.model.PersonalDetails;
import com.loancheck.model.RiskBreakdown;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    public LoanEligibilityResponse evaluateEligibility(LoanEligibilityRequest request) {
        PersonalDetails personal = request.getPersonalDetails();
        FinancialDetails financial = request.getFinancialDetails();
        LoanDetails loan = request.getLoanDetails();

        BigDecimal dtiRatio = computeDebtToIncomeRatio(financial, loan);
        BigDecimal monthlyEmi = computeEmi(loan.getRequestedAmount(), loan.getAnnualInterestRate(), loan.getTenureMonths());
        int riskScore = computeRiskScore(personal, financial, loan, dtiRatio);
        boolean eligible = determineEligibility(riskScore, dtiRatio, monthlyEmi, financial);
        BigDecimal eligibleAmount = computeEligibleAmount(financial, loan, dtiRatio, eligible);
        String recommendation = eligible ? "Approved" : "Not approved";
        List<RiskBreakdown> breakdowns = buildRiskBreakdown(personal, financial, loan, dtiRatio);

        return new LoanEligibilityResponse(
                eligible,
                eligibleAmount,
                monthlyEmi,
                riskScore,
                recommendation,
                breakdowns
        );
    }

    private BigDecimal computeDebtToIncomeRatio(FinancialDetails financial, LoanDetails loan) {
        BigDecimal monthlyEmi = computeEmi(loan.getRequestedAmount(), loan.getAnnualInterestRate(), loan.getTenureMonths());
        BigDecimal monthlyDebt = financial.getExistingMonthlyDebt().add(financial.getMonthlyExpenses()).add(monthlyEmi);
        if (financial.getMonthlyIncome().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.valueOf(100);
        }
        return monthlyDebt
                .divide(financial.getMonthlyIncome(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal computeEmi(BigDecimal principal, BigDecimal annualRate, int tenureMonths) {
        if (principal.compareTo(BigDecimal.ZERO) <= 0 || tenureMonths <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(tenureMonths), 2, RoundingMode.HALF_UP);
        }
        BigDecimal factor = monthlyRate.add(BigDecimal.ONE).pow(tenureMonths);
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(factor);
        BigDecimal denominator = factor.subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

    private int computeRiskScore(PersonalDetails personal, FinancialDetails financial, LoanDetails loan, BigDecimal dtiRatio) {
        int creditScoreComponent = computeCreditComponent(financial.getCreditScore());
        int dtiComponent = computeDtiComponent(dtiRatio);
        int ageComponent = computeAgeComponent(personal.getAge());
        int employmentComponent = computeEmploymentComponent(personal.getEmploymentStatus());
        int baseScore = creditScoreComponent + dtiComponent + ageComponent + employmentComponent;
        return Math.min(100, Math.max(0, baseScore));
    }

    private int computeCreditComponent(BigDecimal creditScore) {
        if (creditScore == null) {
            return 15;
        }
        if (creditScore.compareTo(BigDecimal.valueOf(750)) >= 0) {
            return 35;
        }
        if (creditScore.compareTo(BigDecimal.valueOf(650)) >= 0) {
            return 25;
        }
        return 10;
    }

    private int computeDtiComponent(BigDecimal dtiRatio) {
        if (dtiRatio.compareTo(BigDecimal.valueOf(35)) <= 0) {
            return 35;
        }
        if (dtiRatio.compareTo(BigDecimal.valueOf(50)) <= 0) {
            return 20;
        }
        return 5;
    }

    private int computeAgeComponent(Integer age) {
        if (age == null) {
            return 10;
        }
        if (age < 25 || age > 60) {
            return 10;
        }
        if (age <= 35) {
            return 20;
        }
        return 15;
    }

    private int computeEmploymentComponent(EmploymentStatus employmentStatus) {
        if (employmentStatus == null) {
            return 5;
        }
        switch (employmentStatus) {
            case EMPLOYED:
                return 25;
            case SELF_EMPLOYED:
                return 15;
            default:
                return 5;
        }
    }

    private boolean determineEligibility(int riskScore, BigDecimal dtiRatio, BigDecimal monthlyEmi, FinancialDetails financial) {
        if (riskScore < 55 || dtiRatio.compareTo(BigDecimal.valueOf(55)) > 0) {
            return false;
        }
        BigDecimal availableCapacity = financial.getMonthlyIncome().multiply(BigDecimal.valueOf(0.4));
        return monthlyEmi.compareTo(availableCapacity) <= 0;
    }

    private BigDecimal computeEligibleAmount(FinancialDetails financial, LoanDetails loan, BigDecimal dtiRatio, boolean eligible) {
        if (!eligible) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal maximumAffordableEmi = financial.getMonthlyIncome().multiply(BigDecimal.valueOf(0.4));
        if (maximumAffordableEmi.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal monthlyRate = loan.getAnnualInterestRate().divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal factor = BigDecimal.ONE.add(monthlyRate).pow(loan.getTenureMonths());
        BigDecimal principal = maximumAffordableEmi.multiply(factor.subtract(BigDecimal.ONE)).divide(monthlyRate.multiply(factor), 2, RoundingMode.HALF_UP);
        return principal.max(BigDecimal.ZERO);
    }

    private List<RiskBreakdown> buildRiskBreakdown(PersonalDetails personal, FinancialDetails financial, LoanDetails loan, BigDecimal dtiRatio) {
        List<RiskBreakdown> breakdown = new ArrayList<>();
        breakdown.add(new RiskBreakdown("Credit score", computeCreditComponent(financial.getCreditScore()), getBandColor(computeCreditComponent(financial.getCreditScore())), String.format("Credit score: %s", financial.getCreditScore())));
        breakdown.add(new RiskBreakdown("Debt-to-income", computeDtiComponent(dtiRatio), getBandColor(computeDtiComponent(dtiRatio)), String.format("DTI ratio: %s%%", dtiRatio.setScale(2, RoundingMode.HALF_UP))));
        breakdown.add(new RiskBreakdown("Age & stability", computeAgeComponent(personal.getAge()), getBandColor(computeAgeComponent(personal.getAge())), String.format("Age: %d", personal.getAge())));
        breakdown.add(new RiskBreakdown("Employment", computeEmploymentComponent(personal.getEmploymentStatus()), getBandColor(computeEmploymentComponent(personal.getEmploymentStatus())), String.format("Status: %s", personal.getEmploymentStatus())));
        return breakdown;
    }

    private String getBandColor(int score) {
        if (score >= 30) {
            return "emerald";
        }
        if (score >= 15) {
            return "yellow";
        }
        return "red";
    }
}
