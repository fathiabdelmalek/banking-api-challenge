package com.imfathi.bankingapichallenge.controllers;

import com.imfathi.bankingapichallenge.models.dto.TransferDto;
import com.imfathi.bankingapichallenge.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransferController {
    @Autowired
    private TransferService transferService;

    @GetMapping("/transfers/customers/{customerId}/sent")
    public ResponseEntity<List<TransferDto.Response>> getAllFromCustomerTransfers(@PathVariable Long customerId) {
        return ResponseEntity.ok(transferService.getAllFromCustomerTransfers(customerId));
    }

    @GetMapping("/transfers/customers/{customerId}/received")
    public ResponseEntity<List<TransferDto.Response>> getAllToCustomerTransfers(@PathVariable Long customerId) {
        return ResponseEntity.ok(transferService.getAllToCustomerTransfers(customerId));
    }

    @GetMapping("transfers/customers/{customerId}")
    public ResponseEntity<List<TransferDto.Response>> getAllCustomerTransfers(@PathVariable Long customerId) {
        List<TransferDto.Response> from = transferService.getAllFromCustomerTransfers(customerId);
        List<TransferDto.Response> to = transferService.getAllToCustomerTransfers(customerId);
        List<TransferDto.Response> all = new ArrayList<>();
        all.addAll(from);
        all.addAll(to);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/transfers/accounts/{accountId}/sent")
    public ResponseEntity<List<TransferDto.Response>> getAllFromAccountTransfers(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getAllFromAccountTransfers(accountId));
    }

    @GetMapping("/transfers/accounts/{accountId}/received")
    public ResponseEntity<List<TransferDto.Response>> getAllToAccountTransfers(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getAllToAccountTransfers(accountId));
    }

    @GetMapping("transfers/accounts/{accountId}")
    public ResponseEntity<List<TransferDto.Response>> getAllAccountTransfers(@PathVariable Long accountId) {
        List<TransferDto.Response> from = transferService.getAllFromAccountTransfers(accountId);
        List<TransferDto.Response> to = transferService.getAllToAccountTransfers(accountId);
        List<TransferDto.Response> all = new ArrayList<>();
        all.addAll(from);
        all.addAll(to);
        return ResponseEntity.ok(all);
    }

    @PostMapping("/transfers/accounts/{accountId}")
    public ResponseEntity<TransferDto.Response> createTransfer(@PathVariable Long accountId, @RequestBody TransferDto.CreateRequest request) {
        request.setFromAccountId(accountId);
        TransferDto.Response response = transferService.createTransfer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/transfers/{id}")
    public ResponseEntity<TransferDto.Response> getTransferById(Long id) {
        TransferDto.Response response = transferService.getTransferById(id);
        return ResponseEntity.ok(response);
    }
}
