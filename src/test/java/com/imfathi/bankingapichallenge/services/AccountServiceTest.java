package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.AccountDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.models.entities.Customer;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;
import com.imfathi.bankingapichallenge.repositories.CustomerRepository;
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
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    private Customer customer;
    private Account account;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .build();

        account = Account.builder()
                .id(1L)
                .balance(10000.0)
                .customer(customer)
                .build();
    }

    @Test
    void saveAccount_WithValidData_ShouldCreateAccount() {
        // Arrange
        AccountDto.CreateAccount request = AccountDto.CreateAccount.builder()
                .balance(5000.0)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        AccountDto.Response response = accountService.saveAccount(request, 1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getCustomerId());
        verify(customerRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void saveAccount_WithInvalidCustomer_ShouldThrowException() {
        // Arrange
        AccountDto.CreateAccount request = AccountDto.CreateAccount.builder()
                .balance(5000.0)
                .build();

        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.saveAccount(request, 999L);
        });

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void getAccountById_WithValidId_ShouldReturnAccount() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Act
        AccountDto.Response response = accountService.getAccountById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10000.0, response.getBalance());
        assertEquals(1L, response.getCustomerId());
    }

    @Test
    void getAccountById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountById(999L);
        });
    }

    @Test
    void getAllCustomerAccounts_ShouldReturnList() {
        // Arrange
        Account account2 = Account.builder()
                .id(2L)
                .balance(2000.0)
                .customer(customer)
                .build();

        when(accountRepository.findAllByCustomerId(1L))
                .thenReturn(Arrays.asList(account, account2));

        // Act
        List<AccountDto.Response> accounts = accountService.getAllCustomerAccounts(1L);

        // Assert
        assertEquals(2, accounts.size());
        assertEquals(10000.0, accounts.get(0).getBalance());
        assertEquals(2000.0, accounts.get(1).getBalance());
    }

    @Test
    void getAllCustomerAccounts_WithNoAccounts_ShouldReturnEmptyList() {
        // Arrange
        when(accountRepository.findAllByCustomerId(1L))
                .thenReturn(Arrays.asList());

        // Act
        List<AccountDto.Response> accounts = accountService.getAllCustomerAccounts(1L);

        // Assert
        assertEquals(0, accounts.size());
    }

    @Test
    void getAccountBalance_WithValidId_ShouldReturnCorrectBalance() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Act
        AccountDto.BalanceResponse balance = accountService.getAccountBalance(1L);

        // Assert
        assertNotNull(balance);
        assertEquals(10000.0, balance.getBalance());
    }

    @Test
    void getAccountBalance_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountBalance(999L);
        });
    }

    @Test
    void deleteAccount_ShouldCallRepository() {
        // Arrange
        account.setBalance(0.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Act
        accountService.deleteAccount(1L);

        // Assert
        verify(accountRepository, times(1)).deleteById(1L);
    }
}
