package com.server.loan.calculator.controller;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping(value = "/change-values", consumes = {"application/json"})
    public String saveToDatabase (@RequestBody AdminData data) {
        adminService.addToDatabase(data);
        return "Successfully updated values in database!";
    }

    @PostMapping("/initial-values")
    public ResponseEntity<AdminData> returnInitialValues () {
        return new ResponseEntity<>(adminService.fetchFromDatabase(), HttpStatus.OK);
    }
}
