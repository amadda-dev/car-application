package com.carapp.service;

import com.carapp.model.Customer;
import com.carapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer getCustomerByEmail(Customer customer) {
        return customerRepository.findByEmail(customer.getEmail())
                .orElseThrow(() -> new RuntimeException("Customer email not found"));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setEmail(customerDetails.getPhone());
        customer.setEmail(customerDetails.getAddress());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
