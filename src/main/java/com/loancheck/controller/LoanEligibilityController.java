package com.loancheck.controller;

import com.loancheck.model.EligibilityResult;
import com.loancheck.model.LoanFormData;
import com.loancheck.model.LoanTypeInfo;
import com.loancheck.service.LoanEligibilityService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoanEligibilityController {

    private final LoanEligibilityService eligibilityService;

    public LoanEligibilityController(LoanEligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    @GetMapping(value = "/loan-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LoanTypeInfo> getLoanTypes() {
        return eligibilityService.getLoanTypes();
    }

    @PostMapping(value = "/eligibility", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EligibilityResult evaluateEligibility(@RequestBody LoanFormData formData) {
        return eligibilityService.checkEligibility(formData);
    }
}
