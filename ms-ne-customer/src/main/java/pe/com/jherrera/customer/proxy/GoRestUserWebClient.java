package pe.com.jherrera.customer.proxy;

import pe.com.jherrera.customer.dto.GoRestUserResponse;
import reactor.core.publisher.Flux;

public interface GoRestUserWebClient {

    Flux<GoRestUserResponse> getUsers();
}
