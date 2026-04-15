package com.loancheck.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LoanDetails {

    @NotNull
    private LoanType loanType;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal loanAmountRequested;

    @NotNull
    @Min(1)
    private Integer loanTenure;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal requestedAmount;

    @NotNull
    @Min(1)
    private Integer tenureMonths;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal annualInterestRate;

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getLoanAmountRequested() {
        return loanAmountRequested;
    }

    public void setLoanAmountRequested(BigDecimal loanAmountRequested) {
        this.loanAmountRequested = loanAmountRequested;
    }

    public Integer getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(Integer loanTenure) {
        this.loanTenure = loanTenure;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
}
