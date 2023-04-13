package com.server.loanCalculator;

import com.server.loanCalculator.calculator.exception.RequestedMortgageTooHighException;
import com.server.loanCalculator.calculator.exception.ValidationException;
import com.server.loanCalculator.errors.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RequestedMortgageTooHighException.class)
    public ResponseEntity<ErrorMessage> requestedMortgageTooHighException( RequestedMortgageTooHighException exception){
        ErrorMessage error = ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> validationException (ValidationException exception){
        ErrorMessage error = ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
    }

}
