package com.skprj.crudPostgres.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customers customer;

    @BeforeEach
    void setUp() {
        customer = new Customers();
        customer.setCustomer_id(1);
        customer.setCustomer_name("John Doe");
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        customerService.createCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetAllCustomers() {
        List<Customers> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customers> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getCustomer_name());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_Found() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Optional<Customers> result = customerService.getCustomerById(1);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getCustomer_name());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Customers> result = customerService.getCustomerById(1);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteCustomerById() {
        doNothing().when(customerRepository).deleteById(1);

        customerService.deleteCustomerById(1);

        verify(customerRepository, times(1)).deleteById(1);
    }
}

