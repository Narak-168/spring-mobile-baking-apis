package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phoneNumber);
    Optional<Customer> findByPhone(String phone);
}
