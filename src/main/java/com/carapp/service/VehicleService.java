package com.carapp.service;

import com.carapp.model.Customer;
import com.carapp.model.Vehicle;
import com.carapp.repository.CustomerRepository;
import com.carapp.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicle.getVin() != null && vehicleRepository.existsByVin(vehicle.getVin())) {
            throw new RuntimeException("VIN already exists");
        }

        if (vehicle.getCustomer() == null || vehicle.getCustomer().getId() == null) {
            throw new RuntimeException("Customer is required");
        }

        Customer customer = customerRepository.findById(vehicle.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        vehicle.setCustomer(customer);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByCustomerId(Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setMake(vehicleDetails.getMake());
        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setVin(vehicleDetails.getVin());
        vehicle.setColor(vehicleDetails.getColor());
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}