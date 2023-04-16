package com.server.loanCalculator.admin;

import com.server.loanCalculator.calculator.exception.ValidationException;
import com.server.loanCalculator.calculator.validators.PercentageBelow100Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("values")
    @ResponseBody
    public String returnAllValues () {
        return adminService.returnAllValues();
    }

    @GetMapping("max-property")
    public Double returnMaxPropertyPrice () {
        return adminService.getAdminMaxPropertyPrice();
    }

    @GetMapping("min-property")
    public Double returnMinPropertyPrice () {
        return adminService.getAdminMinPropertyPrice();
    }

    @GetMapping("default-property")
    public Double returnDefaultPropertyPrice () {
        return adminService.getAdminDefaultPropertyPrice();
    }

    @GetMapping("bank-interest")
    public Double returnBankInterestMargin () {
        return adminService.getAdminBankInterestMargin();
    }

    @GetMapping("contract-fee")
    public Double returnContractFee () {
        return adminService.getAdminContractFee();
    }

    @GetMapping("bank-fee")
    public Double returnMonthlyBankFee () {
        return adminService.getAdminMonthlyBankFee();
    }

    @GetMapping("registration")
    public Double returnRegistrationFee () {
        return adminService.getAdminRegistrationFee();
    }

    @GetMapping("min-deposit")
    public Double returnMinDepositPercentage () {
        return adminService.getAdminMinDepositPercent();
    }

}
