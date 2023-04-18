package com.server.loan.calculator.services;

import com.server.loan.calculator.models.AdminData;
import com.server.loan.calculator.validators.BigDecimalInputAsCorrectNumberValidator;
import com.server.loan.calculator.validators.BigDecimalValueInRangeValidator;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Data
public class AdminService {

    private final AdminData adminData;
    private final BigDecimalValueInRangeValidator valueInRangeValidator;
    private final BigDecimalInputAsCorrectNumberValidator inputValidator;

    private void validateAdminInput () {
        inputValidator.validate(adminData.getAdminEuriborInterestRate());
        valueInRangeValidator.validate(adminData.getAdminEuriborInterestRate(), new BigDecimal("1"), new BigDecimal("100"));
        inputValidator.validate(adminData.getAdminBankInterestMargin());
        valueInRangeValidator.validate(adminData.getAdminBankInterestMargin(), new BigDecimal("1"), new BigDecimal("100"));
        inputValidator.validate(adminData.getAdminRegistrationFee());
        inputValidator.validate(adminData.getAdminMonthlyBankFee());
        inputValidator.validate(adminData.getAdminMinPropertyPrice());
        inputValidator.validate(adminData.getAdminMaxPropertyPrice());
        inputValidator.validate(adminData.getAdminDefaultPropertyPrice());
        inputValidator.validate(adminData.getAdminMinDepositPercent());
        valueInRangeValidator.validate(adminData.getAdminMinDepositPercent(), new BigDecimal("1"), new BigDecimal("100"));
        inputValidator.validate(adminData.getAdminContractFee());

    }

    public AdminData returnAllValues () {
        validateAdminInput();
        return adminData;
    }

}
