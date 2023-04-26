package com.server.loan.calculator.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CalculatorData {

    @NotNull(message = "Property price field is mandatory")
    @Positive(message = "Property price must be a positive number")
    private BigDecimal propertyPrice;
    @NotNull(message = "Initial deposit field is mandatory")
    @Positive(message = "Initial deposit must be a positive number")
    private BigDecimal initialDeposit;
    @NotNull(message = "Salary field is mandatory")
    @Positive(message = "Salary must be a positive number")
    private BigDecimal salary;
    @NotNull(message = "Financial Obligation field cannot be null")
    @PositiveOrZero(message = "Financial Obligations must be a positive number or zero")
    private BigDecimal financialObligation;
    @NotNull(message = "Mortgage period field is mandatory")
    @Min(value = 1, message = "Mortgage period must be between 1 and 30 years")
    @Max(value = 30, message = "Mortgage period must be between 1 and 30 years")
    private int mortgagePeriod;
}
