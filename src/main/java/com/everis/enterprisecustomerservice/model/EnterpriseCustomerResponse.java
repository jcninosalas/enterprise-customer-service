package com.everis.enterprisecustomerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class EnterpriseCustomerResponse {
    private String message;
    private Map<String, Object> body;
}
