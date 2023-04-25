package com.server.loan.calculator.service;

import com.server.loan.calculator.exception.DataNotFoundException;
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public void addToDatabase (AdminData newAdminData) {
        AdminData databaseData = adminRepository.findById(1)
                .orElseThrow(() -> new DataNotFoundException("Data not found in database"));
        databaseData.setAdminEuriborDate(newAdminData.getAdminEuriborDate());
        databaseData.setAdminEuriborRate(newAdminData.getAdminEuriborRate());
        databaseData.setAdminBankMargin(newAdminData.getAdminBankMargin());
        databaseData.setAdminContractFee(newAdminData.getAdminContractFee());
        databaseData.setAdminRegistrationFee(newAdminData.getAdminRegistrationFee());
        databaseData.setAdminMonthlyBankFee(newAdminData.getAdminMonthlyBankFee());
        databaseData.setAdminMinPropertyPrice(newAdminData.getAdminMinPropertyPrice());
        databaseData.setAdminMaxPropertyPrice(newAdminData.getAdminMaxPropertyPrice());
        databaseData.setAdminDefaultPropertyPrice(newAdminData.getAdminDefaultPropertyPrice());
        databaseData.setAdminMinDepositPercent(newAdminData.getAdminMinDepositPercent());
        adminRepository.save(databaseData);
    }

    public AdminData fetchFromDatabase () {
        return adminRepository.findById(1)
                .orElseThrow(() -> new DataNotFoundException("Data not found in database"));
    }
}

