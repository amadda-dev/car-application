package com.carapp.service;

import com.carapp.dto.CustomerDTO;
import com.carapp.dto.CustomerRequestDTO;
import com.carapp.dto.mapper.CustomerMapper;
import com.carapp.exception.ResourceNotFoundException;
import com.carapp.model.Customer;
import com.carapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomerSuccessfully() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test User");
        customer.setEmail("user@mail.com");

        CustomerRequestDTO requestDTO = new CustomerRequestDTO();
        requestDTO.setName("Test User");
        requestDTO.setEmail("user@mail.com");
        requestDTO.setPhone("555-1234");
        requestDTO.setAddress("123 Main St");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("Test User");
        customerDTO.setEmail("user@mail.com");

        when(customerRepository.existsByEmail("user@mail.com")).thenReturn(false);
        when(customerMapper.toEntity(requestDTO)).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.createCustomer(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test User");
        assertThat(result.getEmail()).isEqualTo("user@mail.com");

        verify(customerRepository).existsByEmail("user@mail.com");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldGetCustomerByEmail() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test User");
        customer.setEmail("user@mail.com");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("Test User");
        customerDTO.setEmail("user@mail.com");

        when(customerRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.getCustomerByEmail("user@mail.com");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("user@mail.com");
        assertThat(result.getName()).isEqualTo("Test User");

        verify(customerRepository).findByEmail("user@mail.com");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFoundById() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer not found with this id");

        verify(customerRepository).findById(999L);
    }
}