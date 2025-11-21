package com.imfathi.bankingapichallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.imfathi.bankingapichallenge.models.Account;
import com.imfathi.bankingapichallenge.services.AccountService;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/customers/{customerId}/accounts")
    public List<Account> getAccounts(@PathVariable long customerId) {
        return accountService.getAllAccounts(customerId);
    }

    @PostMapping("/customers/{customerId}/accounts")
    public Account createAccount(@PathVariable Long customerId, Account account) {
        return accountService.saveAccount(account);
    }

    @GetMapping("/customers/{customerId}/accounts/{id}")
    public Account getAccount(@PathVariable Long customerId, @PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/customers/{customerId}/accounts/{id}")
    public Account updateAccount(@PathVariable Long customerId, @PathVariable Long id, @RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @DeleteMapping("/customers/{customerId}/accounts/{id}")
    public void deleteAccount(@PathVariable Long customerId, @PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
