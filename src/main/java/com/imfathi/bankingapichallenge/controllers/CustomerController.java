package com.imfathi.bankingapichallenge.controllers;

import com.imfathi.bankingapichallenge.models.dto.CustomerDto;
import com.imfathi.bankingapichallenge.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto.Response>> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto.Response> createCustomer(@RequestBody CustomerDto.CreateCustomer request) {
        CustomerDto.Response response = customerService.saveCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto.Response> getCustomer(@PathVariable Long id) {
        CustomerDto.Response response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDto.Response> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto.CreateCustomer request) {
        request.setId(id);
        CustomerDto.Response response = customerService.saveCustomer(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
