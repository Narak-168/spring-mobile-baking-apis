package kh.edu.istad.mobileapi.service;
import kh.edu.istad.mobileapi.dto.AccountResponse;
import kh.edu.istad.mobileapi.dto.CreateAccountRequest;
import kh.edu.istad.mobileapi.dto.UpdateAccountRequest;


import java.util.List;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest createAccountRequest);
    List<AccountResponse> getAllAccounts();
    AccountResponse getAccountByActNo(String actNo);
    AccountResponse getAccountsByCustomer(String phoneNumber);
    AccountResponse updateAccountByActNo(String actNo, UpdateAccountRequest updateAccountRequest);
    void deleteAccountByActNo(String actNo);
    void disableByActNo(String actNo);
}