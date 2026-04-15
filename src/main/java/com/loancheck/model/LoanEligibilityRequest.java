package com.loancheck.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class LoanEligibilityRequest {

    @Valid
    @NotNull
    private PersonalDetails personalDetails;

    @Valid
    @NotNull
    private FinancialDetails financialDetails;

    @Valid
    @NotNull
    private LoanDetails loanDetails;

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public FinancialDetails getFinancialDetails() {
        return financialDetails;
    }

    public void setFinancialDetails(FinancialDetails financialDetails) {
        this.financialDetails = financialDetails;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }
}
