package pe.com.jherrera.customer.business;

import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<CustomerResponse> findByNameOrEmail(String name, String email);
    Flux<CustomerResponse> findAll();
    Mono<CustomerResponse> save(CustomerRequest request);
    Mono<CustomerResponse> update(Long id, CustomerRequest request);
    Mono<ApiMessageResponse> delete(Long id);
}
