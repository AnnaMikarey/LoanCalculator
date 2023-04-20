package com.server.loan.calculator.service;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.validator.AdminDataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDataValidator adminDataValidator;

    public AdminData returnAllValues () {
        AdminData adminData = new AdminData();
        adminDataValidator.validateAdminInput(adminData);
        return adminData;
    }
}
