package com.loancheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum LoanType {
    HOME("home"),
    CAR("car"),
    EDUCATION("education"),
    PERSONAL("personal");

    private static final Map<String, LoanType> NAME_MAP = new HashMap<>();

    static {
        for (LoanType value : values()) {
            NAME_MAP.put(value.value, value);
        }
    }

    private final String value;

    LoanType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static LoanType fromValue(String value) {
        if (value == null) {
            return null;
        }
        return NAME_MAP.get(value.toLowerCase());
    }
}
