package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.Account;
import kh.edu.istad.mobileapi.domain.AccountType;
import kh.edu.istad.mobileapi.domain.Customer;
import kh.edu.istad.mobileapi.dto.AccountResponse;
import kh.edu.istad.mobileapi.dto.CreateAccountRequest;
import kh.edu.istad.mobileapi.dto.UpdateAccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Boolean existsByActNo(String actNo);
    Boolean existsByActType (AccountType actType);
    Optional<Account> findAccountByActNo(String actNo);
    Optional<Account> findAccountByCustomer(Customer customer);
}
