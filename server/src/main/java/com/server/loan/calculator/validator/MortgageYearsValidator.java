package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.ValidationException;
import com.server.loan.calculator.exception.ValueOutOfRangeException;
import org.springframework.stereotype.Component;

@Component
public class MortgageYearsValidator {
    public void validate (Integer years) {
        if (years == null || years.compareTo(0) <= 0) {
            throw new ValidationException("Received mortgage year value is negative, zero or not a number");
        }
        if (years.compareTo(1) < 0 || years.compareTo(30) > 0) {
            throw new ValueOutOfRangeException("Received mortgage year value not between 1 and 30");
        }
    }
}
