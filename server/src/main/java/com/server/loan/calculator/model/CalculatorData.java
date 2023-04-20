package com.server.loan.calculator.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculatorData {
    private BigDecimal euriborRate = new BigDecimal("3.31");
    private BigDecimal bankInterestRate = new BigDecimal("1.99");
    private BigDecimal contractFee = new BigDecimal("500");
    private BigDecimal monthlyBankFee = new BigDecimal("50");
    private BigDecimal registrationFee = new BigDecimal("100");
    private BigDecimal propertyPrice = new BigDecimal("50000");
    private BigDecimal minPropertyPrice = new BigDecimal("20000");
    private BigDecimal maxPropertyPrice = new BigDecimal("800000");
    private BigDecimal defaultPropertyPrice = new BigDecimal("35000");
    private BigDecimal initialDeposit = new BigDecimal("15000");
    private BigDecimal minDepositPercent = new BigDecimal("15");
    private BigDecimal salary = new BigDecimal("2000");
    private BigDecimal financialObligations = new BigDecimal("100");
    private int mortgagePeriodYears = 10;
    private BigDecimal totalLoan = propertyPrice.subtract(initialDeposit);
}
