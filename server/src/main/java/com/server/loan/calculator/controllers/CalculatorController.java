package com.server.loan.calculator.controllers;

import com.server.loan.calculator.services.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calc")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculator;

    @GetMapping
    public String returnCalculatedMortgage () {
        return calculator.returnInfo();
    }

}
