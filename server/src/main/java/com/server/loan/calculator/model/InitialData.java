package com.server.loan.calculator.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InitialData {
    private BigDecimal euriborRate;
    private String euriborDate;
    private BigDecimal bankInterestRate;
    private BigDecimal annualInterestRate;
    private BigDecimal contractFee;
    private BigDecimal registrationFee;
    private BigDecimal monthlyBankFee;
    private BigDecimal maxPropertyPrice;
    private BigDecimal minPropertyPrice;
    private BigDecimal defaultPropertyPrice;
    private BigDecimal minDepositPercent;
    private BigDecimal defaultInitialDeposit;
    private int defaultMortgagePeriod;
    private BigDecimal defaultSalary;
    private BigDecimal defaultFinancialObligation;
}
