package com.loancheck.model;

import com.loancheck.model.EmploymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PersonalDetails {

    @NotBlank
    private String fullName;

    @NotNull
    @Min(18)
    private Integer age;

    @NotNull
    private EmploymentStatus employmentStatus;

    private Boolean hasExistingCredit;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Boolean getHasExistingCredit() {
        return hasExistingCredit;
    }

    public void setHasExistingCredit(Boolean hasExistingCredit) {
        this.hasExistingCredit = hasExistingCredit;
    }
}
