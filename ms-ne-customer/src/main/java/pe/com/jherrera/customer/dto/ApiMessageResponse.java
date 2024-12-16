package pe.com.jherrera.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMessageResponse {
    private String message;

    public ApiMessageResponse(String message) {
        this.message = message;
    }

}
