package com.server.loanCalculator.calculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculator;

    @GetMapping
    @ResponseBody
    public String returnCalculatedMortgage () {

        return calculator.returnInfo();
    }

}
