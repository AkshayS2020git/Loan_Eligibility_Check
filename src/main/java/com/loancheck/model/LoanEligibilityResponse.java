package com.loancheck.model;

import java.math.BigDecimal;
import java.util.List;

public class LoanEligibilityResponse {

    private boolean eligible;
    private BigDecimal eligibleAmount;
    private BigDecimal monthlyEmi;
    private int riskScore;
    private String recommendation;
    private List<RiskBreakdown> riskBreakdown;

    public LoanEligibilityResponse() {
    }

    public LoanEligibilityResponse(boolean eligible, BigDecimal eligibleAmount, BigDecimal monthlyEmi, int riskScore, String recommendation, List<RiskBreakdown> riskBreakdown) {
        this.eligible = eligible;
        this.eligibleAmount = eligibleAmount;
        this.monthlyEmi = monthlyEmi;
        this.riskScore = riskScore;
        this.recommendation = recommendation;
        this.riskBreakdown = riskBreakdown;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public BigDecimal getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(BigDecimal eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    public BigDecimal getMonthlyEmi() {
        return monthlyEmi;
    }

    public void setMonthlyEmi(BigDecimal monthlyEmi) {
        this.monthlyEmi = monthlyEmi;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public List<RiskBreakdown> getRiskBreakdown() {
        return riskBreakdown;
    }

    public void setRiskBreakdown(List<RiskBreakdown> riskBreakdown) {
        this.riskBreakdown = riskBreakdown;
    }
}
