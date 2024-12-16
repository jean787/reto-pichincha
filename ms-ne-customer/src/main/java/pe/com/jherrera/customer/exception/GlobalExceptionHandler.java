package pe.com.jherrera.customer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones de la API.
 * Proporciona respuestas personalizadas para las excepciones controladas.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de validación de argumentos no válidos en nuestro DTO.
     *
     * @param ex Excepción de tipo WebExchangeBindException.
     * @return Un mapa con los errores de validación y un estado HTTP 400.
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handleValidationExceptions(
            WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "error";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de validación para los parametros no validos.
     *
     * @param ex Excepción de tipo HandlerMethodValidationException.
     * @return Un mapa con los errores de validación y un estado HTTP 400.
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleValidationParameterExceptions(
            HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(error -> {
            String fieldName = error.getMethodParameter().getParameter().getName();
            String errorMessage = error.getResolvableErrors().get(0).getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja las excepciones controlados con la ApiException.
     *
     * @param ex Excepción de tipo ApiException.
     * @return Un ApiException con el mensaje y codigo de error.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getErrorCode()).body(ex);
    }

    /**
     * Maneja las excepciones no controlados.
     *
     * @param ex Excepción.
     * @return Un ApiException con el mensaje de error genrado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGlobalException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
