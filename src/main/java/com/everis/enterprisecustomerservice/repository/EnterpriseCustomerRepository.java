package com.everis.enterprisecustomerservice.repository;

import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EnterpriseCustomerRepository extends ReactiveMongoRepository<EnterpriseCustomer, String> {

    Mono<EnterpriseCustomer> findByRuc(String ruc);
}
