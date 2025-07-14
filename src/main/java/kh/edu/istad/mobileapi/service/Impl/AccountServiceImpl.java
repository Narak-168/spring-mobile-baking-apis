package kh.edu.istad.mobileapi.service.Impl;

import jakarta.transaction.Transactional;
import kh.edu.istad.mobileapi.domain.Account;
import kh.edu.istad.mobileapi.domain.AccountType;
import kh.edu.istad.mobileapi.domain.Customer;
import kh.edu.istad.mobileapi.dto.AccountResponse;
import kh.edu.istad.mobileapi.dto.CreateAccountRequest;
import kh.edu.istad.mobileapi.dto.UpdateAccountRequest;
import kh.edu.istad.mobileapi.mapper.AccountMapper;
import kh.edu.istad.mobileapi.repository.AccountRepository;
import kh.edu.istad.mobileapi.repository.AccountTypeRepository;
import kh.edu.istad.mobileapi.repository.CustomerRepository;
import kh.edu.istad.mobileapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;

    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        if(accountRepository.existsByActNo(createAccountRequest.actNo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account Number is already exists");
        }

        Customer customer = customerRepository.findByPhone(createAccountRequest.phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        AccountType savingsType = accountTypeRepository.findByName("Savings")
                .orElseGet(() -> {
                    AccountType newType = new AccountType();
                    newType.setName("Savings");
                    return accountTypeRepository.save(newType);
                });

        Account account = accountMapper.toAccount(createAccountRequest);
        account.setActType(savingsType);
        account.setIsDeleted(false);
        account.setBalance(Double.valueOf(0));
        account.setReceiverTransactions(new ArrayList<>());
        account.setSenderTransactions(new ArrayList<>());
        account.setCustomer(customer);

        Account account1 =  accountRepository.save(account);

        return accountMapper.fromAccount(account1);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<AccountResponse> accounts = accountRepository.findAll()
                .stream()
                .map(accountMapper::fromAccount)
                .toList();
        return accounts;
    }

    @Override
    public AccountResponse getAccountByActNo(String actNo) {
        return accountRepository.findAccountByActNo(actNo)
                .map(accountMapper::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account number is not found"));
    }

    @Override
    public AccountResponse getAccountsByCustomer(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer is not found with phone number"));
        return accountRepository.findAccountByCustomer(customer)
                .map(accountMapper::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not found"));
    }

    @Override
    public AccountResponse updateAccountByActNo(String actNo, UpdateAccountRequest updateAccountRequest) {
        Account account = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not found"));
        accountMapper.toAccountPartially(updateAccountRequest, account);
        accountRepository.save(account);
        return accountMapper.fromAccount(account);
    }

    @Override
    public void deleteAccountByActNo(String actNo) {
        Account account = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not found"));
        accountRepository.delete(account);
    }

    @Transactional
    @Override
    public void disableByActNo(String actNo) {
        Account account = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not found"));
        if(account.getIsDeleted()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account is already disabled");
        }
        account.setIsDeleted(true);
        accountRepository.save(account);
    }
}
