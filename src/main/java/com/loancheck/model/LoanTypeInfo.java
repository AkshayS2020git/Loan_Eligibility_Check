package com.loancheck.model;

public class LoanTypeInfo {

    private LoanType loanType;
    private String label;
    private double interestRate;
    private int maxMultiplier;

    public LoanTypeInfo() {
    }

    public LoanTypeInfo(LoanType loanType, String label, double interestRate, int maxMultiplier) {
        this.loanType = loanType;
        this.label = label;
        this.interestRate = interestRate;
        this.maxMultiplier = maxMultiplier;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getMaxMultiplier() {
        return maxMultiplier;
    }

    public void setMaxMultiplier(int maxMultiplier) {
        this.maxMultiplier = maxMultiplier;
    }
}
