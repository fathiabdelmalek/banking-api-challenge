package com.imfathi.bankingapichallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.imfathi.bankingapichallenge.models.Customer;
import com.imfathi.bankingapichallenge.services.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }
}
