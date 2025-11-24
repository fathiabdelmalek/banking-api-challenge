package com.imfathi.bankingapichallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imfathi.bankingapichallenge.models.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
