package com.loancheck.controller;

import com.loancheck.model.EmploymentStatus;
import com.loancheck.model.FinancialDetails;
import com.loancheck.model.LoanDetails;
import com.loancheck.model.LoanFormData;
import com.loancheck.model.LoanType;
import com.loancheck.model.PersonalDetails;
import com.loancheck.model.EligibilityResult;
import com.loancheck.service.LoanEligibilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    private final LoanEligibilityService eligibilityService;

    public WebController(LoanEligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("loanTypes", eligibilityService.getLoanTypes());
        return "index";
    }

    @GetMapping("/apply")
    public String apply(Model model) {
        model.addAttribute("loanTypeOptions", LoanType.values());
        model.addAttribute("employmentStatusOptions", EmploymentStatus.values());
        return "form";
    }

    @PostMapping("/apply")
    public String submitApplication(@ModelAttribute("applicationForm") LoanFormData applicationForm, Model model) {
        EligibilityResult result = eligibilityService.checkEligibility(applicationForm);
        model.addAttribute("result", result);
        return "results";
    }

    @ModelAttribute("applicationForm")
    public LoanFormData applicationForm() {
        LoanFormData formData = new LoanFormData();
        PersonalDetails personal = new PersonalDetails();
        FinancialDetails financial = new FinancialDetails();
        LoanDetails loan = new LoanDetails();

        financial.setMonthlyIncome(null);
        financial.setExistingMonthlyDebt(null);
        financial.setCreditScore(null);
        financial.setMonthlyExpenses(java.math.BigDecimal.ZERO);

        loan.setLoanAmountRequested(null);
        loan.setLoanType(null);
        loan.setLoanTenure(null);
        loan.setRequestedAmount(java.math.BigDecimal.ZERO);
        loan.setTenureMonths(0);
        loan.setAnnualInterestRate(java.math.BigDecimal.ZERO);

        formData.setPersonal(personal);
        formData.setFinancial(financial);
        formData.setLoan(loan);
        return formData;
    }
}
