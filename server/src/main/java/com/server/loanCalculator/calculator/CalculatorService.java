package com.server.loanCalculator.calculator;

import com.server.loanCalculator.calculator.exception.RequestedMortgageTooHighException;
import com.server.loanCalculator.calculator.validators.PropertyPriceInRangeValidator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class CalculatorService {

    private double euriborInterestRate = 3.31;
    private double bankInterestMargin = 1.99;
    private double contractFee = 500;
    private double registrationFee = 10;
    private double mortgagePeriodYears =10;
    private double propertyValue = 15000;
    private double initialDeposit = 15000;
    private double totalLoan = propertyValue - initialDeposit;
    private double salary = 2000;
    private double financialObligations = 100;
    private PropertyPriceInRangeValidator propertyPriceInRangeValidator = new PropertyPriceInRangeValidator();


    private double calculateMonthlyInterestRateDecimals(){
       return ((euriborInterestRate + bankInterestMargin)/100) / 12;
    }

    private double calculateMaxAvailableLoan() {
        double monthlyAvailableMoney = (salary *0.4) - financialObligations;
        double mathPower = Math.pow(1 + (calculateMonthlyInterestRateDecimals()), -(12 * mortgagePeriodYears));
        return (monthlyAvailableMoney * (1-mathPower)) / (calculateMonthlyInterestRateDecimals());

    }

    private double calculateMonthlyPayment() {
        propertyPriceInRangeValidator.validate(propertyValue);
        double mathPower = Math.pow(1 + calculateMonthlyInterestRateDecimals(), mortgagePeriodYears * 12);
        return totalLoan * (calculateMonthlyInterestRateDecimals() * mathPower / (mathPower - 1));

    }

    public String returnInfo(){
        if(totalLoan<calculateMaxAvailableLoan()){
            String output = "You can borrow up to " + calculateMaxAvailableLoan() + ". You requested " + totalLoan +
                    " and the current monthly payment would be " + calculateMonthlyPayment();
            return output;

        }else{
            throw new RequestedMortgageTooHighException("Requested mortgage amount too high for income");
        }

    }


}
