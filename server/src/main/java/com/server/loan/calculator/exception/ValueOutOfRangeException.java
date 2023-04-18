package com.server.loan.calculator.exception;

public class ValueOutOfRangeException extends RuntimeException {

    public ValueOutOfRangeException (String message) {

        super(message);
    }
}