package pe.com.jherrera.customer.utils;

import org.springframework.http.HttpStatus;
import pe.com.jherrera.customer.exception.ApiException;

/**
 * Clase Util para uso comun en las excepciones.
 */
public class ExceptionUtils {

    /**
     * Genera una clase ApiException.
     *
     * @param e Exception.
     * @return ApiException.
     */
    public static ApiException createApiException(Throwable e) {
        if (e instanceof ApiException) {
            return (ApiException) e;
        }
        return new ApiException("Ocurrior un error interno", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
