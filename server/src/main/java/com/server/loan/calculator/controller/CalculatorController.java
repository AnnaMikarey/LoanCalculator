package com.server.loan.calculator.controller;

import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.ResultsData;
import com.server.loan.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping(value = "/calculate", consumes = {"application/json"})
    public ResponseEntity<ResultsData> returnCalculatedResults (@RequestBody CalculatorData data) {
        return new ResponseEntity<>(calculatorService.returnCalculatedData(data), HttpStatus.OK);
    }

    @GetMapping("/get-initial-values")
    public ResponseEntity<Object[]> returnDefaultValues () {
        return new ResponseEntity<>(calculatorService.returnInitialDataArray(), HttpStatus.OK);
    }
}
