package com.server.loan.calculator.controller;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.ResultsData;
>>>>>>> 5251286823e9c91c97933d339e473bd07763fa35
import com.server.loan.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/client")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping(value = "/calculate-mortgage", consumes = {"application/json"})
    public ResponseEntity<ResultsData> returnCalculatedResults (@RequestBody CalculatorData data) {

        return new ResponseEntity<>(calculatorService.returnCalculatedData(data), HttpStatus.OK);
    }

<<<<<<< HEAD
=======
import com.server.loan.calculator.model.AdminData;
import com.server.loan.calculator.model.CalculatorData;
import com.server.loan.calculator.model.ResultsData;
import com.server.loan.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/client")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping(value = "/calculate-mortgage", consumes = {"application/json"})
    public ResponseEntity<ResultsData> returnCalculatedResults (@RequestBody CalculatorData data) {

        return new ResponseEntity<>(calculatorService.returnCalculatedData(data), HttpStatus.OK);
    }

=======
>>>>>>> 5251286823e9c91c97933d339e473bd07763fa35
    @PostMapping("/initial-values")
    public ResponseEntity<AdminData> returnInitialValues () {
        return new ResponseEntity<>(calculatorService.fetchValuesFromDatabase(), HttpStatus.OK);
    }
<<<<<<< HEAD
>>>>>>> f8de320 (merge with java)
=======
>>>>>>> 5251286823e9c91c97933d339e473bd07763fa35
}
