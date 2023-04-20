package com.server.loan.calculator.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdminData {
    private BigDecimal adminEuriborRate = new BigDecimal("3.31");
    private String adminEuriborDate = "2023-04-18";
    private BigDecimal adminBankInterestRate = new BigDecimal("1.99");
    private BigDecimal adminContractFee = new BigDecimal("500");
    private BigDecimal adminRegistrationFee = new BigDecimal("10");
    private BigDecimal adminMonthlyBankFee = new BigDecimal("50");
    private BigDecimal adminMinPropertyPrice = new BigDecimal("20000");
    private BigDecimal adminMaxPropertyPrice = new BigDecimal("500000");
    private BigDecimal adminDefaultPropertyPrice = new BigDecimal("35000");
    private BigDecimal adminMinDepositPercent = new BigDecimal("20");
}
