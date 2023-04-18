package com.server.loan.calculator.services;

import com.server.loan.calculator.exception.ValueOutOfRangeException;
import com.server.loan.calculator.models.CalculatorData;
import com.server.loan.calculator.validators.BigDecimalInputAsCorrectNumberValidator;
import com.server.loan.calculator.validators.BigDecimalValueInRangeValidator;
import com.server.loan.calculator.validators.MortgageYearsValidator;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
@Data
public class CalculatorService {

    private final CalculatorData data;
    private final BigDecimalValueInRangeValidator bigDecimalValueInRangeValidator;
    private final BigDecimalInputAsCorrectNumberValidator bigDecimalInputValidator;
    private final MortgageYearsValidator mortgageYearsValidator;

    private BigDecimal calculateMonthlyInterestRateDecimals () {
        BigDecimal addedRates = data.getEuriborInterestRate()
                .add(data.getBankInterestMargin());
        return addedRates.divide(new BigDecimal("1200"), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMaxAvailableLoan () {
        bigDecimalInputValidator.validate(data.getSalary());
        bigDecimalInputValidator.validate(data.getFinancialObligations());
        mortgageYearsValidator.validate(data.getMortgagePeriodYears());
        BigDecimal monthlyAvailableMoney = (data.getSalary()
                .multiply(new BigDecimal("0.4"))).subtract(data.getFinancialObligations());
        BigDecimal powerDenominator = ((BigDecimal.ONE).add(calculateMonthlyInterestRateDecimals())).pow((12 * data.getMortgagePeriodYears()), MathContext.DECIMAL32);
        BigDecimal mathPower = BigDecimal.ONE.divide(powerDenominator, 30, RoundingMode.HALF_UP);
        return (monthlyAvailableMoney.multiply(BigDecimal.ONE.subtract(mathPower))).divide(calculateMonthlyInterestRateDecimals(), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment () {
        bigDecimalValueInRangeValidator.validate(data.getPropertyPrice(), data.getMinPropertyPrice(), data.getMaxPropertyPrice());
        mortgageYearsValidator.validate(data.getMortgagePeriodYears());
        bigDecimalValueInRangeValidator.validate(data.getInitialDeposit(), (data.getPropertyPrice()
                .multiply(data.getMinDepositPercent())
                .divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP)), data.getPropertyPrice());
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(calculateMonthlyInterestRateDecimals().add(new BigDecimal("1"))
                .doubleValue(), data.getMortgagePeriodYears() * 12));
        return data.getTotalLoan()
                .multiply(((calculateMonthlyInterestRateDecimals().multiply(mathPower)
                        .divide(mathPower.subtract(BigDecimal.ONE), RoundingMode.HALF_UP))));

    }

    public String returnInfo () {
        if (data.getTotalLoan()
                .compareTo(calculateMaxAvailableLoan()) < 0) {
            String output = "You can borrow up to " + calculateMaxAvailableLoan() + ". You requested " + data.getTotalLoan()
                    + " and the current monthly payment would be " + calculateMonthlyPayment();
            return output;

        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income");
        }

    }

}
