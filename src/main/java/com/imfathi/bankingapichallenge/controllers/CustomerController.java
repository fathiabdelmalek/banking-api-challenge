package com.imfathi.bankingapichallenge.controllers;

import com.imfathi.bankingapichallenge.models.dto.CustomerDto;
import com.imfathi.bankingapichallenge.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Customers", description = "Customer management endpoints")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto.Response>> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("/customers")
    @Operation(summary = "Create customer", description = "Create a new customer")
    public ResponseEntity<CustomerDto.Response> createCustomer(@Valid @RequestBody CustomerDto.CreateCustomer request) {
        CustomerDto.Response response = customerService.saveCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers/{id}")
    @Operation(summary = "Get customer", description = "Get customer by id")
    public ResponseEntity<CustomerDto.Response> getCustomer(@PathVariable Long id) {
        CustomerDto.Response response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customers/{id}")
    @Operation(summary = "Update customer", description = "Update customer by id")
    public ResponseEntity<CustomerDto.Response> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto.CreateCustomer request) {
        request.setId(id);
        CustomerDto.Response response = customerService.saveCustomer(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customers/{id}")
    @Operation(summary = "Delete customer", description = "Delete customer by id")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
