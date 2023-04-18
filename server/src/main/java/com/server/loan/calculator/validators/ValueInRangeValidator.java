package com.server.loan.calculator.validators;

import com.server.loan.calculator.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValueInRangeValidator {

    public void validate (double currentValue, double minValue, double maxValue) {
        if (currentValue > maxValue || currentValue < minValue) {
            throw new ValidationException("Value not within set parameters");
        }
    }
}
