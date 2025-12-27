package com.carapp.dto.mapper;

import com.carapp.dto.CustomerDTO;
import com.carapp.dto.CustomerRequestDTO;
import com.carapp.dto.export.CustomerExportDTO;
import com.carapp.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        return dto;
    }

    public Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return customer;
    }

    public CustomerExportDTO toExportDTO(Customer customer) {
        return new CustomerExportDTO(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }


}