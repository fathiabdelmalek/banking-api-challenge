package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.AccountDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;
import com.imfathi.bankingapichallenge.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<AccountDto.Response> getAllCustomerAccounts(Long customerId) {
        List<Account> accounts = accountRepository.findAllByCustomerId(customerId);
        return accounts.stream().map(this::toResponse).toList();
    }

    public AccountDto.Response getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        return toResponse(account);

    }

    @Transactional
    public AccountDto.Response saveAccount(AccountDto.CreateAccount request) {
        Account account = accountRepository.save(
                Account.builder()
                        .balance(request.getBalance())
                        .customer(customerRepository.findById(request.getCustomerId())
                                .orElseThrow(
                                        () -> new ResourceNotFoundException("Customer not found: " + request.getCustomerId())))
                        .build());
        return toResponse(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public AccountDto.BalanceResponse getAccountBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        return AccountDto.BalanceResponse.builder().balance(account.getBalance()).build();
    }

    private AccountDto.Response toResponse(Account account) {
        return AccountDto.Response.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .customerId(account.getCustomer().getId())
                .build();
    }
}
