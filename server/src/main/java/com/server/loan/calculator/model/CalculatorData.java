package com.server.loan.calculator.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CalculatorData {
    private BigDecimal propertyPrice;
    private BigDecimal initialDeposit;
    private BigDecimal salary;
    private BigDecimal financialObligation;
    private int mortgagePeriod;
}