package com.server.loanCalculator.calculator.exception;

public class ValidationException extends RuntimeException {

    public ValidationException (String message) {
        super(message);
    }
}
