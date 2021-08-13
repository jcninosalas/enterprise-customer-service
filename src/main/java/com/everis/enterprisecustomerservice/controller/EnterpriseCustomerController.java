package com.everis.enterprisecustomerservice.controller;

import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomerResponse;
import com.everis.enterprisecustomerservice.services.impl.EnterpriseCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/e-customers")
@Validated
public class EnterpriseCustomerController {

    @Autowired
    private EnterpriseCustomerService service;

    @PostMapping
    public Mono<EnterpriseCustomerResponse> createCustomer(@Valid @RequestBody EnterpriseCustomer customer) {
        return service.create(customer);
    }

    @GetMapping("/e-customer")
    public Mono<EnterpriseCustomer> getCustomerByRuc(
            @RequestParam @Size(min = 11, max = 11, message = "Ingresar un numero de RUC valido") String ruc) {
        return service.findByRuc(ruc);
    }

    @GetMapping
    public Flux<EnterpriseCustomer> getAllCustomer() {
        return service.findAll();
    }

    @PutMapping("/e-customer")
    public Mono<EnterpriseCustomerResponse> updateCustomer(@ Valid @RequestBody EnterpriseCustomer customer,
                                                           @RequestParam String id) {
        return service.update(customer, id);
    }

    @DeleteMapping("/customer")
    public Mono<EnterpriseCustomerResponse> disableCustomer(@RequestParam String id) {
        return service.disable(id);
    }
}
