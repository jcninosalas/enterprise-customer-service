package com.everis.enterprisecustomerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CustomerException extends Exception {

    private String message;
    private HttpStatus httpStatus;

}
