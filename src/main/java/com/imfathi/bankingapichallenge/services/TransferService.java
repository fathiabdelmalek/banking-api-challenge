package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.InsufficientFundsException;
import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.TransferDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.models.entities.Transfer;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;
import com.imfathi.bankingapichallenge.repositories.TransferRepository;
import com.imfathi.bankingapichallenge.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransferService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferRepository transferRepository;

    public List<TransferDto.Response> getAllFromCustomerTransfers(Long customerId) {
        List<Transfer> transfers = transferRepository.findAllByFromAccountCustomerId(customerId);
        return transfers.stream().map(this::toResponse).toList();
    }

    public List<TransferDto.Response> getAllToCustomerTransfers(Long customerId) {
        List<Transfer> transfers = transferRepository.findAllByToAccountCustomerId(customerId);
        return transfers.stream().map(this::toResponse).toList();
    }

    public List<TransferDto.Response> getAllFromAccountTransfers(Long fromAccountId) {
        List<Transfer> transfers = transferRepository.findAllByFromAccountId(fromAccountId);
        return transfers.stream().map(this::toResponse).toList();
    }

    public List<TransferDto.Response> getAllToAccountTransfers(Long toAccountId) {
        List<Transfer> transfers = transferRepository.findAllByToAccountId(toAccountId);
        return transfers.stream().map(this::toResponse).toList();
    }

    public TransferDto.Response getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found: " + id));
        return toResponse(transfer);
    }

    @Transactional
    public TransferDto.Response createTransfer(TransferDto.CreateRequest request, Long accountId) {
        Account fromAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Source account not found: " + accountId));
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Destination account not found: " + request.getToAccountId()));

        if (fromAccount.getBalance() < request.getAmount()) {
            throw new InsufficientFundsException(
                    String.format("Insufficient funds. Available: %.2f, Requested: %.2f",
                            fromAccount.getBalance(), request.getAmount()));
        }

        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());
        fromAccount = accountRepository.save(fromAccount);
        toAccount = accountRepository.save(toAccount);

        Transfer transfer = Transfer.builder()
                .amount(request.getAmount())
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();

        Transfer savedTransfer = transferRepository.save(transfer);

        return toResponse(savedTransfer);
    }

    private TransferDto.Response toResponse(Transfer transfer) {
        return TransferDto.Response.builder()
                .id(transfer.getId())
                .fromAccountId(transfer.getFromAccount().getId())
                .toAccountId(transfer.getToAccount().getId())
                .amount(transfer.getAmount())
                .status(String.valueOf(transfer.getStatus()))
                .createdAt(transfer.getCreatedAt())
                .build();
    }
}
