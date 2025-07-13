package kh.edu.istad.mobileapi.mapper;


import kh.edu.istad.mobileapi.domain.Customer;
import kh.edu.istad.mobileapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobileapi.dto.CustomerResponse;
import kh.edu.istad.mobileapi.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);

    //DTO->Model
    //Model->DTO

    //Return type is converted data
    //parameter is source data


    CustomerResponse fromCustomer(Customer customer); //Map Customer to CustomerResponse


    Customer toCustomer(CreateCustomerRequest createCustomerRequest);


}


