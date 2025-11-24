package com.imfathi.bankingapichallenge.controllers;

import com.imfathi.bankingapichallenge.models.dto.AccountDto;
import com.imfathi.bankingapichallenge.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<List<AccountDto.Response>> getAccounts(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAllCustomerAccounts(customerId));
    }

    @PostMapping("/customers/{customerId}/accounts")
    public ResponseEntity<AccountDto.Response> createAccount(@PathVariable Long customerId, @RequestBody AccountDto.CreateAccount request) {
        request.setCustomerId(customerId);
        AccountDto.Response response = accountService.saveAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers/{customerId}/accounts/{id}")
    public ResponseEntity<AccountDto.Response> getAccount(@PathVariable Long id) {
        AccountDto.Response response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customers/{customerId}/accounts/{id}")
    public ResponseEntity<AccountDto.Response> updateAccount(@PathVariable Long customerId, @RequestBody AccountDto.CreateAccount request) {
        request.setCustomerId(customerId);
        AccountDto.Response response = accountService.saveAccount(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customers/{customerId}/accounts/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<AccountDto.BalanceResponse> getAccountBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountBalance(id));
    }
}
