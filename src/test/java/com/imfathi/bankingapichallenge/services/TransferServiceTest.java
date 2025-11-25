package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.InsufficientFundsException;
import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.TransferDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.models.entities.Customer;
import com.imfathi.bankingapichallenge.models.entities.Transfer;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;
import com.imfathi.bankingapichallenge.repositories.TransferRepository;
import com.imfathi.bankingapichallenge.utils.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    private Customer customer1;
    private Customer customer2;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        customer1 = Customer.builder()
                .id(1L)
                .name("Test Customer 1")
                .build();

        customer2 = Customer.builder()
                .id(2L)
                .name("Test Customer 2")
                .build();

        fromAccount = Account.builder()
                .id(1L)
                .balance(1000.0)
                .customer(customer1)
                .build();

        toAccount = Account.builder()
                .id(2L)
                .balance(500.0)
                .customer(customer2)
                .build();
    }

    @Test
    void createTransfer_WithSufficientFunds_ShouldCompleteSuccessfully() {
        // Arrange
        TransferDto.CreateRequest request = TransferDto.CreateRequest.builder()
                .toAccountId(2L)
                .amount(200.0)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        Transfer savedTransfer = Transfer.builder()
                .id(1L)
                .amount(200.0)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();
        when(transferRepository.save(any(Transfer.class))).thenReturn(savedTransfer);

        // Act
        TransferDto.Response response = transferService.createTransfer(request, 1L);

        // Assert
        assertNotNull(response);
        assertEquals(200.0, response.getAmount());
        assertEquals("COMPLETED", response.getStatus());
        assertEquals(800.0, fromAccount.getBalance());
        assertEquals(700.0, toAccount.getBalance());

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void createTransfer_WithInsufficientFunds_ShouldThrowException() {
        // Arrange
        TransferDto.CreateRequest request = TransferDto.CreateRequest.builder()
                .toAccountId(2L)
                .amount(2000.0)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // Act & Assert
        assertThrows(InsufficientFundsException.class, () -> {
            transferService.createTransfer(request, 1L);
        });

        verify(accountRepository, never()).save(any(Account.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }

    @Test
    void createTransfer_WithInvalidFromAccount_ShouldThrowException() {
        // Arrange
        TransferDto.CreateRequest request = TransferDto.CreateRequest.builder()
                .toAccountId(2L)
                .amount(100.0)
                .build();

        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transferService.createTransfer(request, 999L);
        });
    }

    @Test
    void createTransfer_WithInvalidToAccount_ShouldThrowException() {
        // Arrange
        TransferDto.CreateRequest request = TransferDto.CreateRequest.builder()
                .toAccountId(999L)
                .amount(100.0)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transferService.createTransfer(request, 1L);
        });
    }

    @Test
    void getAllFromAccountTransfers_ShouldReturnTransfers() {
        // Arrange
        Transfer transfer1 = Transfer.builder()
                .id(1L)
                .amount(100.0)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();

        Transfer transfer2 = Transfer.builder()
                .id(2L)
                .amount(50.0)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();

        when(transferRepository.findAllByFromAccountId(1L))
                .thenReturn(Arrays.asList(transfer1, transfer2));

        // Act
        List<TransferDto.Response> transfers = transferService.getAllFromAccountTransfers(1L);

        // Assert
        assertEquals(2, transfers.size());
        assertEquals(100.0, transfers.get(0).getAmount());
        assertEquals(50.0, transfers.get(1).getAmount());
    }

    @Test
    void getAllToAccountTransfers_ShouldReturnTransfers() {
        // Arrange
        Transfer transfer = Transfer.builder()
                .id(1L)
                .amount(75.0)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();

        when(transferRepository.findAllByToAccountId(2L))
                .thenReturn(Arrays.asList(transfer));

        // Act
        List<TransferDto.Response> transfers = transferService.getAllToAccountTransfers(2L);

        // Assert
        assertEquals(1, transfers.size());
        assertEquals(75.0, transfers.get(0).getAmount());
    }

    @Test
    void getTransferById_WithValidId_ShouldReturnTransfer() {
        // Arrange
        Transfer transfer = Transfer.builder()
                .id(1L)
                .amount(100.0)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();

        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));

        // Act
        TransferDto.Response response = transferService.getTransferById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(100.0, response.getAmount());
    }

    @Test
    void getTransferById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(transferRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transferService.getTransferById(999L);
        });
    }
}
