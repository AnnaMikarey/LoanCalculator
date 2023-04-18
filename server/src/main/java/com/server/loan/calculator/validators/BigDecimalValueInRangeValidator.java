package com.server.loan.calculator.validators;

import com.server.loan.calculator.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BigDecimalValueInRangeValidator {

    public void validate (BigDecimal currentValue, BigDecimal minValue, BigDecimal maxValue) {
        if (currentValue.compareTo(maxValue) > 0 || currentValue.compareTo(minValue) < 0) {
            throw new ValidationException("Value not within set parameters");
        }
    }
}
