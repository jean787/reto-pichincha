package pe.com.jherrera.customer.business.impl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static pe.com.jherrera.customer.utils.TestUtils.generateObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.dto.GoRestUserResponse;
import pe.com.jherrera.customer.dto.UpdateCustomerRequest;
import pe.com.jherrera.customer.entity.CustomerEntity;
import pe.com.jherrera.customer.exception.ApiException;
import pe.com.jherrera.customer.mapper.CustomerMapper;
import pe.com.jherrera.customer.proxy.GoRestUserWebClient;
import pe.com.jherrera.customer.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;
    @Mock
    GoRestUserWebClient goRestUserWebClient;
    @Spy
    CustomerMapper customerMapper;

    CustomerRequest request;
    CustomerResponse response;
    UpdateCustomerRequest updateRequest;
    GoRestUserResponse goRestUserResponse;
    CustomerEntity customerEntity;

    @BeforeEach
    void setUp() throws IOException {
        request = generateObject("data/ApiCustomerRequest.json", CustomerRequest.class);
        response = generateObject("data/ApiCustomerResponse.json", CustomerResponse.class);
        updateRequest = generateObject("data/ApiUpdateCustomerRequest.json", UpdateCustomerRequest.class);
        goRestUserResponse = generateObject("data/GoRestUsers.json", GoRestUserResponse.class);
        customerEntity = generateObject("data/CustomerEntity.json", CustomerEntity.class);
    }

    @Test
    void createCustomerSuccess() {

        //When
        when(customerRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Mono.empty());
        when(goRestUserWebClient.getUsers())
                .thenReturn(Flux.just(goRestUserResponse));
        when(customerMapper.toEntity(any(CustomerRequest.class)))
                .thenReturn(customerEntity);
        when(customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(Mono.just(customerEntity));
        when(customerMapper.toResponse(any(CustomerEntity.class)))
                .thenReturn(response);

        //Then
        StepVerifier.create(customerService.createCustomer(request))
                .expectNextMatches(response -> response.getStatus().equalsIgnoreCase("active"))
                .verifyComplete();

    }

    @Test
    void createCustomerSuccess_userExists() {
        //Given
        request.setEmail("sumitra@example.test");

        //When
        when(customerRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Mono.empty());
        when(goRestUserWebClient.getUsers())
                .thenReturn(Flux.just(goRestUserResponse));
        when(customerMapper.toEntity(any(CustomerRequest.class)))
                .thenReturn(customerEntity);
        when(customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(Mono.just(customerEntity));
        when(customerMapper.toResponse(any(CustomerEntity.class)))
                .thenReturn(response);

        //Then
        StepVerifier.create(customerService.createCustomer(request))
                .expectNextMatches(response -> response.getStatus().equalsIgnoreCase("active"))
                .verifyComplete();

    }

    @Test
    void createCustomerFailure_EmailExist() {

        //When
        when(customerRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Mono.just(customerEntity));

        //Then
        StepVerifier.create(customerService.createCustomer(request))
                .expectErrorMatches(error -> error instanceof ApiException)
                .verify();

    }

    @Test
    void updateCustomerSuccess() {

        //When
        when(customerRepository.findById(anyLong()))
                .thenReturn(Mono.just(customerEntity));
        doNothing().when(customerMapper).toEntity(any(CustomerEntity.class), any(UpdateCustomerRequest.class));
        when(customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(Mono.just(customerEntity));
        when(customerMapper.toResponse(any(CustomerEntity.class)))
                .thenReturn(response);

        //Then
        StepVerifier.create(customerService.updateCustomer(1L, updateRequest))
                .expectNextMatches(response -> response != null)
                .verifyComplete();

    }

    @Test
    void updateCustomerFailed_idNotFound() {

        //When
        when(customerRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        //Then
        StepVerifier.create(customerService.updateCustomer(1L, updateRequest))
                .expectErrorMatches(error -> error instanceof ApiException)
                .verify();

    }

    @Test
    void updateCustomerFailed_emailExist() {
        //Given
        updateRequest.setEmail("other@example.test");

        //When
        when(customerRepository.findById(anyLong()))
                .thenReturn(Mono.just(customerEntity));
        when(customerRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Mono.just(new CustomerEntity()));

        //Then
        StepVerifier.create(customerService.updateCustomer(1L, updateRequest))
                .expectErrorMatches(error -> error instanceof ApiException)
                .verify();

    }

    @Test
    void deleteCustomerSuccess() {

        //When
        when(customerRepository.findById(anyLong()))
                .thenReturn(Mono.just(customerEntity));
        when(customerRepository.deleteById(anyLong()))
                .thenReturn(Mono.empty());

        //Then
        StepVerifier.create(customerService.deleteCustomer(1L))
                .expectNextMatches(response -> response.getMessage() != null)
                .verifyComplete();

    }

    @Test
    void deleteCustomerFailed_idNotFound() {

        //When
        when(customerRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        //Then
        StepVerifier.create(customerService.deleteCustomer(1L))
                .expectErrorMatches(error -> error instanceof ApiException)
                .verify();

    }

    @Test
    void searchAllCustomerSuccess() {

        //When
        when(customerRepository.findAll())
                .thenReturn(Flux.just(customerEntity));
        when(customerMapper.toResponse(any(CustomerEntity.class)))
                .thenReturn(response);

        //Then
        StepVerifier.create(customerService.searchAll())
                .expectNextMatches(response -> response.getStatus().equalsIgnoreCase("active"))
                .verifyComplete();

    }

    @Test
    void searchByNameCustomerSuccess() {

        //When
        when(customerRepository.findByNameIgnoreCase(anyString()))
                .thenReturn(Mono.just(customerEntity));
        when(customerMapper.toResponse(any(CustomerEntity.class)))
                .thenReturn(response);

        //Then
        StepVerifier.create(customerService.searchByNameOrEmail("test", null))
                .expectNextMatches(response -> response.getStatus().equalsIgnoreCase("active"))
                .verifyComplete();

    }

    @Test
    void searchByEmailCustomerSuccess() {

        //When
        when(customerRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Mono.just(customerEntity));
        when(customerMapper.toResponse(any(CustomerEntity.class)))
                .thenReturn(response);

        //Then
        StepVerifier.create(customerService.searchByNameOrEmail(null, "test"))
                .expectNextMatches(response -> response.getStatus().equalsIgnoreCase("active"))
                .verifyComplete();

    }

    @Test
    void searchByNameOrEmailCustomerFailed() {

        //Then
        StepVerifier.create(customerService.searchByNameOrEmail(null, null))
                .expectErrorMatches(error -> error instanceof ApiException)
                .verify();
    }
}
