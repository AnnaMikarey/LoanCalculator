package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BigDecimalInputAsCorrectNumberValidator {
    public void validate (BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Received value is negative, zero or not a number");
        }
    }
}
