package com.imfathi.bankingapichallenge.repositories;

import com.imfathi.bankingapichallenge.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public List<Account> findAllByCustomerId(Long id);
}
