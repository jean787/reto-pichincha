package pe.com.jherrera.customer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.com.jherrera.customer.business.CustomerService;
import pe.com.jherrera.customer.business.impl.CustomerServiceImpl;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.UpdateCustomerRequest;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static pe.com.jherrera.customer.utils.TestUtils.generateObject;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CustomerController.class)
@Import(CustomerServiceImpl.class)
public class CustomerControllerTest {

    private WebTestClient webTestClient;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    public CustomerControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    CustomerRequest request;
    UpdateCustomerRequest updateRequest;

    @BeforeEach
    void setUp() throws IOException {
        request = generateObject("data/ApiCustomerRequest.json", CustomerRequest.class);
        updateRequest = generateObject("data/ApiUpdateCustomerRequest.json", UpdateCustomerRequest.class);
    }

    @Test
    void searchAllCustomerOk() {
        webTestClient.get()
                .uri("/api/customer")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchCustomerOk() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customer/by")
                        .queryParam("name", "test")
                        .queryParam("email", "test@test.com")
                        .build()
                )
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchCustomerBadRequest() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customer/by")
                        .queryParam("name", "test")
                        .queryParam("email", "testtest.com")
                        .build()
                )
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createCustomerOk() {
        webTestClient.post()
                .uri("/api/customer")
                .body(Mono.just(request), CustomerRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void createCustomerBadRequest() {
        webTestClient.post()
                .uri("/api/customer")
                .body(Mono.just(new CustomerRequest()), CustomerRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateCustomerOk() {
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customer/{id}")
                        .build(1)
                )
                .body(Mono.just(updateRequest), UpdateCustomerRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateCustomerBadRequest() {
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customer/{id}")
                        .build(1)
                )
                .body(Mono.just(new UpdateCustomerRequest()), UpdateCustomerRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deleteCustomerOk() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customer/{id}")
                        .build(1)
                )
                .exchange()
                .expectStatus().isOk();
    }

}
