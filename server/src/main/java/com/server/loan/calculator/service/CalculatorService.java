package com.server.loan.calculator.service;

import com.server.loan.calculator.exception.ValueOutOfRangeException;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.validator.CalculatorDataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final CalculatorDataValidator calculatorDataValidator;

    private BigDecimal calculateMonthlyInterestRateDecimals (CalculatorData data) {
        BigDecimal addedRates = data.getEuriborRate()
                .add(data.getBankInterestRate());
        return addedRates.divide(new BigDecimal("1200"), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMaxAvailableLoan (CalculatorData data) {
        BigDecimal monthlyAvailableMoney = (data.getSalary()
                .multiply(new BigDecimal("0.4"))).subtract(data.getFinancialObligations());
        BigDecimal powerDenominator = ((BigDecimal.ONE).add(calculateMonthlyInterestRateDecimals(data))).pow((12 * data.getMortgagePeriodYears()), MathContext.DECIMAL32);
        BigDecimal mathPower = BigDecimal.ONE.divide(powerDenominator, 30, RoundingMode.HALF_UP);
        return (monthlyAvailableMoney.multiply(BigDecimal.ONE.subtract(mathPower))).divide(calculateMonthlyInterestRateDecimals(data), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment (CalculatorData data) {
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(calculateMonthlyInterestRateDecimals(data).add(new BigDecimal("1"))
                .doubleValue(), data.getMortgagePeriodYears() * 12));
        return data.getTotalLoan()
                .multiply(((calculateMonthlyInterestRateDecimals(data).multiply(mathPower)
                        .divide(mathPower.subtract(BigDecimal.ONE), RoundingMode.HALF_UP))));

    }

    public String returnInfo () {
        CalculatorData data = new CalculatorData();
        calculatorDataValidator.validateDatabaseValues(data);
        calculatorDataValidator.validateUserInput(data);
        if (data.getTotalLoan()
                .compareTo(calculateMaxAvailableLoan(data)) < 0) {
            String output = "You can borrow up to " + calculateMaxAvailableLoan(data) + ". You requested " + data.getTotalLoan() + " and the current monthly payment would be " + calculateMonthlyPayment(data);
            return output;

        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income");
        }
    }
}
