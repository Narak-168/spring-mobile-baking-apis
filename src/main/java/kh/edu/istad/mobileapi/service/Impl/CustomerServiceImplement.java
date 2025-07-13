package kh.edu.istad.mobileapi.service.Impl;

import kh.edu.istad.mobileapi.domain.Customer;
import kh.edu.istad.mobileapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobileapi.dto.CustomerResponse;
import kh.edu.istad.mobileapi.dto.UpdateCustomerRequest;
import kh.edu.istad.mobileapi.mapper.CustomerMapper;
import kh.edu.istad.mobileapi.repository.CustomerRepository;
import kh.edu.istad.mobileapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImplement implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        // validation
        if(customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if(customerRepository.existsByPhone(createCustomerRequest.phone())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }


        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setAccounts(new ArrayList<>());
        customer.setIsDeleted(false);


        log.info("Creating Customer getID before save: " + customer.getId());

        customerRepository.save(customer);

        log.info("Created Customer getID after save : {}", customer.getId());

        return customerMapper.fromCustomer(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        List<CustomerResponse> customers = customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();

        return customers;
    }

    @Override
    public CustomerResponse findByPhone(String phone) {
        return customerRepository
                .findByPhone(phone)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer's phone number not found"));
    }

    @Override
    public CustomerResponse updateByPhone(String phone, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository
                .findByPhone(phone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer's phone number not found"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        customer = customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }


    @Override
    public void deleteByPhone(String phone) {
        Customer customer = customerRepository
                .findByPhone(phone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer's phone number not found"));
        customerRepository.delete(customer);
    }
}
