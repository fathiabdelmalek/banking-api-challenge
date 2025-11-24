package com.imfathi.bankingapichallenge.repositories;

import com.imfathi.bankingapichallenge.models.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    public List<Transfer> findAllByFromAccountCustomerId(Long id);
    public List<Transfer> findAllByToAccountCustomerId(Long id);
    public List<Transfer> findAllByFromAccountId(Long id);
    public List<Transfer> findAllByToAccountId(Long id);
}
