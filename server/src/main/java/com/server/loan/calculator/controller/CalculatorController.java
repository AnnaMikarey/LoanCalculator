package com.server.loan.calculator.controller;

import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.ResultsData;
import com.server.loan.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping(value = "/calculate-mortgage", consumes = {"application/json"})
    public ResponseEntity<ResultsData> returnCalculatedResults (@RequestBody CalculatorData data) {

        return new ResponseEntity<>(calculatorService.returnCalculatedData(data), HttpStatus.OK);
    }

    @PostMapping("/initial-values")
    public ResponseEntity<AdminData> returnInitialValues () {
        return new ResponseEntity<>(calculatorService.fetchValuesFromDatabase(), HttpStatus.OK);
    }
}
