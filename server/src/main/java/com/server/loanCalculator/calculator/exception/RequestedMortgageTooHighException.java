package com.server.loanCalculator.calculator.exception;

public class RequestedMortgageTooHighException extends RuntimeException{

    public RequestedMortgageTooHighException(String message) {
        super(message);
    }
}
