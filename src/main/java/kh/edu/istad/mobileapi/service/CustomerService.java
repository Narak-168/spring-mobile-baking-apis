package kh.edu.istad.mobileapi.service;

import kh.edu.istad.mobileapi.domain.Customer;
import kh.edu.istad.mobileapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobileapi.dto.CustomerResponse;
import kh.edu.istad.mobileapi.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
List<CustomerResponse> findAllCustomers();
CustomerResponse findByPhone(String phone);
CustomerResponse updateByPhone(String phone, UpdateCustomerRequest updateCustomerRequest);
void deleteByPhone(String phone);
}
