package com.server.loan.calculator.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException (String message) {
        super(message);
    }
}
