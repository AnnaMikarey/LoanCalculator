package com.server.loan.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AdminData {
    private String adminEuriborDate;
    private BigDecimal adminEuriborRate;
    private BigDecimal adminBankMargin;
    private BigDecimal adminContractFee;
    private BigDecimal adminRegistrationFee;
    private BigDecimal adminMonthlyBankFee;
    private BigDecimal adminMinPropertyPrice;
    private BigDecimal adminMaxPropertyPrice;
    private BigDecimal adminDefaultPropertyPrice;
    private BigDecimal adminMinDepositPercent;
}
