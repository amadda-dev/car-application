package com.carapp.service;

import com.carapp.model.Customer;
import com.carapp.model.Transaction;
import com.carapp.model.Vehicle;
import com.carapp.repository.CustomerRepository;
import com.carapp.repository.TransactionRepository;
import com.carapp.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getCustomer() == null || transaction.getCustomer().getId() == null) {
            throw new RuntimeException("Customer is required");
        }

        if (transaction.getVehicle() == null || transaction.getVehicle().getId() == null) {
            throw new RuntimeException("Vehicle is required");
        }

        Customer customer = customerRepository.findById(transaction.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Vehicle vehicle = vehicleRepository.findById(transaction.getVehicle().getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        transaction.setCustomer(customer);
        transaction.setVehicle(vehicle);
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByCustomerId(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    public List<Transaction> getTransactionsByVehicleId(Long vehicleId) {
        return transactionRepository.findByVehicleId(vehicleId);
    }

    public List<Transaction> getTransactionsByType(Transaction.TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}