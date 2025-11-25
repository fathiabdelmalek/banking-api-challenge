package com.imfathi.bankingapichallenge.config;

import com.imfathi.bankingapichallenge.models.entities.Customer;
import com.imfathi.bankingapichallenge.repositories.CustomerRepository;
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
    public CommandLineRunner initData() {
        return args -> {
            if (customerRepository.count() == 0) {
                customerRepository.saveAll(Arrays.asList(
                        Customer.builder().name("Arisha Barron").build(),
                        Customer.builder().name("Branden Gibson").build(),
                        Customer.builder().name("Rhonda Church").build(),
                        Customer.builder().name("Georgina Hazel").build()
                ));
            }
        };
    }
}