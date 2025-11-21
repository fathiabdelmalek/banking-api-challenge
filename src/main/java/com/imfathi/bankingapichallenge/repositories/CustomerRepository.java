package com.imfathi.bankingapichallenge.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imfathi.bankingapichallenge.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
