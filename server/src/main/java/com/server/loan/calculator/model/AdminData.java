package com.server.loan.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminData {
    private BigDecimal adminEuriborRate;
    private String adminEuriborDate;
    private BigDecimal adminBankMargin;
    private BigDecimal adminContractFee;
    private BigDecimal adminRegistrationFee;
    private BigDecimal adminMonthlyBankFee;
    private BigDecimal adminMinPropertyPrice;
    private BigDecimal adminMaxPropertyPrice;
    private BigDecimal adminDefaultPropertyPrice;
    private BigDecimal adminMinDepositPercent;
}
