package com.server.loanCalculator.calculator.validators;

import com.server.loanCalculator.calculator.exception.ValidationException;


public class PropertyPriceInRangeValidator extends Validator <Double>{
    @Override
    public void validate(Double propertyPrice) {
        if(propertyPrice > 800000 || propertyPrice < 20000){
            throw new ValidationException("Property price must be between 20.000 and 800.000 Eur");
        }
    }
}
