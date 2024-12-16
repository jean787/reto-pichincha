package pe.com.jherrera.customer.business;

import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.dto.UpdateCustomerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<CustomerResponse> createCustomer(CustomerRequest request);
    Mono<CustomerResponse> updateCustomer(Long id, UpdateCustomerRequest request);
    Mono<ApiMessageResponse> deleteCustomer(Long id);
    Flux<CustomerResponse> searchAll();
    Mono<CustomerResponse> searchByNameOrEmail(String name, String email);
}
