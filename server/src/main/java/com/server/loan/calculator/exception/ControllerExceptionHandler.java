package com.server.loan.calculator.exception;

import com.server.loan.calculator.errors.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

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
    public ResponseEntity<ErrorMessage> valueOutOfRangeException (ValueOutOfRangeException exception) {
        ErrorMessage error = errorMessageBuilder(exception);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> validationException (ValidationException exception) {
        ErrorMessage error = errorMessageBuilder(exception);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
    }
}
