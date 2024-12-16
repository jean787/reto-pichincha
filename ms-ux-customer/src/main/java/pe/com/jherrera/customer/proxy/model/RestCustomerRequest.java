package pe.com.jherrera.customer.proxy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestCustomerRequest {

    private String name;
    private String address;
    private String phone;
    private String email;
    private String gender;
    private String status;
}
