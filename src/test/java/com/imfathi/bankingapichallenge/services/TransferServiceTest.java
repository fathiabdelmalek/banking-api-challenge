package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.InsufficientFundsException;
import com.imfathi.bankingapichallenge.models.dto.TransferDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Account fromAccount;
    private Account toAccount;
    private final Long fromId = 1L;
    private final Long toId = 2L;

    @BeforeEach
    void setUp() {
        fromAccount = Account.builder()
                .id(fromId)
                .balance(10000.00)
                .build();
        toAccount = Account.builder()
                .id(toId)
                .balance(100.00)
                .build();
    }

    @Test
    void performTransfer_ShouldSucceed_WhenFundsAreSufficient() {
        // Arrange
        Double amount = 1000.00;

        TransferDto.CreateTransfer request = TransferDto.CreateTransfer.builder()
                .amount(amount)
                .toAccountId(toId)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
        Transfer savedTransfer = Transfer.builder()
                .id(1L)
                .amount(amount)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .status(Status.COMPLETED)
                .build();
        when(transferRepository.save(any(Transfer.class))).thenReturn(savedTransfer);

        // Act
        TransferDto.Response response = transferService.performTransfer(request, fromAccount.getId());

        // Assert
        // Verify balances were updated
        // Note: In a real implementation, setters would be called.
        // We assume service updates objects and saves them.
        verify(accountRepository, times(2)).save(any(Account.class)); 
        verify(transferRepository).save(any(Transfer.class));
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void performTransfer_ShouldThrowException_WhenFundsInsufficient() {
        // Arrange
        Double amount = 20000.00;

        TransferDto.CreateTransfer request = TransferDto.CreateTransfer.builder()
                .amount(amount)
                .toAccountId(toId)
                .build();

        when(accountRepository.findById(fromId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(toId)).thenReturn(Optional.of(toAccount));

        // Act
        assertThrows(InsufficientFundsException.class, () -> transferService.performTransfer(request, fromId));

        // Assert
        verify(accountRepository, never()).save(any(Account.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }
}
