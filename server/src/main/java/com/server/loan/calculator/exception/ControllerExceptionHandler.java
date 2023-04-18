package com.server.loan.calculator.exception;

import com.server.loan.calculator.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private ErrorMessage errorMessageBuilder (RuntimeException exception, HttpStatus status) {
        return ErrorMessage.builder()
                .statusCode(status.value())
                .timestamp(Instant.now())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(ValueOutOfRangeException.class)
    public ResponseEntity<ErrorMessage> valueOutOfRangeException (ValueOutOfRangeException exception) {
        ErrorMessage error = errorMessageBuilder(exception, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> validationException (ValidationException exception) {
        ErrorMessage error = errorMessageBuilder(exception, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> dataNotFoundException (DataNotFoundException exception) {
        ErrorMessage error = errorMessageBuilder(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
