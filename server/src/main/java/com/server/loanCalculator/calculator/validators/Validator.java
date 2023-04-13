package com.server.loanCalculator.calculator.validators;

import org.springframework.stereotype.Component;

@Component
public abstract class Validator <T>{

    public abstract void validate (T attribute);
}
