package com.server.loan.calculator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ADMIN")
public class AdminData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Euribor data is mandatory")
    private String adminEuriborDate;
    @NotNull(message = "Euribor rate is mandatory")
    @Positive(message = "Euribor rate must be a positive number")
    @Max(value = 100, message = "Euribor rate cannot be more than 100%")
    private BigDecimal adminEuriborRate;
    @NotNull(message = "Bank margin is mandatory")
    @Positive(message = "Bank margin must be a positive number")
    @Max(value = 100, message = "Bank margin cannot be more than 100%")
    private BigDecimal adminBankMargin;
    @NotNull(message = "Contract fee is mandatory")
    @Positive(message = "Contract fee must be a positive number")
    private BigDecimal adminContractFee;
    @NotNull(message = "Registration fee is mandatory")
    @Positive(message = "Registration fee must be a positive number")
    private BigDecimal adminRegistrationFee;
    @NotNull(message = "Monthly bank fee is mandatory")
    @Positive(message = "Monthly bank fee must be a positive number")
    private BigDecimal adminMonthlyBankFee;
    @NotNull(message = "Minimum property price is mandatory")
    @Positive(message = "Minimum property price must be a positive number")
    private BigDecimal adminMinPropertyPrice;
    @NotNull(message = "Maximum property price is mandatory")
    @Positive(message = "Maximum property price must be a positive number")
    private BigDecimal adminMaxPropertyPrice;
    @NotNull(message = "Default property price is mandatory")
    @Positive(message = "Default property price must be a positive number")
    private BigDecimal adminDefaultPropertyPrice;
    @NotNull(message = "Minimum deposit percent is mandatory")
    @Positive(message = "Minimum deposit percent must be a positive number")
    @Max(value = 100, message = "Minimum deposit percent cannot be more than 100%")
    private BigDecimal adminMinDepositPercent;
}
