package pe.com.jherrera.customer.business.impl;

import static pe.com.jherrera.customer.utils.ExceptionUtils.createApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.jherrera.customer.business.CustomerService;
import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.dto.UpdateCustomerRequest;
import pe.com.jherrera.customer.entity.CustomerEntity;
import pe.com.jherrera.customer.exception.ApiException;
import pe.com.jherrera.customer.mapper.CustomerMapper;
import pe.com.jherrera.customer.proxy.GoRestUserWebClient;
import pe.com.jherrera.customer.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio para procesar operaciones de de los cliente.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final GoRestUserWebClient goRestUserWebClient;
    private final CustomerMapper customerMapper;

    /**
     * Registra un cliente.
     *
     * @param request DTO con los datos necesarios del cliente.
     * @return Mono<CustomerResponse> response con los datos del cliente.
     */
    @Override
    public Mono<CustomerResponse> createCustomer(CustomerRequest request) {
        //Valida si existe email en otro usuario
        return emailExist(request.getEmail())
                .flatMap(emailExist -> {
                    if (emailExist) {
                        return Mono.error(new ApiException("Usuario en uso.", HttpStatus.CONFLICT));
                    }
                    //valida si cliente existe en gorest
                    return userExistsInGoRest(request.getName(), request.getEmail())
                            .flatMap(userExists -> {
                                CustomerEntity customerEntity = customerMapper.toEntity(request);
                                customerEntity.setStatus(userExists ? "exists" : "active");
                                customerEntity.setCreatedDate(LocalDateTime.now());
                                customerEntity.setModifiedDate(LocalDateTime.now());
                                return customerRepository.save(customerEntity)
                                        .map(customerMapper::toResponse);
                            });
                })
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.error(createApiException(e));
                });
    }

    /**
     * Actualiza un cliente.
     *
     * @param id id del cliente a actualizar.
     * @param request DTO con los datos necesarios del cliente
     * @return Mono<CustomerResponse> response con los datos del cliente.
     */
    @Override
    public Mono<CustomerResponse> updateCustomer(Long id, UpdateCustomerRequest request) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ApiException("No se encontró el cliente", HttpStatus.NOT_FOUND)))
                .flatMap(customer -> {

                    //Valida si existe el email en otro usuario
                    Mono<Boolean> validate;
                    if (!customer.getEmail().equalsIgnoreCase(request.getEmail())) {
                        validate = emailExist(request.getEmail());
                    } else {
                        validate = Mono.just(false);
                    }

                    return validate
                            .flatMap(emailExist -> {
                                if (emailExist) {
                                    return Mono.error(new ApiException("Usuario en uso.", HttpStatus.CONFLICT));
                                }
                                customerMapper.toEntity(customer, request);
                                customer.setModifiedDate(LocalDateTime.now());
                                return customerRepository.save(customer)
                                        .map(customerMapper::toResponse);
                            });
                })
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.error(createApiException(e));
                });
    }

    /**
     * Elimina un cliente.
     *
     * @param id id del cliente a eliminar.
     * @return Mono<ApiMessageResponse> mensaje de la operacion.
     */
    @Override
    public Mono<ApiMessageResponse> deleteCustomer(Long id) {
        return customerRepository.findById(id)
                .flatMap(customer -> {
                    return customerRepository.deleteById(id)
                            .then(Mono.just(new ApiMessageResponse("Operacion exitosa")));
                })
                .switchIfEmpty(Mono.error(new ApiException("No se encontró el cliente", HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.error(createApiException(e));
                });
    }

    /**
     * Obtiene todos los clientes registrados.
     *
     * @return Flux<CustomerResponse> lista de clientese encontrado.
     */
    @Override
    public Flux<CustomerResponse> searchAll() {
        return customerRepository.findAll()
                .map(customerMapper::toResponse)
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.error(createApiException(e));
                });
    }

    /**
     * Busca un cliente por su name o email.
     *
     * @param name nombre del cliente.
     * @param email email del cliente.
     * @return Mono<CustomerResponse> cleinte encontrado.
     */
    @Override
    public Mono<CustomerResponse> searchByNameOrEmail(String name, String email) {

        Mono<CustomerEntity> response = Optional.ofNullable(name)
                .map(customerRepository::findByNameIgnoreCase)
                .orElseGet(() -> Optional.ofNullable(email)
                        .map(customerRepository::findByEmailIgnoreCase)
                        .orElse(Mono.empty())
                );

        return response
                .map(customerMapper::toResponse)
                .switchIfEmpty(Mono.error(new ApiException("No se encontró el cliente", HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.error(createApiException(e));
                });

    }

    /**
     * valida si cliente existe en el servicio GoRest.
     *
     * @param name nombre del cliente.
     * @param email email del cliente.
     * @return Mono<Boolean> true o false.
     */
    private Mono<Boolean> userExistsInGoRest(String name, String email) {
        return goRestUserWebClient.getUsers()
                .filter(user -> user.getName().equalsIgnoreCase(name)
                        || user.getEmail().equalsIgnoreCase(email))
                .hasElements();
    }

    /**
     * valida si el email existe en otro cliente registrado.
     *
     * @param email email del cliente.
     * @return Mono<Boolean> true o false.
     */
    private Mono<Boolean> emailExist(String email) {
        return customerRepository.findByEmailIgnoreCase(email)
                .hasElement();
    }
}
