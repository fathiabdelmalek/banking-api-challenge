package com.imfathi.bankingapichallenge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imfathi.bankingapichallenge.models.Account;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<com.imfathi.bankingapichallenge.models.Account> getAllAccounts(long customerId) {
        return (List<Account>) accountRepository.findAllByCustomerId(customerId);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
