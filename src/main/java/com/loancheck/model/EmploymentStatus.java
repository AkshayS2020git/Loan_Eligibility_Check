package com.loancheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum EmploymentStatus {
    EMPLOYED("employed"),
    SELF_EMPLOYED("self_employed"),
    UNEMPLOYED("unemployed");

    private static final Map<String, EmploymentStatus> LOOKUP = new HashMap<>();

    static {
        for (EmploymentStatus status : values()) {
            LOOKUP.put(status.value, status);
        }
    }

    private final String value;

    EmploymentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EmploymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        return LOOKUP.get(value.toLowerCase());
    }
}
