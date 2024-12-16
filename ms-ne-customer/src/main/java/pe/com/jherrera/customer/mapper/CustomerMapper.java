package pe.com.jherrera.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.dto.UpdateCustomerRequest;
import pe.com.jherrera.customer.entity.CustomerEntity;

/**
 * Servicio que realiza el mapeo de los objectos Customer.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Transforma el Objecto DTO a un Entity.
     *
     * @param customerRequest DTO customerRequest.
     * @return CustomerEntity entity.
     */
    CustomerEntity toEntity(CustomerRequest customerRequest);

    /**
     * Transforma el Objecto DTO inicializado a un Entity.
     *
     * @param entity entity inicializado.
     * @param customerRequest DTO customerRequest.
     * @return void.
     */
    void toEntity(@MappingTarget CustomerEntity entity, UpdateCustomerRequest customerRequest);

    /**
     * Transforma el Objecto Entity a un DTO response.
     *
     * @param customerEntity entity.
     * @return CustomerResponse DTO response transformado.
     */
    CustomerResponse toResponse(CustomerEntity customerEntity);
}
