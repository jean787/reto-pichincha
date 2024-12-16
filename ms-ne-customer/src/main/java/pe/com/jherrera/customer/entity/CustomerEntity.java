package pe.com.jherrera.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("customer")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String gender;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
