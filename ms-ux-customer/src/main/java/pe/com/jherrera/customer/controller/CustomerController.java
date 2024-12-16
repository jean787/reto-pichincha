package pe.com.jherrera.customer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.jherrera.customer.business.CustomerService;
import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.exception.ApiException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador para gestionar las operaciones del cliente.
 * Proporciona endpoints para registrar, listar, actualizar y eliminar los clientes.
 */
@RestController
@RequestMapping("/ux/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Endpoint para listar todos los clientes.
     *
     * @return Una lista de los clientes.
     */
    @Operation(
            operationId = "searchAll",
            summary = "Obtener clientes",
            description = "Lista los clientes registrados.",
            tags = { "Customer" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    })
            }
    )
    @GetMapping
    public ResponseEntity<Flux<CustomerResponse>> searchAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    /**
     * Endpoint para buscar un cliente por name o email.
     *
     * @param name nombre del cliente.
     * @param email email del clienteo.
     * @return Cliente correspondiente al name o email.
     */
    @Operation(
            operationId = "searchByNameOrEmail",
            summary = "Buscar cliente",
            description = "Realiza la busqueda de clientes por nombre o email.",
            tags = { "Customer" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    })
            }
    )
    @GetMapping("/by")
    public ResponseEntity<Mono<CustomerResponse>> searchByNameOrEmail(
            @Valid @RequestParam(required = false) @Pattern(regexp = "^[a-zA-Z\\s]{0,50}$") String name,
            @Valid @RequestParam(required = false) @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]+") String email) {
        return ResponseEntity.ok(customerService.findByNameOrEmail(name, email));
    }

    /**
     * Endpoint para registrar un cliente.
     *
     * @param request DTO con los datos necesarios para crear un cliente.
     * @return El cliente registrado.
     */
    @Operation(
            operationId = "save",
            summary = "Registra un nuevo cliente",
            description = "Realiza el registro de un nuevo cliente",
            tags = { "Customer" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    })
            }
    )
    @PostMapping
    public ResponseEntity<Mono<CustomerResponse>> save(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.save(request));
    }

    /**
     * Endpoint para actulizar un cliente por ID.
     *
     * @param id ID del cliente a actulizar.
     * @param request DTO con los datos necesarios para crear un cliente.
     * @return El cliente actualizado.
     */
    @Operation(
            operationId = "update",
            summary = "Actualizar cliente",
            description = "Actualiza un cliente por ID",
            tags = { "Customer" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    })
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Mono<CustomerResponse>> update(@PathVariable Long id, @RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.update(id, request));
    }

    /**
     * Endpoint para eliminar un cliente por ID.
     *
     * @param id ID del cliente a elimar.
     * @return mensaje correspondiente a la operacion.
     */
    @Operation(
            operationId = "delete",
            summary = "Eliminar cliente",
            description = "Elimina un cliente por ID",
            tags = { "Customer" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessageResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "BAD REQUEST", content = {
                            @Content(schema = @Schema(implementation = ApiException.class))
                    })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<ApiMessageResponse>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.delete(id));
    }
}
