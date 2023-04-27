package com.server.loan.service;

import com.server.loan.calculator.exception.DataNotFoundException;
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.InitialData;
import com.server.loan.calculator.model.ResultsData;
import com.server.loan.calculator.repository.AdminRepository;
import com.server.loan.calculator.service.CalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {
    @Mock
    private AdminRepository adminRepositoryMock;
    @InjectMocks
    private CalculatorService calculatorService;
    private CalculatorData calculatorDataMock;
    private ResultsData resultsDataMock;
    private InitialData initialDataMock;
    private AdminData adminDataMock;

    @BeforeEach
    public void setup () {
        calculatorDataMock = CalculatorData.builder()
                .propertyPrice(new BigDecimal("50000"))
                .initialDeposit(new BigDecimal("7500"))
                .salary(new BigDecimal("2000"))
                .financialObligation(new BigDecimal("0"))
                .mortgagePeriod(30)
                .build();
        adminDataMock = new AdminData(1, "2023-01-01", new BigDecimal("3.31"), new BigDecimal("1.99"), new BigDecimal("100"), new BigDecimal("10"), new BigDecimal("50"), new BigDecimal("20000"), new BigDecimal("800000"), new BigDecimal("50000"), new BigDecimal("15"));
        resultsDataMock = ResultsData.builder()
                .requestedLoanAmount(new BigDecimal("42500"))
                .maxAvailableLoanAmount(new BigDecimal("144065.06206062485339465288176345896976"))
                .monthlyPaymentAmount(new BigDecimal("236.00447726895140876830807904943712852778989932500"))
                .build();
        initialDataMock = InitialData.builder()
                .euriborDate("2023-01-01")
                .euriborRate(new BigDecimal("3.31"))
                .bankInterestRate(new BigDecimal("1.99"))
                .annualInterestRate(new BigDecimal("5.3"))
                .contractFee(new BigDecimal("100"))
                .registrationFee(new BigDecimal("10"))
                .monthlyBankFee(new BigDecimal("50"))
                .maxPropertyPrice(new BigDecimal("800000"))
                .minPropertyPrice(new BigDecimal("20000"))
                .defaultPropertyPrice(new BigDecimal("50000"))
                .minDepositPercent(new BigDecimal("15"))
                .defaultInitialDeposit(new BigDecimal("7500"))
                .defaultMortgagePeriod(30)
                .defaultSalary(new BigDecimal("2000"))
                .defaultFinancialObligation(new BigDecimal("0"))
                .build();
    }

    @Test
    public void returnCalculatedData_returnsResultsData_whenDataIsInDatabaseAndCorrectUserInput () {
        final ResultsData expected = resultsDataMock;
        doReturn(Optional.of(adminDataMock)).when(adminRepositoryMock)
                .findById(1);
        final ResultsData actual = calculatorService.returnCalculatedData(calculatorDataMock);
        assertEquals(0, expected.getMaxAvailableLoanAmount()
                .compareTo(actual.getMaxAvailableLoanAmount()));
        assertEquals(0, expected.getRequestedLoanAmount()
                .compareTo(actual.getRequestedLoanAmount()));
        assertEquals(0, expected.getMonthlyPaymentAmount()
                .compareTo(actual.getMonthlyPaymentAmount()));

    }

    @Test
    public void returnCalculatedData_throwsDataNotFound_whenDataNotInDatabaseAndCorrectUserInput () {
        final DataNotFoundException expected = new DataNotFoundException("Data not found in database");
        when(adminRepositoryMock.findById(1)).thenThrow(expected);
        assertThrows(DataNotFoundException.class, () -> calculatorService.returnCalculatedData(calculatorDataMock));
    }

    @Test
    public void returnInitialDataArray_returnsSameLengthObjectArray_whenDataIsInDatabaseAndCorrectUserInput () {
        final Object[] expected = new Object[]{initialDataMock, resultsDataMock};
        doReturn(Optional.of(adminDataMock)).when(adminRepositoryMock)
                .findById(1);

        final Object[] actual = calculatorService.returnInitialDataArray();
        assertEquals(expected.length, actual.length);
    }

    @Test
    public void returnInitialDataArray_throwsDataNotFound_whenDataNotInDatabaseAndCorrectUserInput () {
        final DataNotFoundException expected = new DataNotFoundException("Data not found in database");
        when(adminRepositoryMock.findById(1)).thenThrow(expected);
        assertThrows(DataNotFoundException.class, () -> calculatorService.returnInitialDataArray());
    }
}

