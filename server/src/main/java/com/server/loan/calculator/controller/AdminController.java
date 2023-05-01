package com.server.loan.calculator.controller;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:4200/", " http://16.16.120.205:8081/", "http://13.48.162.1:8081/", "https://loan-calculator.site/"})
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping(value = "/change-values", consumes = {"application/json"})
    public ResponseEntity<HttpStatus> saveToDatabase (@Valid @RequestBody AdminData data) {
        adminService.addToDatabase(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-initial-values")
    public ResponseEntity<AdminData> returnInitialValues () {
        return new ResponseEntity<>(adminService.fetchFromDatabase(), HttpStatus.OK);
    }
}