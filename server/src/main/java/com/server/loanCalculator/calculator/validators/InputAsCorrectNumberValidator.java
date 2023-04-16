package com.server.loanCalculator.calculator.validators;

import com.server.loanCalculator.calculator.exception.ValidationException;

public class InputAsCorrectNumberValidator extends Validator<Double> {

    @Override
    public void validate (Double value) {
        if (value.isNaN() || value <= 0) {
            throw new ValidationException("Received value is negative, zero or not a number");
        }
    }
}