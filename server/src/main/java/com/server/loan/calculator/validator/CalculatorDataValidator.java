package com.server.loan.calculator.validator;

import com.server.loan.calculator.model.CalculatorData;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class CalculatorDataValidator {
    private final CalculatorData calculatorData;
    private final BigDecimalValueInRangeValidator valueInRangeValidator = new BigDecimalValueInRangeValidator();
    private final BigDecimalInputAsCorrectNumberValidator inputValidator = new BigDecimalInputAsCorrectNumberValidator();
    private final MortgageYearsValidator mortgageYearsValidator = new MortgageYearsValidator();
    private final DataExistsValidator dataExistsValidator = new DataExistsValidator();

    public void validateDatabaseValues () {
        dataExistsValidator.validate(calculatorData.getEuriborRate());
        dataExistsValidator.validate(calculatorData.getBankInterestRate());
        dataExistsValidator.validate(calculatorData.getContractFee());
        dataExistsValidator.validate(calculatorData.getMonthlyBankFee());
        dataExistsValidator.validate(calculatorData.getRegistrationFee());
        dataExistsValidator.validate(calculatorData.getMinDepositPercent());
        dataExistsValidator.validate(calculatorData.getMaxPropertyPrice());
        dataExistsValidator.validate(calculatorData.getDefaultPropertyPrice());
        dataExistsValidator.validate(calculatorData.getMinDepositPercent());
        valueInRangeValidator.validate(calculatorData.getEuriborRate(), BigDecimal.ONE, new BigDecimal("100"));
        valueInRangeValidator.validate(calculatorData.getBankInterestRate(), BigDecimal.ONE, new BigDecimal("100"));
        valueInRangeValidator.validate(calculatorData.getMinDepositPercent(), BigDecimal.ONE, new BigDecimal("100"));
    }

    public void validateUserInput () {
        mortgageYearsValidator.validate(calculatorData.getMortgagePeriodYears());
        inputValidator.validate(calculatorData.getPropertyPrice());
        inputValidator.validate(calculatorData.getInitialDeposit());
        inputValidator.validate(calculatorData.getSalary());
        inputValidator.validate(calculatorData.getFinancialObligations());
        valueInRangeValidator.validate(calculatorData.getPropertyPrice(), calculatorData.getMinPropertyPrice(), calculatorData.getMaxPropertyPrice());
        valueInRangeValidator.validate(calculatorData.getInitialDeposit(), (calculatorData.getPropertyPrice()
                .multiply(calculatorData.getMinDepositPercent())
                .divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP)), calculatorData.getPropertyPrice());
        inputValidator.validate(calculatorData.getTotalLoan());
    }
}
