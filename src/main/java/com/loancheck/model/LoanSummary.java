package com.loancheck.model;

public class LoanSummary {

    private double maxEligibleAmount;
    private double emi;
    private double totalInterestPayable;
    private double totalRepaymentAmount;
    private double interestRate;
    private int loanTenureMonths;

    public LoanSummary() {
    }

    public LoanSummary(double maxEligibleAmount, double emi, double totalInterestPayable, double totalRepaymentAmount, double interestRate, int loanTenureMonths) {
        this.maxEligibleAmount = maxEligibleAmount;
        this.emi = emi;
        this.totalInterestPayable = totalInterestPayable;
        this.totalRepaymentAmount = totalRepaymentAmount;
        this.interestRate = interestRate;
        this.loanTenureMonths = loanTenureMonths;
    }

    public double getMaxEligibleAmount() {
        return maxEligibleAmount;
    }

    public void setMaxEligibleAmount(double maxEligibleAmount) {
        this.maxEligibleAmount = maxEligibleAmount;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public double getTotalInterestPayable() {
        return totalInterestPayable;
    }

    public void setTotalInterestPayable(double totalInterestPayable) {
        this.totalInterestPayable = totalInterestPayable;
    }

    public double getTotalRepaymentAmount() {
        return totalRepaymentAmount;
    }

    public void setTotalRepaymentAmount(double totalRepaymentAmount) {
        this.totalRepaymentAmount = totalRepaymentAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanTenureMonths() {
        return loanTenureMonths;
    }

    public void setLoanTenureMonths(int loanTenureMonths) {
        this.loanTenureMonths = loanTenureMonths;
    }
}
