package com.server.loan.calculator.error;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorMessage {
    private int statusCode;
    private Instant timestamp;
    private String message;
}
