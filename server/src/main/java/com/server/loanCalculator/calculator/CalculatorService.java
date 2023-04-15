package com.server.loanCalculator.calculator;

import com.server.loanCalculator.calculator.exception.ValueOutOfRangeException;
import com.server.loanCalculator.calculator.validators.InputAsCorrectNumberValidator;
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
    private double monthlyBankFee = 50;
    private double registrationFee = 10;
    private double mortgagePeriodYears = 10;
    private double propertyValue = 50000;
    private double minPropertyValue = 20000;
    private double maxPropertyValue = 800000;
    private double defaultPropertyValue = 35000;
    private double initialDeposit = 15000;
    private double minimalDepositPercent = 15;
    private double totalLoan = propertyValue - initialDeposit;
    private double salary = 2000;
    private double financialObligations = 100;
    private double minMortgageYears = 1;
    private double maxMortgageYears = 30;
    private PropertyPriceInRangeValidator propertyPriceInRangeValidator = new PropertyPriceInRangeValidator();
    private InputAsCorrectNumberValidator inputValidator = new InputAsCorrectNumberValidator();

    private double calculateMonthlyInterestRateDecimals () {
        return ((euriborInterestRate + bankInterestMargin) / 100) / 12;
    }

    private void checkIfMortgageYearsWithinRange () {
        if (mortgagePeriodYears < minMortgageYears || mortgagePeriodYears > maxMortgageYears) {
            throw new ValueOutOfRangeException("Requested mortgage period out of the set parameters.");
        }
    }

    private void checkIfDepositWithinRange () {
        double minDeposit = (propertyValue / 100) * minimalDepositPercent;
        if (initialDeposit < minDeposit || initialDeposit == propertyValue) {
            throw new ValueOutOfRangeException("Initial deposit below " + minimalDepositPercent + "% of property value or matches property value.");
        }
    }

    private double calculateMaxAvailableLoan () {
        inputValidator.validate(salary);
        checkIfMortgageYearsWithinRange();
        double monthlyAvailableMoney = (salary * 0.4) - financialObligations;
        double mathPower = Math.pow(1 + (calculateMonthlyInterestRateDecimals()), -(12 * mortgagePeriodYears));
        return (monthlyAvailableMoney * (1 - mathPower)) / (calculateMonthlyInterestRateDecimals());
    }

    private double calculateMonthlyPayment () {
        propertyPriceInRangeValidator.validate(propertyValue);
        checkIfMortgageYearsWithinRange();
        checkIfDepositWithinRange();
        double mathPower = Math.pow(1 + calculateMonthlyInterestRateDecimals(), mortgagePeriodYears * 12);
        return totalLoan * (calculateMonthlyInterestRateDecimals() * mathPower / (mathPower - 1));
    }

    public String returnInfo () {
        if (totalLoan < calculateMaxAvailableLoan()) {
            return "You can borrow up to " + calculateMaxAvailableLoan() + ". You requested " + totalLoan
                    + " and the current monthly payment would be " + calculateMonthlyPayment()
                    + " and a monthly bank fee of " + monthlyBankFee + " Eur.";
        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income.");
        }
    }

}
