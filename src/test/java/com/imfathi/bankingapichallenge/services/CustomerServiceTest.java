package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.AccountDto;
import com.imfathi.bankingapichallenge.models.dto.CustomerDto;
import com.imfathi.bankingapichallenge.models.entities.Account;
import com.imfathi.bankingapichallenge.models.entities.Customer;
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
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .build();
    }

    @Test
    void saveCustomer_WithValidData_ShouldSaveCustomer() {
        // Arrange
        CustomerDto.CreateCustomer request = CustomerDto.CreateCustomer.builder()
                .name("Test Customer")
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        CustomerDto.Response response = customerService.saveCustomer(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Customer", response.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getCustomerById_WithValidId_ShouldReturnCustomer() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        Customer found = customerService.getCustomerById(1L);

        // Assert
        assertNotNull(found);
        assertEquals(customer.getId(), found.getId());
        verify(customerRepository).findById(1L);
    }

    @Test
    void getCustomerById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));

        // Assert
        verify(customerRepository).findById(1L);
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {

        // Arrange
        Customer customer2 = Customer.builder()
                .name("Test Customer 2")
                .build();

        when(customerRepository.findAll())
                .thenReturn(Arrays.asList(customer, customer2));

        // Act
        List<CustomerDto.Response> customers = customerService.getAllCustomers();

        // Assert
        assertEquals(2, customers.size());
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer updated = customerService.updateCustomer(1L, customer);

        // Assert
        assertNotNull(updated);
        assertEquals(customer.getName(), updated.getName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        // Act
        customerService.deleteCustomer(1L);

        // Assert
        verify(customerRepository).delete(customer);
    }
}
