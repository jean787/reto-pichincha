package pe.com.jherrera.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCustomerRequest extends CustomerRequest {

    @NotEmpty
    @Pattern(regexp = "^(active|inactive|exists)$")
    @Size(min = 0, max = 15)
    private String status;
}
