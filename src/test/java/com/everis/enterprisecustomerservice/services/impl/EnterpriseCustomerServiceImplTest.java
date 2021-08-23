package com.everis.enterprisecustomerservice.services.impl;

import com.everis.enterprisecustomerservice.exception.CustomerNotFoundException;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomer;
import com.everis.enterprisecustomerservice.model.EnterpriseCustomerResponse;
import com.everis.enterprisecustomerservice.repository.EnterpriseCustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;



@ExtendWith(MockitoExtension.class)
class EnterpriseCustomerServiceImplTest {

    @Mock
    private EnterpriseCustomerRepository repository;

    @InjectMocks
    private EnterpriseCustomerServiceImpl service;

    private final String DNI = "12345678";
    private final String COMPANY_NAME = "everis";

    @BeforeEach
    void init() {
        EnterpriseCustomer customer = new EnterpriseCustomer();
        customer.setRuc("12345678912");
        customer.setCreatedAt(new Date());
        customer.setContactDni("12345678");
        customer.setCompanyName("everis");
        when(repository.findByRuc(any(String.class)))
                .thenReturn(Mono.just(customer));
    }

    @Test
    void findByRuc() {
        StepVerifier.create(service.findByRuc("12345678912"))
                .assertNext(customer -> {
                            Assertions.assertEquals(COMPANY_NAME, customer.getCompanyName());
                            Assertions.assertEquals(DNI, customer.getContactDni());
                })
                .verifyComplete();
    }

    @Test
    void saveCustomer() {

        when(repository.insert(any(EnterpriseCustomer.class)))
                .then((Answer<EnterpriseCustomer>) invocationOnMock -> {
                    EnterpriseCustomer ec = new EnterpriseCustomer();
                    ec.setId("ASDF");
                    return ec;
                });
        EnterpriseCustomer customer = new EnterpriseCustomer();
        customer.setRuc("12345678912");
        customer.setCreatedAt(new Date());
        customer.setContactDni("98765412");
        Mono<EnterpriseCustomerResponse> result = service.create(customer);

//        StepVerifier.create(result).assertNext(
//                savedCustomer -> savedCustomer.
//        );
    }

    @Test
    void customerNotFound() {
        when(repository.findByRuc(any()))
                .thenThrow(new CustomerNotFoundException());
        StepVerifier.create(repository.findByRuc("09878798"))
                .expectError(CustomerNotFoundException.class)
                .verify();
    }

}