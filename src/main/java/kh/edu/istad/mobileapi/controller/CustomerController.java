package kh.edu.istad.mobileapi.controller;

import jakarta.validation.Valid;
import kh.edu.istad.mobileapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobileapi.dto.CustomerResponse;
import kh.edu.istad.mobileapi.dto.UpdateCustomerRequest;
import kh.edu.istad.mobileapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerService.createCustomer(createCustomerRequest);
    }

    @GetMapping("/{phone}")
    public CustomerResponse findByPhone( @PathVariable String phone) {
        return customerService.findByPhone(phone);
    }

    @PatchMapping("/{phone}")
    void updateByPhone(@PathVariable String phone,
                       @RequestBody UpdateCustomerRequest updateCustomerRequest) {}


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{phone}")
    void deleteByPhone(@PathVariable String phone) {
        customerService.deleteByPhone(phone);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{phone}")
    public void disableByPhone(@PathVariable String phone){
        customerService.disableByPhone(phone);
    }
}