package com.server.loan.calculator.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CalculatorData {
    private double euriborInterestRate = 3.31;
    private double bankInterestMargin = 1.99;
    private double contractFee = 500;
    private double monthlyBankFee = 50;
    private double registrationFee = 10;
    private double mortgagePeriodYears = 10;
    private double propertyValue = 50000;
    private double minPropertyValue = 20000;
    private double maxPropertyValue = 800000;
    private double defaultPropertyValue = 35000;
    private double initialDeposit = 15000;
    private double minimalDepositPercent = 15;
    private double totalLoan = propertyValue - initialDeposit;
    private double salary = 2000;
    private double financialObligations = 100;
}
