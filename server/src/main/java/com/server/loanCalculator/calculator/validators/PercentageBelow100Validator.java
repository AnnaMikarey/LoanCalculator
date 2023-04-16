package com.server.loanCalculator.calculator.validators;

import com.server.loanCalculator.calculator.exception.ValidationException;

public class PercentageBelow100Validator extends Validator<Double> {

    @Override
    public void validate (Double value) {
        if (value >= 100) {
            throw new ValidationException("Received percentage cannot be 100% or above");
        }
    }
}
