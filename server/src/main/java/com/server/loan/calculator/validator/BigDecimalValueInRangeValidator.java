package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.ValidationException;

import java.math.BigDecimal;

public class BigDecimalValueInRangeValidator {
    public void validate (BigDecimal currentValue, BigDecimal minValue, BigDecimal maxValue) {
        if (currentValue.compareTo(maxValue) > 0 || currentValue.compareTo(minValue) < 0) {
            throw new ValidationException("Value not within set parameters");
        }
    }
}
