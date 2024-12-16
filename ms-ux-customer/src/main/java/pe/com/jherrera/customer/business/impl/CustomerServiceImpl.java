package pe.com.jherrera.customer.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.jherrera.customer.business.CustomerService;
import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.mapper.CustomerMapper;
import pe.com.jherrera.customer.proxy.CustomerWebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para procesar operaciones de de los cliente.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerWebClient customerWebClient;
    private final CustomerMapper mapper;

    /**
     * Busca un cliente por su name o email.
     *
     * @param name nombre del cliente.
     * @param email email del cliente.
     * @return Mono<CustomerResponse> cleinte encontrado.
     */
    @Override
    public Mono<CustomerResponse> findByNameOrEmail(String name, String email) {
        return customerWebClient.searchByNameOrEmail(name, email)
                .map(mapper::toResponse);
    }

    /**
     * Obtiene todos los clientes registrados.
     *
     * @return Flux<CustomerResponse> lista de clientese encontrado.
     */
    @Override
    public Flux<CustomerResponse> findAll() {
        return customerWebClient.searchAll()
                .map(mapper::toResponse);
    }

    /**
     * Registra un cliente.
     *
     * @param request DTO con los datos necesarios del cliente.
     * @return Mono<CustomerResponse> response con los datos del cliente.
     */
    @Override
    public Mono<CustomerResponse> save(CustomerRequest request) {
        return customerWebClient.save(mapper.toRequest(request))
                .map(mapper::toResponse);
    }

    /**
     * Actualiza un cliente.
     *
     * @param id id del cliente a actualizar.
     * @param request DTO con los datos necesarios del cliente
     * @return Mono<CustomerResponse> response con los datos del cliente.
     */
    @Override
    public Mono<CustomerResponse> update(Long id, CustomerRequest request) {
        return customerWebClient.update(id, mapper.toRequest(request))
                .map(mapper::toResponse);
    }

    /**
     * Elimina un cliente.
     *
     * @param id id del cliente a eliminar.
     * @return Mono<ApiMessageResponse> mensaje de la operacion.
     */
    @Override
    public Mono<ApiMessageResponse> delete(Long id) {
        return customerWebClient.delete(id)
                .map(mapper::toApiResponse);
    }
}
