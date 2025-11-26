package com.imfathi.bankingapichallenge.controllers;

import com.imfathi.bankingapichallenge.models.dto.AccountDto;
import com.imfathi.bankingapichallenge.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Accounts", description = "Account management endpoints")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/customers/{customerId}/accounts")
    @Operation(summary = "Get customer accounts", description = "Get all accounts for a customer")
    public ResponseEntity<List<AccountDto.Response>> getAccounts(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAllCustomerAccounts(customerId));
    }

    @PostMapping("/customers/{customerId}/accounts")
    @Operation(summary = "Create account", description = "Create a new account for a customer with initial deposit")
    public ResponseEntity<AccountDto.Response> createAccount(@PathVariable Long customerId, @Valid @RequestBody AccountDto.CreateAccount request) {
        AccountDto.Response response = accountService.saveAccount(request, customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers/{customerId}/accounts/{id}")
    @Operation(summary = "Get account", description = "Get account by id")
    public ResponseEntity<AccountDto.Response> getAccount(@PathVariable Long id) {
        AccountDto.Response response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customers/{customerId}/accounts/{id}")
    @Operation(summary = "Delete account", description = "Delete account by id")
    public void closeAccount(@PathVariable Long id) {
        accountService.closeAccount(id);
    }

    @GetMapping("/customers/{customerId}/accounts/{id}/balance")
    @Operation(summary = "Get account balance", description = "Retreive the balance of an account by id")
    public ResponseEntity<AccountDto.BalanceResponse> getAccountBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountBalance(id));
    }
}
