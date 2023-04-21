package com.server.loan.calculator.validator;

import com.server.loan.calculator.exception.ValidationException;
import com.server.loan.calculator.model.AdminData;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class AdminDataValidator {
    private final BigDecimalValueInRangeValidator valueInRangeValidator;
    private final BigDecimalInputAsCorrectNumberValidator inputValidator;

    public void validateAdminInput (AdminData adminData) {
        inputValidator.validate(adminData.getEuriborRate());
        valueInRangeValidator.validate(adminData.getEuriborRate(), BigDecimal.ONE, new BigDecimal("100"));
        inputValidator.validate(adminData.getBankMargin());
        valueInRangeValidator.validate(adminData.getBankMargin(), BigDecimal.ONE, new BigDecimal("100"));
        inputValidator.validate(adminData.getRegistrationFee());
        inputValidator.validate(adminData.getMonthlyBankFee());
        inputValidator.validate(adminData.getMinPropertyPrice());
        inputValidator.validate(adminData.getMaxPropertyPrice());
        inputValidator.validate(adminData.getDefaultPropertyPrice());
        inputValidator.validate(adminData.getMinDepositPercent());
        valueInRangeValidator.validate(adminData.getMinDepositPercent(), BigDecimal.ONE, new BigDecimal("100"));
        inputValidator.validate(adminData.getContractFee());
        if (StringUtils.isBlank(adminData.getEuriborDate())) {
            throw new ValidationException("Euribor interest rate date empty or not a string");
        }
    }
}
