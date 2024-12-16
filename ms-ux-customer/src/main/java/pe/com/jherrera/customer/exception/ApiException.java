package pe.com.jherrera.customer.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 6088136638885458888L;

    @JsonProperty
    private String errorCategory;

    @JsonProperty
    private String description;

    @JsonProperty
    private Map details;

    @JsonIgnore
    private Integer errorCode;

    /**
     * Constructor.
     */
    public ApiException(String errorCategory, String description, HttpStatus httpStatus) {

        this.errorCategory = errorCategory;
        this.description = description;
        this.errorCode = httpStatus.value();
    }

}