package com.server.loan.calculator.service;

import com.server.loan.calculator.exception.ValueOutOfRangeException;
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.InitialData;
import com.server.loan.calculator.model.ResultsData;
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

    private BigDecimal calculateMonthlyInterestRateDecimals (AdminData adminData) {
        BigDecimal addedRates = adminData.getEuriborRate()
                .add(adminData.getBankMargin());
        return addedRates.divide(new BigDecimal("1200"), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMaxAvailableLoan (CalculatorData data, AdminData adminData) {

        BigDecimal monthlyAvailableMoney = (data.getSalary()
                .multiply(new BigDecimal("0.4"))).subtract(data.getFinancialObligation());
        BigDecimal powerDenominator = ((BigDecimal.ONE).add(calculateMonthlyInterestRateDecimals(adminData))).pow((12 * data.getMortgagePeriodYears()), MathContext.DECIMAL32);
        BigDecimal mathPower = BigDecimal.ONE.divide(powerDenominator, 32, RoundingMode.HALF_UP);
        return (monthlyAvailableMoney.multiply(BigDecimal.ONE.subtract(mathPower))).divide(calculateMonthlyInterestRateDecimals(adminData), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment (CalculatorData data, AdminData adminData, BigDecimal requestedLoan) {
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(calculateMonthlyInterestRateDecimals(adminData).add(new BigDecimal("1"))
                .doubleValue(), data.getMortgagePeriodYears() * 12));
        return requestedLoan.multiply(((calculateMonthlyInterestRateDecimals(adminData).multiply(mathPower)
                .divide(mathPower.subtract(BigDecimal.ONE), RoundingMode.HALF_UP))));

    }

    public AdminData fetchValuesFromDatabase () {
        return new AdminData(new BigDecimal("3.31"), "2023-04-17", new BigDecimal("1.99"), new BigDecimal("500"), new BigDecimal("10"), new BigDecimal("50"), new BigDecimal("20000"), new BigDecimal("500000"), new BigDecimal("35000"), new BigDecimal("15"));
    }

    public ResultsData returnCalculatedData (CalculatorData data) {
        calculatorDataValidator.validateDatabaseValues(fetchValuesFromDatabase());
        calculatorDataValidator.validateUserInput(data, fetchValuesFromDatabase());
        ResultsData results = new ResultsData();
        results.setMaxAvailableLoanAmount(calculateMaxAvailableLoan(data, fetchValuesFromDatabase()));
        BigDecimal requestedLoan = data.getPropertyPrice()
                .subtract(data.getInitialDeposit());
        if (requestedLoan.compareTo(results.getMaxAvailableLoanAmount()) <= 0) {
            results.setRequestedLoanAmount(requestedLoan);

        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income");
        }
        results.setMonthlyPaymentAmount(calculateMonthlyPayment(data, fetchValuesFromDatabase(), requestedLoan));
        return results;
    }

    public Object[] returnInitialDataArray () {
        InitialData initialData = createInitialData();
        ResultsData initialResults = new ResultsData();
        initialResults.setRequestedLoanAmount(createInitialData().getDefaultPropertyPrice()
                .subtract(createInitialData().getDefaultInitialDeposit()));
        initialResults.setMaxAvailableLoanAmount(calculateMaxAvailableLoan(createDefaultCalculatorData(), fetchValuesFromDatabase()));
        initialResults.setMonthlyPaymentAmount(calculateMonthlyPayment(createDefaultCalculatorData(), fetchValuesFromDatabase(), initialResults.getRequestedLoanAmount()));
        return new Object[]{initialData, initialResults};
    }

    private CalculatorData createDefaultCalculatorData () {
        calculatorDataValidator.validateDatabaseValues(fetchValuesFromDatabase());
        BigDecimal defaultInitialDeposit = ((fetchValuesFromDatabase().getDefaultPropertyPrice()).multiply(fetchValuesFromDatabase().getMinDepositPercent())).divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP);
        return new CalculatorData(fetchValuesFromDatabase().getDefaultPropertyPrice(), defaultInitialDeposit, new BigDecimal("2500"), new BigDecimal("0"), 10);

    }

    private InitialData createInitialData () {
        calculatorDataValidator.validateDatabaseValues(fetchValuesFromDatabase());
        InitialData initialData = new InitialData();
        initialData.setEuriborRate(fetchValuesFromDatabase().getEuriborRate());
        initialData.setEuriborDate(fetchValuesFromDatabase().getEuriborDate());
        initialData.setRegistrationFee(fetchValuesFromDatabase().getRegistrationFee());
        initialData.setBankInterestRate(fetchValuesFromDatabase().getBankMargin());
        initialData.setAnnualInterestRate(fetchValuesFromDatabase().getBankMargin()
                .add(fetchValuesFromDatabase().getEuriborRate()));
        initialData.setContractFee(fetchValuesFromDatabase().getContractFee());
        initialData.setMonthlyBankFee(fetchValuesFromDatabase().getMonthlyBankFee());
        initialData.setMaxPropertyPrice(fetchValuesFromDatabase().getMaxPropertyPrice());
        initialData.setMinPropertyPrice(fetchValuesFromDatabase().getMinPropertyPrice());
        initialData.setDefaultPropertyPrice(createDefaultCalculatorData().getPropertyPrice());
        initialData.setMinDepositPercent(fetchValuesFromDatabase().getMinDepositPercent());
        initialData.setDefaultMortgagePeriod(createDefaultCalculatorData().getMortgagePeriodYears());
        initialData.setDefaultSalary(createDefaultCalculatorData().getSalary());
        initialData.setDefaultFinancialObligation(createDefaultCalculatorData().getFinancialObligation());
        initialData.setDefaultInitialDeposit(createDefaultCalculatorData().getInitialDeposit());
        return initialData;
    }
}
