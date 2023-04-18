package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.DataNotFoundException;

import java.math.BigDecimal;

public class DataExistsValidator {
    public void validate (BigDecimal value) {
        if (value == null) {
            throw new DataNotFoundException("Requested value not found");
        }
    }
}
