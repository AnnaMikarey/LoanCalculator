package com.server.loan.calculator.service;

import com.server.loan.calculator.exception.DataNotFoundException;
import com.server.loan.calculator.exception.ValidationException;
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.InitialData;
import com.server.loan.calculator.model.ResultsData;
import com.server.loan.calculator.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final AdminRepository adminRepository;

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

    private BigDecimal calculateTotalPaidAmount (CalculatorData data, AdminData adminData) {
        BigDecimal requestedLoan = data.getPropertyPrice()
                .subtract(data.getInitialDeposit());
        BigDecimal addedRates = (adminData.getAdminEuriborRate()
                .add(adminData.getAdminBankMargin())).divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP);
        BigDecimal brackets = BigDecimal.ONE.add(addedRates.multiply(BigDecimal.valueOf(data.getMortgagePeriod())));
        return requestedLoan.multiply(brackets);
    }

    private AdminData fetchValuesFromDatabase () {
        return adminRepository.findById(1)
                .orElseThrow(() -> new DataNotFoundException("Data not found in database"));
    }

    public ResultsData returnCalculatedData (CalculatorData data) {
        AdminData databaseData = fetchValuesFromDatabase();
        customValidateUserInput(data, databaseData);
        BigDecimal requestedLoan = data.getPropertyPrice()
                .subtract(data.getInitialDeposit());
        BigDecimal maxLoanAvailable = calculateMaxAvailableLoan(data, databaseData);
        if (requestedLoan.compareTo(maxLoanAvailable) <= 0) {
            BigDecimal totalInterest = calculateTotalPaidAmount(data, databaseData).subtract(requestedLoan);
            BigDecimal totalBankFeeToPay = databaseData.getAdminMonthlyBankFee()
                    .multiply(BigDecimal.valueOf(data.getMortgagePeriod() * 12L));
            BigDecimal totalAmountToPay = requestedLoan.add(totalInterest.add(totalBankFeeToPay.add(databaseData.getAdminContractFee()
                    .add(databaseData.getAdminRegistrationFee()))));
            return ResultsData.builder()
                    .requestedLoanAmount(requestedLoan)
                    .maxAvailableLoanAmount(maxLoanAvailable)
                    .monthlyPaymentAmount(calculateMonthlyPayment(data, databaseData, requestedLoan))
                    .totalBankFee(totalBankFeeToPay)
                    .totalInterestAmount(totalInterest)
                    .totalAmountToBePaid(totalAmountToPay)
                    .build();
        } else {
            throw new ValidationException("Requested mortgage amount too high for income");
        }
    }

    public Object[] returnInitialDataArray () {
        AdminData adminData = fetchValuesFromDatabase();
        CalculatorData calculatorData = createDefaultCalculatorData(adminData);
        return new Object[]{createInitialData(adminData, calculatorData), returnCalculatedData(calculatorData)};
    }

    private CalculatorData createDefaultCalculatorData (AdminData databaseData) {
        BigDecimal defaultInitialDeposit = ((databaseData.getAdminDefaultPropertyPrice()).multiply(databaseData.getAdminMinDepositPercent())).divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP);
        return CalculatorData.builder()
                .propertyPrice(databaseData.getAdminDefaultPropertyPrice())
                .initialDeposit(defaultInitialDeposit)
                .salary(new BigDecimal("2000"))
                .financialObligation(new BigDecimal("0"))
                .mortgagePeriod(30)
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

    private void customValidateUserInput (CalculatorData calculatorData, AdminData adminData) {
        if (calculatorData.getPropertyPrice()
                .compareTo(adminData.getAdminMaxPropertyPrice()) > 0
                || calculatorData.getPropertyPrice()
                .compareTo(adminData.getAdminMinPropertyPrice()) < 0) {
            throw new ValidationException("Entered property price not between " + String.format("%.2f", adminData.getAdminMinPropertyPrice()) + " and " + String.format("%.2f", adminData.getAdminMaxPropertyPrice()));
        }
        if (calculatorData.getInitialDeposit()
                .compareTo(calculatorData.getPropertyPrice()) >= 0
                || calculatorData.getInitialDeposit()
                .compareTo(calculatorData.getPropertyPrice()
                        .multiply(adminData.getAdminMinDepositPercent())
                        .divide(new BigDecimal("100"), 32, RoundingMode.HALF_UP)) < 0) {
            throw new ValidationException("Initial deposit amount less than " + String.format("%.2f", adminData.getAdminMinDepositPercent()) + "% of property price or 100% and above");
        }
    }
}
