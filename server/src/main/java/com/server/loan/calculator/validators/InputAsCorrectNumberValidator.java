package com.server.loan.calculator.validators;

import com.server.loan.calculator.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class InputAsCorrectNumberValidator {

    public void validate (Double value) {
        if (value.isNaN() || value <= 0) {
            throw new ValidationException("Received value is negative, zero or not a number");
        }
    }
}
