package pe.com.jherrera.customer.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.jherrera.customer.business.CustomerService;
import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.dto.UpdateCustomerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador para gestionar las operaciones del cliente.
 * Proporciona endpoints para registrar, listar, actualizar y eliminar los clientes.
 */
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Endpoint para listar todos los clientes.
     *
     * @return Una lista de los clientes.
     */
    @GetMapping
    public ResponseEntity<Flux<CustomerResponse>> searchAll() {
        return ResponseEntity.ok(customerService.searchAll());
    }

    /**
     * Endpoint para buscar un cliente por name o email.
     *
     * @param name nombre del cliente.
     * @param email email del clienteo.
     * @return Cliente correspondiente al name o email.
     */
    @GetMapping("/by")
    public ResponseEntity<Mono<CustomerResponse>> searchByNameOrEmail(
            @Valid @RequestParam(required = false) @Pattern(regexp = "^[a-zA-Z\\s]{0,50}$") String name,
            @Valid @RequestParam(required = false) @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]+") String email) {
        return ResponseEntity.ok(customerService.searchByNameOrEmail(name, email));
    }

    /**
     * Endpoint para registrar un cliente.
     *
     * @param request DTO con los datos necesarios para crear un cliente.
     * @return El cliente registrado.
     */
    @PostMapping
    public ResponseEntity<Mono<CustomerResponse>> save(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    /**
     * Endpoint para actulizar un cliente por ID.
     *
     * @param id ID del cliente a actulizar.
     * @param request DTO con los datos necesarios para crear un cliente.
     * @return El cliente actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mono<CustomerResponse>> update(@PathVariable Long id, @RequestBody @Valid UpdateCustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    /**
     * Endpoint para eliminar un cliente por ID.
     *
     * @param id ID del cliente a elimar.
     * @return mensaje correspondiente a la operacion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<ApiMessageResponse>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
