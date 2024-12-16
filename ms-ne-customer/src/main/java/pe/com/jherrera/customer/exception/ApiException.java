package pe.com.jherrera.customer.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción para manejar errores generales de la API.
 */
@Getter
@JsonAutoDetect(
        creatorVisibility = Visibility.NONE,
        fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 6088136638885458888L;

    @JsonProperty
    private String message;
    @JsonIgnore
    private Integer errorCode;

  /**
   * Constructor.
   *
   * @param message Mensaje de error.
   * @param errorCategory codigo de error.
   */
    public ApiException(String message, HttpStatus errorCategory) {
        super(message);

        this.message = message;
        this.errorCode = errorCategory.value();
    }
}
