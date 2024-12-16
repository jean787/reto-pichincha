package pe.com.jherrera.customer.proxy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.com.jherrera.customer.proxy.CustomerWebClient;
import pe.com.jherrera.customer.proxy.model.RestApiMessageResponse;
import pe.com.jherrera.customer.proxy.model.RestCustomerRequest;
import pe.com.jherrera.customer.proxy.model.RestCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio cliente para interactuar con la API de Soporte Customer.
 * Proporciona funcionalidades para obtener información de clientes desde la API remota.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerWebClientImpl implements CustomerWebClient {

    private final WebClient webClientCustomer;

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return Una lista de los clientes.
     */
    @Override
    public Flux<RestCustomerResponse> searchAll() {
        return webClientCustomer.get()
                .uri("/api/customer")
                .retrieve()
                .bodyToFlux(RestCustomerResponse.class);
    }

    /**
     * buscar un cliente por name o email.
     *
     * @param name nombre del cliente.
     * @param email email del clienteo.
     * @return Mono<RestCustomerResponse> Cliente correspondiente al name o email.
     */
    @Override
    public Mono<RestCustomerResponse> searchByNameOrEmail(String name, String email) {
        return webClientCustomer.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customer/by")
                        .queryParam("name", name)
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToMono(RestCustomerResponse.class);
    }

    /**
     * Registra un cliente.
     *
     * @param request request para crear un cliente.
     * @return El cliente registrado.
     */
    @Override
    public Mono<RestCustomerResponse> save(RestCustomerRequest request) {
        return webClientCustomer.post()
                .uri("/api/customer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RestCustomerResponse.class);
    }

    /**
     * Actuliza un cliente por ID.
     *
     * @param id ID del cliente a actulizar.
     * @param request request para actualizar un cliente.
     * @return El cliente actualizado.
     */
    @Override
    public Mono<RestCustomerResponse> update(Long id, RestCustomerRequest request) {
        return webClientCustomer.put()
                .uri("/api/customer/"+id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RestCustomerResponse.class);
    }

    /**
     * Elimina un cliente por ID.
     *
     * @param id ID del cliente a elimar.
     * @return mensaje correspondiente a la operacion.
     */
    @Override
    public Mono<RestApiMessageResponse> delete(Long id) {
        return webClientCustomer.delete()
                .uri("/api/customer/"+id)
                .retrieve()
                .bodyToMono(RestApiMessageResponse.class);
    }
}
