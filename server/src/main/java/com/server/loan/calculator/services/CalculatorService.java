package com.server.loan.calculator.services;

import com.server.loan.calculator.exception.ValueOutOfRangeException;
import com.server.loan.calculator.models.CalculatorData;
import com.server.loan.calculator.validators.InputAsCorrectNumberValidator;
import com.server.loan.calculator.validators.ValueInRangeValidator;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Data
public class CalculatorService {

    private final CalculatorData data;
    private final ValueInRangeValidator valueInRangeValidator;
    private final InputAsCorrectNumberValidator inputValidator;

    private double calculateMonthlyInterestRateDecimals () {
        return ((data.getEuriborInterestRate() + data.getBankInterestMargin()) / 100) / 12;
    }

    private BigDecimal calculateMaxAvailableLoan () {
        inputValidator.validate(data.getSalary());
        inputValidator.validate(data.getFinancialObligations());
        inputValidator.validate(data.getMortgagePeriodYears());
        valueInRangeValidator.validate(data.getMortgagePeriodYears(), 1, 30);
        double monthlyAvailableMoney = (data.getSalary() * 0.4) - (data.getFinancialObligations());
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(1 + (calculateMonthlyInterestRateDecimals()), -(12 * data.getMortgagePeriodYears())));
        return (BigDecimal.valueOf(monthlyAvailableMoney)
                .multiply((BigDecimal.ONE.subtract(mathPower)))).divide(BigDecimal.valueOf(calculateMonthlyInterestRateDecimals()), RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment () {
        valueInRangeValidator.validate(data.getPropertyValue(), data.getMinPropertyValue(), data.getMaxPropertyValue());
        valueInRangeValidator.validate(data.getMortgagePeriodYears(), 1, 30);
        valueInRangeValidator.validate(data.getInitialDeposit(), (data.getPropertyValue() / 100 * data.getMinimalDepositPercent()), data.getPropertyValue());
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(1 + calculateMonthlyInterestRateDecimals(), data.getMortgagePeriodYears() * 12));
        return BigDecimal.valueOf(data.getTotalLoan())
                .multiply(((BigDecimal.valueOf(calculateMonthlyInterestRateDecimals())
                        .multiply(mathPower)
                        .divide(mathPower.subtract(BigDecimal.ONE), RoundingMode.HALF_UP))));

    }

    public String returnInfo () {
        if (BigDecimal.valueOf(data.getTotalLoan())
                .compareTo(calculateMaxAvailableLoan()) < 0) {
            String output = "You can borrow up to " + calculateMaxAvailableLoan() + ". You requested " + data.getTotalLoan() + " and the current monthly payment would be " + calculateMonthlyPayment();
            return output;

        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income");
        }

    }

}
