package com.server.loan.calculator.service;

import com.server.loan.calculator.exception.ValueOutOfRangeException;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.validator.CalculatorDataValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    private final CalculatorData data = new CalculatorData();
    private final CalculatorDataValidator calculatorDataValidator = new CalculatorDataValidator(data);

    private BigDecimal calculateMonthlyInterestRateDecimals () {
        BigDecimal addedRates = data.getEuriborRate()
                .add(data.getBankInterestRate());
        return addedRates.divide(new BigDecimal("1200"), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMaxAvailableLoan () {
        calculatorDataValidator.validateDatabaseValues();
        calculatorDataValidator.validateUserInput();
        BigDecimal monthlyAvailableMoney = (data.getSalary()
                .multiply(new BigDecimal("0.4"))).subtract(data.getFinancialObligations());
        BigDecimal powerDenominator = ((BigDecimal.ONE).add(calculateMonthlyInterestRateDecimals())).pow((12 * data.getMortgagePeriodYears()), MathContext.DECIMAL32);
        BigDecimal mathPower = BigDecimal.ONE.divide(powerDenominator, 30, RoundingMode.HALF_UP);
        return (monthlyAvailableMoney.multiply(BigDecimal.ONE.subtract(mathPower))).divide(calculateMonthlyInterestRateDecimals(), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment () {
        calculatorDataValidator.validateDatabaseValues();
        calculatorDataValidator.validateUserInput();
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(calculateMonthlyInterestRateDecimals().add(new BigDecimal("1"))
                .doubleValue(), data.getMortgagePeriodYears() * 12));
        return data.getTotalLoan()
                .multiply(((calculateMonthlyInterestRateDecimals().multiply(mathPower)
                        .divide(mathPower.subtract(BigDecimal.ONE), RoundingMode.HALF_UP))));

    }

    public String returnInfo () {
        if (data.getTotalLoan()
                .compareTo(calculateMaxAvailableLoan()) < 0) {
            String output = "You can borrow up to " + calculateMaxAvailableLoan() + ". You requested " + data.getTotalLoan() + " and the current monthly payment would be " + calculateMonthlyPayment();
            return output;

        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income");
        }
    }
}
