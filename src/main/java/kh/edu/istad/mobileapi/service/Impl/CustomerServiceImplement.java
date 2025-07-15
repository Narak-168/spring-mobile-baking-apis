package kh.edu.istad.mobileapi.service.Impl;

import kh.edu.istad.mobileapi.domain.Customer;
import kh.edu.istad.mobileapi.domain.KYC;
import kh.edu.istad.mobileapi.domain.Segment;
import kh.edu.istad.mobileapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobileapi.dto.CustomerResponse;
import kh.edu.istad.mobileapi.dto.UpdateCustomerRequest;
import kh.edu.istad.mobileapi.mapper.CustomerMapper;
import kh.edu.istad.mobileapi.repository.CustomerRepository;
import kh.edu.istad.mobileapi.repository.KYCRepository;
import kh.edu.istad.mobileapi.repository.SegmentRepository;
import kh.edu.istad.mobileapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImplement implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KYCRepository kYCRepository;
    private final SegmentRepository segmentRepository;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        // validation
        if(customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if(customerRepository.existsByPhone(createCustomerRequest.phone())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }
        if(kYCRepository.existsByNationalCardId(createCustomerRequest.nationalCardID())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National card already exists");
        }


        // Find segment
        Segment segment = segmentRepository.getSegmentByName(createCustomerRequest.segmentName())
                .orElseThrow(() -> new RuntimeException("Segment not found"));

        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());
        customer.setSegment(segment);

        Customer savedCustomer = customerRepository.save(customer);

        KYC kyc = new KYC();
        kyc.setNationalCardId(createCustomerRequest.nationalCardID());

        kyc.setCustomer(savedCustomer);
        kYCRepository.save(kyc);

        savedCustomer.setKyc(kyc);

        log.info("Creating Customer getID before save: " + customer.getId());

        customerRepository.save(savedCustomer);

        log.info("Created Customer getID after save : {}", customer.getId());

        return customerMapper.fromCustomer(savedCustomer);
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
                .findByPhoneAndIsDeletedFalse(phone)
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

    @Transactional
    @Override
    public void disableByPhone(String phone) {
        if (!customerRepository.isExistsByPhone(phone)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer's phone number not found");
        }
        customerRepository.disableByPhone(phone);
    }

    //homework-V2
    @Override
    public void verifyByKYCNationalCardID(String nationalCardID) {
        if (!kYCRepository.existsByNationalCardId(nationalCardID)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National Card ID number not found");
        }
        KYC kyc = kYCRepository.findByNationalCardId(nationalCardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "KYC's card number not found"));
        kyc.setIsVerified(true);
        kYCRepository.save(kyc);
    }

}
