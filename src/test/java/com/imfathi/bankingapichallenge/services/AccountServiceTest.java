package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.AccountDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.models.entities.Customer;
import com.imfathi.bankingapichallenge.repositories.AccountRepository;
import com.imfathi.bankingapichallenge.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void saveAccount_ShouldReturnResponse_WhenCustomerExists() {
        // Arrange
        Long customerId = 1L;
        AccountDto.CreateAccount request = new AccountDto.CreateAccount();
        // Assuming request has a balance field
        // request.setBalance(BigDecimal.TEN); 
        
        Customer customer = new Customer();
        customer.setId(customerId);
        
        Account account = new Account();
        account.setId(100L);
        account.setCustomer(customer);
        account.setBalance(0.00);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        AccountDto.Response response = accountService.saveAccount(request, customerId);

        // Assert
        assertNotNull(response);
        assertEquals(100L, response.getId());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void saveAccount_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        Long customerId = 99L;
        AccountDto.CreateAccount request = new AccountDto.CreateAccount();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> 
            accountService.saveAccount(request, customerId)
        );
    }

    @Test
    void getAccountBalance_ShouldReturnBalance() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setBalance(5000.00);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        AccountDto.BalanceResponse response = accountService.getAccountBalance(accountId);

        // Assert
        assertEquals(5000.00, response.getBalance());
    }
}
