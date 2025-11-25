package com.imfathi.bankingapichallenge.controllers;

import com.imfathi.bankingapichallenge.models.dto.TransferDto;
import com.imfathi.bankingapichallenge.services.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Transfers", description = "Transfer management endpoints")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @GetMapping("/transfers/customers/{customerId}/sent")
    @Operation(summary = "Get customer sent transfers", description = "Get all customer transfers sent to a customer")
    public ResponseEntity<List<TransferDto.Response>> getAllFromCustomerTransfers(@PathVariable Long customerId) {
        return ResponseEntity.ok(transferService.getAllFromCustomerTransfers(customerId));
    }

    @GetMapping("/transfers/customers/{customerId}/received")
    @Operation(summary = "Get customer received transfers", description = "Get all customer transfers received from a customer")
    public ResponseEntity<List<TransferDto.Response>> getAllToCustomerTransfers(@PathVariable Long customerId) {
        return ResponseEntity.ok(transferService.getAllToCustomerTransfers(customerId));
    }

    @GetMapping("transfers/customers/{customerId}")
    @Operation(summary = "Get customer transfers", description = "Get all customer transfers")
    public ResponseEntity<List<TransferDto.Response>> getAllCustomerTransfers(@PathVariable Long customerId) {
        List<TransferDto.Response> from = transferService.getAllFromCustomerTransfers(customerId);
        List<TransferDto.Response> to = transferService.getAllToCustomerTransfers(customerId);
        List<TransferDto.Response> all = new ArrayList<>();
        all.addAll(from);
        all.addAll(to);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/transfers/accounts/{accountId}/sent")
    @Operation(summary = "Get account sent transfers", description = "Get all account transfers sent from an account")
    public ResponseEntity<List<TransferDto.Response>> getAllFromAccountTransfers(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getAllFromAccountTransfers(accountId));
    }

    @GetMapping("/transfers/accounts/{accountId}/received")
    @Operation(summary = "Get account received transfers", description = "Get all account transfers received to an account")
    public ResponseEntity<List<TransferDto.Response>> getAllToAccountTransfers(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getAllToAccountTransfers(accountId));
    }

    @GetMapping("transfers/accounts/{accountId}")
    @Operation(summary = "Get account transfers", description = "Get all account transfers")
    public ResponseEntity<List<TransferDto.Response>> getAllAccountTransfers(@PathVariable Long accountId) {
        List<TransferDto.Response> from = transferService.getAllFromAccountTransfers(accountId);
        List<TransferDto.Response> to = transferService.getAllToAccountTransfers(accountId);
        List<TransferDto.Response> all = new ArrayList<>();
        all.addAll(from);
        all.addAll(to);
        return ResponseEntity.ok(all);
    }

    @PostMapping("/transfers/accounts/{accountId}")
    @Operation(summary = "Create transfer", description = "Create a new transfer from an account to another account")
    public ResponseEntity<TransferDto.Response> createTransfer(@PathVariable Long accountId, @Valid @RequestBody TransferDto.CreateTransfer request) {
        TransferDto.Response response = transferService.performTransfer(request, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/transfers/{id}")
    @Operation(summary = "Get transfer", description = "Get transfer by id")
    public ResponseEntity<TransferDto.Response> getTransferById(Long id) {
        TransferDto.Response response = transferService.getTransferById(id);
        return ResponseEntity.ok(response);
    }
}
