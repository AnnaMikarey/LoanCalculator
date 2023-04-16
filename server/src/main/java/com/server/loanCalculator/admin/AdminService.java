package com.server.loanCalculator.admin;

import com.server.loanCalculator.calculator.validators.InputAsCorrectNumberValidator;
import com.server.loanCalculator.calculator.validators.PercentageBelow100Validator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class AdminService {

    private double adminBankInterestMargin = 1.99;
    private double adminContractFee = 500;
    private double adminRegistrationFee = 10;
    private double adminMonthlyBankFee = 50;
    private double adminMinPropertyPrice = 10000;
    private double adminMaxPropertyPrice = 500000;
    private double adminDefaultPropertyPrice = 20000;
    private double adminMinDepositPercent = 20;
    private InputAsCorrectNumberValidator inputValidator = new InputAsCorrectNumberValidator();
    private PercentageBelow100Validator percentageValidator = new PercentageBelow100Validator();

    private void validateAdminInput () {
        inputValidator.validate(adminBankInterestMargin);
        percentageValidator.validate(adminBankInterestMargin);
        inputValidator.validate(adminRegistrationFee);
        inputValidator.validate(adminMonthlyBankFee);
        inputValidator.validate(adminMinPropertyPrice);
        inputValidator.validate(adminMaxPropertyPrice);
        inputValidator.validate(adminDefaultPropertyPrice);
        inputValidator.validate(adminMinDepositPercent);
        percentageValidator.validate(adminMinDepositPercent);
    }

    public String returnAllValues () {
        validateAdminInput();
        return "Updated values are: \n" + adminBankInterestMargin + "%\n" + adminContractFee + "\n" + adminRegistrationFee + "\n"
                + adminMonthlyBankFee + "\n" + adminMinDepositPercent + "\n" + adminMaxPropertyPrice + "\n" + adminDefaultPropertyPrice
                + "\n" + adminMinDepositPercent + "%";
    }
}
