package com.server.loan.calculator.service;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.validator.AdminDataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDataValidator adminDataValidator;

    public void addToDatabase (AdminData adminData) {
        adminDataValidator.validateAdminInput(adminData);
    }

    public AdminData fetchFromDatabase () {
        return new AdminData("2023-04-17", new BigDecimal("3.31"), new BigDecimal("1.99"), new BigDecimal("500"),
                new BigDecimal("10"), new BigDecimal("50"), new BigDecimal("20000"), new BigDecimal("500000"),
                new BigDecimal("35000"), new BigDecimal("15"));
    }
}
