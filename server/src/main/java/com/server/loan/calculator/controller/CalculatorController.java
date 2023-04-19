package com.server.loan.calculator.controller;

import com.server.loan.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculator;

    @GetMapping("/calculate-mortgage")
    public String returnCalculatedMortgage () {

        return calculator.returnInfo();
    }

}
