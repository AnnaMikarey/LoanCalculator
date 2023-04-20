package com.server.loan.calculator.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculatorData {
    private BigDecimal propertyPrice;
    private BigDecimal initialDeposit;
    private BigDecimal salary;
    private BigDecimal financialObligations;
    private int mortgagePeriodYears;
}
