package pe.com.jherrera.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String gender;
    private String status;
}
