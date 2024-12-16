package pe.com.jherrera.customer.proxy;

import pe.com.jherrera.customer.proxy.model.RestApiMessageResponse;
import pe.com.jherrera.customer.proxy.model.RestCustomerRequest;
import pe.com.jherrera.customer.proxy.model.RestCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerWebClient {

    Flux<RestCustomerResponse> searchAll();
    Mono<RestCustomerResponse> searchByNameOrEmail(String name, String email);
    Mono<RestCustomerResponse> save(RestCustomerRequest request);
    Mono<RestCustomerResponse> update(Long id, RestCustomerRequest request);
    Mono<RestApiMessageResponse> delete(Long id);
}
