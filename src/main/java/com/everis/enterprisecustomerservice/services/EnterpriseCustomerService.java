package com.everis.enterprisecustomerservice.services;

import com.everis.enterprisecustomerservice.exception.CustomerException;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import com.everis.enterprisecustomerservice.repository.EnterpriseCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

@Service
public class EnterpriseCustomerService {

    @Autowired
    private EnterpriseCustomerRepository repository;

    public Flux<EnterpriseCustomer> findAll() {
        return repository.findAll();
    }

    public Mono<ResponseEntity<EnterpriseCustomer>> getByRuc(String accountNumber)  {
        return repository.findByRuc(accountNumber)
                .map(a -> ResponseEntity.ok().body(a))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<EnterpriseCustomer> create(EnterpriseCustomer customer) {
        return repository.findByRuc(customer.getRuc())
                .switchIfEmpty(repository.save(setNewEnterpriseCustomer(customer)));
    }

    public Mono<EnterpriseCustomer> update( EnterpriseCustomer customer) {
        return repository.findByRuc(customer.getRuc())
                .flatMap( a -> {
                    customer.setId(a.getId());
                    return repository.save(customer);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<EnterpriseCustomer> disable(String ruc) {
        return repository.findByRuc(ruc)
                .flatMap( a -> {
                    a.setIsActive(false);
                    return repository.save(a);
                })
                .switchIfEmpty(Mono.empty());
    }

    private EnterpriseCustomer setNewEnterpriseCustomer(EnterpriseCustomer customer) {
       customer.setCreatedAt(new Date());
       customer.setIsActive(true);
      // return Mono.just(customer);
        return customer;
    }

}
