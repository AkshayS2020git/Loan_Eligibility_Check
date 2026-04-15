package com.loancheck.controller;

import com.loancheck.model.LoanEligibilityRequest;
import com.loancheck.model.LoanEligibilityResponse;
import com.loancheck.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/check")
    public ResponseEntity<LoanEligibilityResponse> checkLoanEligibility(
            @Valid @RequestBody LoanEligibilityRequest request) {
        LoanEligibilityResponse response = loanService.evaluateEligibility(request);
        return ResponseEntity.ok(response);
    }
}
