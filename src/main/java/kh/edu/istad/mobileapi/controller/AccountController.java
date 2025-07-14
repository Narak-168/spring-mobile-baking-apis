package kh.edu.istad.mobileapi.controller;

import jakarta.validation.Valid;
import kh.edu.istad.mobileapi.dto.AccountResponse;
import kh.edu.istad.mobileapi.dto.CreateAccountRequest;
import kh.edu.istad.mobileapi.dto.UpdateAccountRequest;
import kh.edu.istad.mobileapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        log.info("Creating account {}", createAccountRequest);
        return accountService.createAccount(createAccountRequest);
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{actNo}")
    public AccountResponse getAccountByAccountNo(@PathVariable String actNo) {
        return accountService.getAccountByActNo(actNo);
    }

    @GetMapping("/customer/{phoneNumber}")
    public AccountResponse getAccountByPhoneNumber(@PathVariable String phoneNumber) {
        return accountService.getAccountsByCustomer(phoneNumber);
    }

    @PatchMapping("/{actNo}")
    public AccountResponse updateAccount(@PathVariable String actNo, @Valid @RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountService.updateAccountByActNo(actNo, updateAccountRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{actNo}")
    public void deleteAccount(@PathVariable String actNo) {
        accountService.deleteAccountByActNo(actNo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/disable/{actNo}")
    public void disableAccount(@PathVariable String actNo) {
        accountService.disableByActNo(actNo);
    }
}