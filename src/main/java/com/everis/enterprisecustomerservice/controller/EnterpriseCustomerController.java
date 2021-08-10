package com.everis.enterprisecustomerservice.controller;

import com.everis.enterprisecustomerservice.exception.CustomerException;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import com.everis.enterprisecustomerservice.services.EnterpriseCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EnterpriseCustomerController {

    @Autowired
    private EnterpriseCustomerService service;

    @PostMapping("/e-customer/new")
    public Mono<ResponseEntity<Object>> createCustomer(@RequestBody EnterpriseCustomer customer) {
        return service.create(customer)
                .flatMap(c -> {
                    if (c == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(c));
                });
    }

    @GetMapping("/e-customer/find")
    public Mono<ResponseEntity<EnterpriseCustomer>> getCustomerByRuc(@RequestParam String ruc) {
        return service.getByRuc(ruc);
    }

    @GetMapping("/e-customer")
    public Flux<EnterpriseCustomer> getAllCustomer() {
        return service.findAll();
    }

    @PutMapping("/e-customer/disable")
    public Mono<ResponseEntity<EnterpriseCustomer>> disableCustomer(@RequestParam String ruc) {
        return service.disable(ruc)
                .flatMap( c -> Mono.just(ResponseEntity.ok(c)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/e-customer/update")
    public Mono<ResponseEntity<EnterpriseCustomer>> updateCustomer(@RequestBody EnterpriseCustomer customer) {
        return service.update(customer)
                .flatMap(c -> Mono.just(ResponseEntity.ok(c)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
