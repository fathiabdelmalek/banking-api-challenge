package com.imfathi.bankingapichallenge.services;

import com.imfathi.bankingapichallenge.exceptions.ResourceNotFoundException;
import com.imfathi.bankingapichallenge.models.dto.CustomerDto;
import com.imfathi.bankingapichallenge.models.entities.Customer;
import com.imfathi.bankingapichallenge.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void getAllCustomers_ShouldReturnList() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // Act
        List<CustomerDto.Response> result = customerService.getAllCustomers();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getCustomerById_ShouldReturnCustomer_WhenFound() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        CustomerDto.Response result = customerService.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getCustomerById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void saveCustomer_ShouldReturnResponse() {
        // Arrange
        CustomerDto.CreateCustomer request = new CustomerDto.CreateCustomer();
        request.setName("Jane Doe");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(2L);
        savedCustomer.setName("Jane Doe");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // Act
        CustomerDto.Response result = customerService.saveCustomer(request);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Jane Doe", result.getName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_ShouldCallRepository() {
        // Arrange
        doNothing().when(customerRepository).deleteById(1L);

        // Act
        customerService.deleteCustomer(1L);

        // Assert
        verify(customerRepository).deleteById(1L);
    }
}
