package com.loancheck.model;

public class LoanFormData {

    private PersonalDetails personal;
    private FinancialDetails financial;
    private LoanDetails loan;

    public PersonalDetails getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalDetails personal) {
        this.personal = personal;
    }

    public FinancialDetails getFinancial() {
        return financial;
    }

    public void setFinancial(FinancialDetails financial) {
        this.financial = financial;
    }

    public LoanDetails getLoan() {
        return loan;
    }

    public void setLoan(LoanDetails loan) {
        this.loan = loan;
    }
}
