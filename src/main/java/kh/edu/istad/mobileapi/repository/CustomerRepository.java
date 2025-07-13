package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phoneNumber);
    Optional<Customer> findByPhone(String phone);
}
