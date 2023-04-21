package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.DataNotFoundException;
import com.server.loan.calculator.exception.ValidationException;
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Component
public class CalculatorDataValidator {
    private final BigDecimalValueInRangeValidator valueInRangeValidator;
    private final BigDecimalInputAsCorrectNumberValidator inputValidator;
    private final MortgageYearsValidator mortgageYearsValidator;
    private final DataExistsValidator dataExistsValidator;

    public void validateDatabaseValues (AdminData adminData) {
        dataExistsValidator.validate(adminData.getEuriborRate());
        dataExistsValidator.validate(adminData.getBankMargin());
        dataExistsValidator.validate(adminData.getContractFee());
        dataExistsValidator.validate(adminData.getMonthlyBankFee());
        dataExistsValidator.validate(adminData.getRegistrationFee());
        dataExistsValidator.validate(adminData.getMinDepositPercent());
        dataExistsValidator.validate(adminData.getMaxPropertyPrice());
        dataExistsValidator.validate(adminData.getDefaultPropertyPrice());
        dataExistsValidator.validate(adminData.getMinDepositPercent());
        valueInRangeValidator.validate(adminData.getEuriborRate(), BigDecimal.ONE, new BigDecimal("100"));
        valueInRangeValidator.validate(adminData.getBankMargin(), BigDecimal.ONE, new BigDecimal("100"));
        valueInRangeValidator.validate(adminData.getMinDepositPercent(), BigDecimal.ONE, new BigDecimal("100"));
        if (StringUtils.isBlank(adminData.getEuriborDate())) {
            throw new DataNotFoundException("Euribor interest rate date not found in database");
        }
    }

    public void validateUserInput (CalculatorData calculatorData, AdminData adminData) {
        mortgageYearsValidator.validate(calculatorData.getMortgagePeriodYears());
        inputValidator.validate(calculatorData.getPropertyPrice());
        inputValidator.validate(calculatorData.getInitialDeposit());
        inputValidator.validate(calculatorData.getSalary());
        valueInRangeValidator.validate(calculatorData.getPropertyPrice(), adminData.getMinPropertyPrice(), adminData.getMaxPropertyPrice());
        valueInRangeValidator.validate(calculatorData.getInitialDeposit(), (calculatorData.getPropertyPrice()
                .multiply(adminData.getMinDepositPercent())
                .divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP)), calculatorData.getPropertyPrice());
        inputValidator.validate(calculatorData.getPropertyPrice()
                .subtract(calculatorData.getInitialDeposit()));
        if (calculatorData.getFinancialObligation() == null || calculatorData.getFinancialObligation()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Received value is negative or not a number");
        }
    }
}
