package pe.com.jherrera.customer.business.impl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static pe.com.jherrera.customer.utils.TestUtils.generateObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.mapper.CustomerMapper;
import pe.com.jherrera.customer.proxy.CustomerWebClient;
import pe.com.jherrera.customer.proxy.model.RestApiMessageResponse;
import pe.com.jherrera.customer.proxy.model.RestCustomerRequest;
import pe.com.jherrera.customer.proxy.model.RestCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.io.IOException;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerWebClient customerWebClient;
    @Mock
    CustomerMapper mapper;

    CustomerRequest request;

    @BeforeEach
    void setUp() throws IOException {
        request = generateObject("data/customerRequest.json", CustomerRequest.class);
    }

    @Test
    void findAllSuccess() {
        when(customerWebClient.searchAll())
                .thenReturn(Flux.just(new RestCustomerResponse()));
        when(mapper.toResponse(any(RestCustomerResponse.class)))
                .thenReturn(new CustomerResponse());

        StepVerifier.create(customerService.findAll())
                .expectNextMatches(response -> response != null)
                .verifyComplete();
    }

    @Test
    void findByNameSuccess() {
        when(customerWebClient.searchByNameOrEmail(anyString(), anyString()))
                .thenReturn(Mono.just(new RestCustomerResponse()));
        when(mapper.toResponse(any(RestCustomerResponse.class)))
                .thenReturn(new CustomerResponse());

        StepVerifier.create(customerService.findByNameOrEmail("test", "test"))
                .expectNextMatches(response -> response != null)
                .verifyComplete();
    }

    @Test
    void saveCustomerSuccess() {
        when(mapper.toRequest(any(CustomerRequest.class)))
                .thenReturn(new RestCustomerRequest());
        when(customerWebClient.save(any(RestCustomerRequest.class)))
                .thenReturn(Mono.just(new RestCustomerResponse()));
        when(mapper.toResponse(any(RestCustomerResponse.class)))
                .thenReturn(new CustomerResponse());

        StepVerifier.create(customerService.save(request))
                .expectNextMatches(response -> response != null)
                .verifyComplete();
    }

    @Test
    void updateCustomerSuccess() {
        when(mapper.toRequest(any(CustomerRequest.class)))
                .thenReturn(new RestCustomerRequest());
        when(customerWebClient.update(anyLong(), any(RestCustomerRequest.class)))
                .thenReturn(Mono.just(new RestCustomerResponse()));
        when(mapper.toResponse(any(RestCustomerResponse.class)))
                .thenReturn(new CustomerResponse());

        StepVerifier.create(customerService.update(1L, request))
                .expectNextMatches(response -> response != null)
                .verifyComplete();
    }

    @Test
    void deleteCustomerSuccess() {

        when(customerWebClient.delete(anyLong()))
                .thenReturn(Mono.just(new RestApiMessageResponse()));
        when(mapper.toApiResponse(any(RestApiMessageResponse.class)))
                .thenReturn(new ApiMessageResponse());

        StepVerifier.create(customerService.delete(1L))
                .expectNextMatches(response -> response != null)
                .verifyComplete();
    }
}
