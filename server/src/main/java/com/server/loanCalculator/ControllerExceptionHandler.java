package com.server.loanCalculator;

import com.server.loanCalculator.calculator.exception.ValueOutOfRangeException;
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

    private ErrorMessage errorMessageBuilder (RuntimeException exception) {
        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(ValueOutOfRangeException.class)
    public ResponseEntity<ErrorMessage> requestedMortgageTooHighException (ValueOutOfRangeException exception) {
        ErrorMessage error = errorMessageBuilder(exception);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> validationException (ValidationException exception) {
        ErrorMessage error = errorMessageBuilder(exception);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
    }
}
