package com.everis.enterprisecustomerservice.services.impl;

import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnterpriseCustomerService {


    Mono<EnterpriseCustomerResponse> create (EnterpriseCustomer customer);

    Flux<EnterpriseCustomer> findAll ();

    Mono<EnterpriseCustomer> findByRuc (String ruc);

    Mono<EnterpriseCustomerResponse> update(EnterpriseCustomer customer, String id);

    Mono<EnterpriseCustomerResponse> disable(String id);
}
