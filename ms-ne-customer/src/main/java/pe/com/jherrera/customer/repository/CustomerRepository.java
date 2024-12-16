package pe.com.jherrera.customer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.com.jherrera.customer.entity.CustomerEntity;
import reactor.core.publisher.Mono;

/**
 * Repositorio reactivo para gestionar los clientes.
 * Proporciona operaciones CRUD y búsqueda personalizada por nombre de email.
 */
public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {

    /**
     * Busca un cliente basado en su nombre.
     *
     * @param name Nombre del cliente.
     * @return Un Mono con el cliente encontrado, o vacío si no existe.
     */
    Mono<CustomerEntity> findByNameIgnoreCase(String name);

    /**
     * Busca un cliente basado en su email.
     *
     * @param email Email del cliente.
     * @return Un Mono con el cliente encontrado, o vacío si no existe.
     */
    Mono<CustomerEntity> findByEmailIgnoreCase(String email);
}
