package com.server.loan.calculator.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResultsData {
    private BigDecimal requestedLoanAmount;
    private BigDecimal maxAvailableLoanAmount;
    private BigDecimal monthlyPaymentAmount;
}
