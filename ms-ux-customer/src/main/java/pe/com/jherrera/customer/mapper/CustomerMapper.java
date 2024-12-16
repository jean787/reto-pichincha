package pe.com.jherrera.customer.mapper;

import org.mapstruct.Mapper;
import pe.com.jherrera.customer.dto.ApiMessageResponse;
import pe.com.jherrera.customer.dto.CustomerRequest;
import pe.com.jherrera.customer.dto.CustomerResponse;
import pe.com.jherrera.customer.proxy.model.RestApiMessageResponse;
import pe.com.jherrera.customer.proxy.model.RestCustomerRequest;
import pe.com.jherrera.customer.proxy.model.RestCustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(RestCustomerResponse restCustomerResponse);
    RestCustomerRequest toRequest(CustomerRequest restCustomerResponse);
    ApiMessageResponse toApiResponse(RestApiMessageResponse restApiMessageResponse);

}
