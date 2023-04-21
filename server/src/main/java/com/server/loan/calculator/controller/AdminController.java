package com.server.loan.calculator.controller;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.service.AdminService;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
>>>>>>> f8de320 (merge with java)
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

<<<<<<< HEAD
    @GetMapping("/get-values")
    public AdminData returnAllValues () {

        return adminService.returnAllValues();
=======
    @PostMapping(value = "/values", consumes = {"application/json"})
    public  void saveToDatabase (@RequestBody AdminData data) {
        adminService.addToDatabase(data);
            }

    @GetMapping("/values")
    public ResponseEntity<AdminData> returnInitialValues () {
        return new ResponseEntity<>(adminService.fetchFromDatabase(), HttpStatus.OK);
>>>>>>> f8de320 (merge with java)
    }
}
