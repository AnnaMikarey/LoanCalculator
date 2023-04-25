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
        BigDecimal addedRates = adminData.getAdminEuriborRate()
                .add(adminData.getAdminBankMargin());
        return addedRates.divide(new BigDecimal("1200"), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMaxAvailableLoan (CalculatorData data, AdminData adminData) {

        BigDecimal monthlyAvailableMoney = (data.getSalary()
                .multiply(new BigDecimal("0.4"))).subtract(data.getFinancialObligation());
        BigDecimal powerDenominator = ((BigDecimal.ONE).add(calculateMonthlyInterestRateDecimals(adminData))).pow((12 * data.getMortgagePeriod()), MathContext.DECIMAL32);
        BigDecimal mathPower = BigDecimal.ONE.divide(powerDenominator, 32, RoundingMode.HALF_UP);
        return (monthlyAvailableMoney.multiply(BigDecimal.ONE.subtract(mathPower))).divide(calculateMonthlyInterestRateDecimals(adminData), 32, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment (CalculatorData data, AdminData adminData, BigDecimal requestedLoan) {
        BigDecimal mathPower = BigDecimal.valueOf(Math.pow(calculateMonthlyInterestRateDecimals(adminData).add(new BigDecimal("1"))
                .doubleValue(), data.getMortgagePeriod() * 12));
        return requestedLoan.multiply(((calculateMonthlyInterestRateDecimals(adminData).multiply(mathPower)
                .divide(mathPower.subtract(BigDecimal.ONE), RoundingMode.HALF_UP))));

    }

    private AdminData fetchValuesFromDatabase () {
        return new AdminData("2023-04-20", new BigDecimal("3.31"), new BigDecimal("1.99"), new BigDecimal("500"),
                new BigDecimal("10"), new BigDecimal("50"), new BigDecimal("20000"), new BigDecimal("500000"),
                new BigDecimal("35000"), new BigDecimal("15"));
    }

    public ResultsData returnCalculatedData (CalculatorData data) {
        AdminData databaseData = fetchValuesFromDatabase();
        calculatorDataValidator.validateDatabaseValues(databaseData);
        calculatorDataValidator.validateUserInput(data, databaseData);
        BigDecimal requestedLoan = data.getPropertyPrice()
                .subtract(data.getInitialDeposit());
        BigDecimal maxLoanAvailable = calculateMaxAvailableLoan(data, databaseData);
        if (requestedLoan.compareTo(maxLoanAvailable) <= 0) {
            return ResultsData.builder()
                    .requestedLoanAmount(requestedLoan)
                    .maxAvailableLoanAmount(maxLoanAvailable)
                    .monthlyPaymentAmount(calculateMonthlyPayment(data, databaseData, requestedLoan))
                    .build();
        } else {
            throw new ValueOutOfRangeException("Requested mortgage amount too high for income");
        }
    }

    public Object[] returnInitialDataArray () {
        AdminData adminData = fetchValuesFromDatabase();
        calculatorDataValidator.validateDatabaseValues(adminData);
        CalculatorData calculatorData = createDefaultCalculatorData(adminData);
        return new Object[]{createInitialData(adminData, calculatorData), returnCalculatedData(calculatorData)};
    }

    private CalculatorData createDefaultCalculatorData (AdminData databaseData) {
        BigDecimal defaultInitialDeposit = ((databaseData.getAdminDefaultPropertyPrice()).multiply(databaseData.getAdminMinDepositPercent())).divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP);
        return CalculatorData.builder()
                .propertyPrice(databaseData.getAdminDefaultPropertyPrice())
                .initialDeposit(defaultInitialDeposit)
                .salary(new BigDecimal("2500"))
                .financialObligation(new BigDecimal("0"))
                .mortgagePeriod(10)
                .build();
    }

    private InitialData createInitialData (AdminData databaseData, CalculatorData calculatorData) {
        return InitialData.builder()
                .euriborDate(databaseData.getAdminEuriborDate())
                .euriborRate(databaseData.getAdminEuriborRate())
                .bankInterestRate(databaseData.getAdminBankMargin())
                .annualInterestRate(databaseData.getAdminEuriborRate()
                        .add(databaseData.getAdminBankMargin()))
                .contractFee(databaseData.getAdminContractFee())
                .registrationFee(databaseData.getAdminRegistrationFee())
                .monthlyBankFee(databaseData.getAdminMonthlyBankFee())
                .maxPropertyPrice(databaseData.getAdminMaxPropertyPrice())
                .minPropertyPrice(databaseData.getAdminMinPropertyPrice())
                .defaultPropertyPrice(calculatorData.getPropertyPrice())
                .minDepositPercent(databaseData.getAdminMinDepositPercent())
                .defaultInitialDeposit(calculatorData.getInitialDeposit())
                .defaultMortgagePeriod(calculatorData.getMortgagePeriod())
                .defaultSalary(calculatorData.getSalary())
                .defaultFinancialObligation(calculatorData.getFinancialObligation())
                .build();
    }
}
