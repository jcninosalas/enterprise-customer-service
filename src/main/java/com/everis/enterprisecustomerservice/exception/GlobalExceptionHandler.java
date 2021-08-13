package com.everis.enterprisecustomerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handling custom validation errors
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> customValidationErrorHandling(WebExchangeBindException exception) {
        var errorDetails = new ErrorDetails(
             new Date(),
             "Validation error",
                Objects.requireNonNull(exception.getBindingResult()
                                .getFieldError())
                                .getDefaultMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //handling customer not found errors
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> creditLineNotFoundErrorHandling(CustomerNotFoundException exception) {
        var errorDetails = new ErrorDetails(
                new Date(),
                "Credit line not found error",
                exception.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
