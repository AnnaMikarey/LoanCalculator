package com.server.loan.calculator.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdminData {
    private double adminEuriborInterest = 3.31;
    private double adminBankInterestMargin = 1.99;
    private double adminContractFee = 500;
    private double adminRegistrationFee = 10;
    private double adminMonthlyBankFee = 50;
    private double adminMinPropertyPrice = 20000;
    private double adminMaxPropertyPrice = 500000;
    private double adminDefaultPropertyPrice = 20000;
    private double adminMinDepositPercent = 20;
}
