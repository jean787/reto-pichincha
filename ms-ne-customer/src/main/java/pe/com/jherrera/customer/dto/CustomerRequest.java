package pe.com.jherrera.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerRequest {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z\\s]+")
    @Size(min = 0, max = 50)
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+")
    @Size(min = 0, max = 50)
    private String address;

    @Pattern(regexp = "^[0-9]+")
    @Size(min = 0, max = 15)
    private String phone;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]+")
    @Size(min = 0, max = 50)
    private String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]+")
    @Size(min = 0, max = 10)
    private String gender;
}
