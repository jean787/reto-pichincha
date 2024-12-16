package pe.com.jherrera.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetailErrorEnum {

    GENERIC_ERROR("SYSTEM", "Error Generico"),
    FIELD_ERROR("SYSTEM", "Error de campo(s)"),
    SERVICE_EXTERNAL("FUNCTIONAL", "ERROR DE SERVICIO EXTERNO: [Error al comunicarse con el servicio externo]"),
    ERROR_VALIDATION("SYSTEM", "Ocurrió un error de validación en los campos.");

    private String category;
    private String description;
}
