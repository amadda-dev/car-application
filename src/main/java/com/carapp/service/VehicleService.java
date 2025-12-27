package com.carapp.service;

import com.carapp.dto.VehicleDTO;
import com.carapp.dto.VehicleRequestDTO;
import com.carapp.dto.export.VehicleExportDTO;
import com.carapp.dto.mapper.VehicleMapper;
import com.carapp.exception.ResourceNotFoundException;
import com.carapp.model.Customer;
import com.carapp.model.Vehicle;
import com.carapp.repository.CustomerRepository;
import com.carapp.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    public VehicleDTO createVehicle(VehicleRequestDTO requestDTO) {
        if (requestDTO.getVin() != null && vehicleRepository.existsByVin(requestDTO.getVin())) {
            throw new IllegalArgumentException("VIN already exists");
        }

        Vehicle vehicle = vehicleMapper.toEntity(requestDTO);

        if (requestDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", requestDTO.getCustomerId()));
            vehicle.setCustomer(customer);
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(savedVehicle);
    }

    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
        return vehicleMapper.toDTO(vehicle);
    }

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getVehiclesByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }
        return vehicleRepository.findByCustomerId(customerId)
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO updateVehicle(Long id, VehicleRequestDTO requestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));

        vehicle.setMake(requestDTO.getMake());
        vehicle.setModel(requestDTO.getModel());
        vehicle.setCar_year(requestDTO.getCar_year());
        vehicle.setVin(requestDTO.getVin());
        vehicle.setSold(requestDTO.getSold());
        vehicle.setColor(requestDTO.getColor());

        if (requestDTO.getCustomerId() != null) {
            if (vehicle.getCustomer() == null || !requestDTO.getCustomerId().equals(vehicle.getCustomer().getId())) {
                Customer newCustomer = customerRepository.findById(requestDTO.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", requestDTO.getCustomerId()));
                vehicle.setCustomer(newCustomer);
            }
        } else {
            vehicle.setCustomer(null);
        }

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(updatedVehicle);
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle", "id", id);
        }
        vehicleRepository.deleteById(id);
    }
    public List<VehicleExportDTO> getAllVehiclesForExport() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toExportDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getSoldVehicles() {
        return vehicleRepository.findBySold(true)
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());

    }

    public List<VehicleDTO> searchVehicles(Boolean sold, String make, String model, Integer year, String color) {
        List<Vehicle> vehicles = vehicleRepository.searchVehicles(sold, make, model, year, color);
        return vehicles.stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }
}