package pe.com.jherrera.customer.proxy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.com.jherrera.customer.dto.GoRestUserResponse;
import pe.com.jherrera.customer.proxy.GoRestUserWebClient;
import reactor.core.publisher.Flux;

/**
 * Servicio cliente para interactuar con la API de GoRest.
 * Proporciona funcionalidades para obtener información de usuarios desde la API remota.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GoRestUserWebClientImpl implements GoRestUserWebClient {

    private final WebClient gorestWebClient;

    /**
     * Obtiene la lista de usuarios desde la API de GoRest.
     *
     * @return Un Flux con la lista de usuarios.
     */
    @Override
    public Flux<GoRestUserResponse> getUsers() {
        return gorestWebClient
                .get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(GoRestUserResponse.class)
                .log();
    }
}
