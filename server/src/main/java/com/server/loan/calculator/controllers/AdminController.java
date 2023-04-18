package com.server.loan.calculator.controllers;

import com.server.loan.calculator.models.AdminData;
import com.server.loan.calculator.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("values")
    public AdminData returnAllValues () {
        return adminService.returnAllValues();
    }
}
