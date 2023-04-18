package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.ValidationException;
import com.server.loan.calculator.model.AdminData;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AdminDataValidator {
    private final AdminData adminData;
    private final BigDecimalValueInRangeValidator valueInRangeValidator = new BigDecimalValueInRangeValidator();
    private final BigDecimalInputAsCorrectNumberValidator inputValidator = new BigDecimalInputAsCorrectNumberValidator();

    public void validateAdminInput () {
        inputValidator.validate(adminData.getAdminEuriborRate());
        valueInRangeValidator.validate(adminData.getAdminEuriborRate(), BigDecimal.ONE, new BigDecimal("100"));
        inputValidator.validate(adminData.getAdminBankInterestRate());
        valueInRangeValidator.validate(adminData.getAdminBankInterestRate(), BigDecimal.ONE, new BigDecimal("100"));
        inputValidator.validate(adminData.getAdminRegistrationFee());
        inputValidator.validate(adminData.getAdminMonthlyBankFee());
        inputValidator.validate(adminData.getAdminMinPropertyPrice());
        inputValidator.validate(adminData.getAdminMaxPropertyPrice());
        inputValidator.validate(adminData.getAdminDefaultPropertyPrice());
        inputValidator.validate(adminData.getAdminMinDepositPercent());
        valueInRangeValidator.validate(adminData.getAdminMinDepositPercent(), BigDecimal.ONE, new BigDecimal("100"));
        inputValidator.validate(adminData.getAdminContractFee());
        if (StringUtils.isBlank(adminData.getAdminEuriborDate())) {
            throw new ValidationException("Euribor interest rate date is empty or not a string");
        }
    }
}
