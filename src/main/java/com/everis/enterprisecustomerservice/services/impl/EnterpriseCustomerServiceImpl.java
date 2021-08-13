package com.everis.enterprisecustomerservice.services.impl;

import com.everis.enterprisecustomerservice.exception.CustomerNotFoundException;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomerResponse;
import com.everis.enterprisecustomerservice.repository.EnterpriseCustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@Slf4j
public class EnterpriseCustomerServiceImpl implements com.everis.enterprisecustomerservice.services.impl.EnterpriseCustomerService {

    @Autowired
    private EnterpriseCustomerRepository repository;

    @Override
    public Mono<EnterpriseCustomerResponse> create(EnterpriseCustomer customer) {
        return repository.insert(setNewCustomer.apply(customer))
                .flatMap(customerResponse);
    }

    @Override
    public Flux<EnterpriseCustomer> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<EnterpriseCustomer> findByRuc(String ruc) {
        return repository.findByRuc(ruc)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException()));
    }

    @Override
    public Mono<EnterpriseCustomerResponse> update(EnterpriseCustomer customer, String id) {
        return repository.findById(id)
                .map(c -> updateCustomer.apply(c, customer))
                .flatMap(repository::save)
                .flatMap(customerResponse)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException()));
    }

    @Override
    public Mono<EnterpriseCustomerResponse> disable(String id) {
        return repository.findById(id)
                .map(disableCustomer)
                .flatMap(disableResponse);
    }

    private final Function<EnterpriseCustomer, EnterpriseCustomer> setNewCustomer = customer -> {
        customer.setIsActive(true);
        customer.setCreatedAt(Date.from(Instant.now()));
        return customer;
    };

    private final Function<EnterpriseCustomer, Mono<EnterpriseCustomerResponse>> customerResponse =  customer ->
        Mono.just(new EnterpriseCustomerResponse(
           "El registro ha sido realizado con exito",
           Map.of("customer", customer)
        ));

    private final Function<EnterpriseCustomer, Mono<EnterpriseCustomerResponse>> disableResponse =  customer ->
            Mono.just(new EnterpriseCustomerResponse(
                    "El cliente ha sido desahabilitato",
                    Map.of("customer", customer)
            ));

    private final BiFunction<EnterpriseCustomer, EnterpriseCustomer, EnterpriseCustomer> updateCustomer =
            (customer, customerToUpdate) -> {
                customerToUpdate.setIsActive(true);
                customerToUpdate.setId(customer.getId());
                customerToUpdate.setModifiedAt(Date.from(Instant.now()));
                customerToUpdate.setCreatedAt(customer.getCreatedAt());
                log.info("actualizado : {}", customerToUpdate);
                return customerToUpdate;
            };


    private final Function<EnterpriseCustomer, EnterpriseCustomer> disableCustomer = customer -> {
        customer.setIsActive(false);
        return customer;
    };
}
