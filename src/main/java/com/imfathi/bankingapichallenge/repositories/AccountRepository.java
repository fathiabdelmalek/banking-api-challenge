package com.imfathi.bankingapichallenge.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.imfathi.bankingapichallenge.models.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
    public List<Account> findAllByCustomerId(long id);
}
