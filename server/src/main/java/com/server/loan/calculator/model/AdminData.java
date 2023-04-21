package com.server.loan.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminData {
    private BigDecimal euriborRate;
    private String euriborDate;
    private BigDecimal bankMargin;
    private BigDecimal contractFee;
    private BigDecimal registrationFee;
    private BigDecimal monthlyBankFee;
    private BigDecimal minPropertyPrice;
    private BigDecimal maxPropertyPrice;
    private BigDecimal defaultPropertyPrice;
    private BigDecimal minDepositPercent;
}
