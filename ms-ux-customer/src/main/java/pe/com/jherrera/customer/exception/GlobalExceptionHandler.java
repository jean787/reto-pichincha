package pe.com.jherrera.customer.exception;

import static pe.com.jherrera.customer.exception.DetailErrorEnum.ERROR_VALIDATION;
import static pe.com.jherrera.customer.exception.DetailErrorEnum.SERVICE_EXTERNAL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones de la API.
 * Proporciona respuestas personalizadas para las excepciones controladas.
 */
@Slf4j
@RestControllerAdvice
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

        ApiException apiException = ApiException.builder()
                .errorCategory(ERROR_VALIDATION.getCategory())
                .description(ERROR_VALIDATION.getDescription())
                .details(errors)
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de webclient.
     *
     * @param ex Excepción de tipo WebClientResponseException.
     * @return Un ApiException con el mensaje y codigo de error.
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<?> handleWebClientResponseException(WebClientResponseException ex) {
        log.error(ex.getMessage(), ex);
        ApiException apiException = ApiException.builder()
                .errorCategory(SERVICE_EXTERNAL.getCategory())
                .description(SERVICE_EXTERNAL.getDescription())
                .details(ex.getResponseBodyAs(Map.class))
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(apiException);
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

        ApiException apiException = ApiException.builder()
                .errorCategory(ERROR_VALIDATION.getCategory())
                .description(ERROR_VALIDATION.getDescription())
                .details(errors)
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
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
     * Maneja excepciones generales.
     *
     * @param ex Excepción de tipo Exception.
     * @return Un mensaje de error genérico con estado HTTP 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        log.error(ex.getMessage(), ex);
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
