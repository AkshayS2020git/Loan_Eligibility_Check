package com.loancheck.model;

import java.util.List;

public class EligibilityResult {

    private EligibilityStatus status;
    private List<String> rejectionReasons;
    private LoanRiskBreakdown riskBreakdown;
    private LoanSummary loanSummary;
    private LoanFormData formData;

    public EligibilityResult() {
    }

    public EligibilityResult(EligibilityStatus status, List<String> rejectionReasons, LoanRiskBreakdown riskBreakdown, LoanSummary loanSummary, LoanFormData formData) {
        this.status = status;
        this.rejectionReasons = rejectionReasons;
        this.riskBreakdown = riskBreakdown;
        this.loanSummary = loanSummary;
        this.formData = formData;
    }

    public EligibilityStatus getStatus() {
        return status;
    }

    public void setStatus(EligibilityStatus status) {
        this.status = status;
    }

    public List<String> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<String> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public LoanRiskBreakdown getRiskBreakdown() {
        return riskBreakdown;
    }

    public void setRiskBreakdown(LoanRiskBreakdown riskBreakdown) {
        this.riskBreakdown = riskBreakdown;
    }

    public LoanSummary getLoanSummary() {
        return loanSummary;
    }

    public void setLoanSummary(LoanSummary loanSummary) {
        this.loanSummary = loanSummary;
    }

    public LoanFormData getFormData() {
        return formData;
    }

    public void setFormData(LoanFormData formData) {
        this.formData = formData;
    }
}
