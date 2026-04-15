package com.loancheck.model;

public class LoanRiskBreakdown {

    private RiskFactor creditScoreFactor;
    private RiskFactor debtToIncomeFactor;
    private RiskFactor ageFactor;
    private RiskFactor employmentFactor;
    private int totalRiskScore;

    public LoanRiskBreakdown() {
    }

    public LoanRiskBreakdown(RiskFactor creditScoreFactor, RiskFactor debtToIncomeFactor, RiskFactor ageFactor, RiskFactor employmentFactor, int totalRiskScore) {
        this.creditScoreFactor = creditScoreFactor;
        this.debtToIncomeFactor = debtToIncomeFactor;
        this.ageFactor = ageFactor;
        this.employmentFactor = employmentFactor;
        this.totalRiskScore = totalRiskScore;
    }

    public RiskFactor getCreditScoreFactor() {
        return creditScoreFactor;
    }

    public void setCreditScoreFactor(RiskFactor creditScoreFactor) {
        this.creditScoreFactor = creditScoreFactor;
    }

    public RiskFactor getDebtToIncomeFactor() {
        return debtToIncomeFactor;
    }

    public void setDebtToIncomeFactor(RiskFactor debtToIncomeFactor) {
        this.debtToIncomeFactor = debtToIncomeFactor;
    }

    public RiskFactor getAgeFactor() {
        return ageFactor;
    }

    public void setAgeFactor(RiskFactor ageFactor) {
        this.ageFactor = ageFactor;
    }

    public RiskFactor getEmploymentFactor() {
        return employmentFactor;
    }

    public void setEmploymentFactor(RiskFactor employmentFactor) {
        this.employmentFactor = employmentFactor;
    }

    public int getTotalRiskScore() {
        return totalRiskScore;
    }

    public void setTotalRiskScore(int totalRiskScore) {
        this.totalRiskScore = totalRiskScore;
    }
}
