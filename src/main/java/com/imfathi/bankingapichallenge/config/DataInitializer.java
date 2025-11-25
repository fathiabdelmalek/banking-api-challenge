package com.imfathi.bankingapichallenge.config;

import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.models.entities.Customer;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;
import com.imfathi.bankingapichallenge.repositories.CustomerRepository;
import com.imfathi.bankingapichallenge.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CustomerRepository customerRepository;

    @Bean
    public CommandLineRunner initData(AccountRepository accountRepository, TransferRepository transferRepository) {
        return args -> {
            if (customerRepository.count() == 0)
                customerRepository.saveAll(Arrays.asList(
                        Customer.builder().name("Arisha Barron").build(),
                        Customer.builder().name("Branden Gibson").build(),
                        Customer.builder().name("Rhonda Church").build(),
                        Customer.builder().name("Georgina Hazel").build()
                ));
            if (accountRepository.count() == 0)
                accountRepository.saveAll(Arrays.asList(
                        Account.builder().balance(1000.00).customer(customerRepository.findById(1L).orElse(null)).build(),
                        Account.builder().balance(500.00).customer(customerRepository.findById(2L).orElse(null)).build(),
                        Account.builder().balance(0.00).customer(customerRepository.findById(3L).orElse(null)).build(),
                        Account.builder().balance(20000.00).customer(customerRepository.findById(4L).orElse(null)).build(),
                        Account.builder().balance(600000.00).customer(customerRepository.findById(1L).orElse(null)).build()
                ));
        };
    }
}