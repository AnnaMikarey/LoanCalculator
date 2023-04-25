package com.server.loan.calculator.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResultsData {
    private BigDecimal requestedLoanAmount;
    private BigDecimal maxAvailableLoanAmount;
    private BigDecimal monthlyPaymentAmount;
}
