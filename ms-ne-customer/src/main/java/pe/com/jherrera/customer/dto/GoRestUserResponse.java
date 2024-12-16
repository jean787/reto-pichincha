package pe.com.jherrera.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoRestUserResponse {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
