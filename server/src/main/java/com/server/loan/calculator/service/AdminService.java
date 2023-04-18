package com.server.loan.calculator.service;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.validator.AdminDataValidator;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminData adminData = new AdminData();
    private final AdminDataValidator adminDataValidator = new AdminDataValidator(adminData);

    public AdminData returnAllValues () {
        adminDataValidator.validateAdminInput();
        return adminData;
    }
}
