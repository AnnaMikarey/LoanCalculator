package com.server.loan.calculator.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResultsData {
    private BigDecimal resultContractFee;
    private BigDecimal resultRegistrationFee;
    private BigDecimal resultMonthlyBankFee;
    private BigDecimal requestedLoanAmount;
    private BigDecimal maxAvailableLoanAmount;
    private BigDecimal monthlyPaymentAmount;
}
