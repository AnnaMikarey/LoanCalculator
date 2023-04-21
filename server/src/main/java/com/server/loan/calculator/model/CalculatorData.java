package com.server.loan.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalculatorData {
    private BigDecimal propertyPrice;
    private BigDecimal initialDeposit;
    private BigDecimal salary;
    private BigDecimal financialObligation;
    private int mortgagePeriodYears;
}
