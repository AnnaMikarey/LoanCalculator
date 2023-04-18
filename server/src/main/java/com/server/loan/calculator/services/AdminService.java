package com.server.loan.calculator.services;

import com.server.loan.calculator.models.AdminData;
import com.server.loan.calculator.validators.InputAsCorrectNumberValidator;
import com.server.loan.calculator.validators.ValueInRangeValidator;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AdminService {

    private final AdminData adminData;
    private final ValueInRangeValidator valueInRangeValidator;
    private final InputAsCorrectNumberValidator inputValidator;

    private void validateAdminInput () {
        inputValidator.validate(adminData.getAdminEuriborInterest());
        valueInRangeValidator.validate(adminData.getAdminEuriborInterest(), 1, 100);
        inputValidator.validate(adminData.getAdminBankInterestMargin());
        valueInRangeValidator.validate(adminData.getAdminBankInterestMargin(), 1, 100);
        inputValidator.validate(adminData.getAdminRegistrationFee());
        inputValidator.validate(adminData.getAdminMonthlyBankFee());
        inputValidator.validate(adminData.getAdminMinPropertyPrice());
        inputValidator.validate(adminData.getAdminMaxPropertyPrice());
        inputValidator.validate(adminData.getAdminDefaultPropertyPrice());
        inputValidator.validate(adminData.getAdminMinDepositPercent());
        valueInRangeValidator.validate(adminData.getAdminMinDepositPercent(), 1, 100);
        inputValidator.validate(adminData.getAdminContractFee());

    }

    public AdminData returnAllValues () {
        validateAdminInput();
        return adminData;
    }

}
