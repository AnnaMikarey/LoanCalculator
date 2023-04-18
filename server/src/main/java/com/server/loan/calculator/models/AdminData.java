package com.server.loan.calculator.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class AdminData {

    private BigDecimal adminEuriborInterestRate = new BigDecimal("3.31");
    private BigDecimal adminBankInterestMargin = new BigDecimal("1.99");
    private BigDecimal adminContractFee = new BigDecimal("500");
    private BigDecimal adminRegistrationFee = new BigDecimal("10");
    private BigDecimal adminMonthlyBankFee = new BigDecimal("50");
    private BigDecimal adminMinPropertyPrice = new BigDecimal("20000");
    private BigDecimal adminMaxPropertyPrice = new BigDecimal("500000");
    private BigDecimal adminDefaultPropertyPrice = new BigDecimal("35000");
    private BigDecimal adminMinDepositPercent = new BigDecimal("20");

}
