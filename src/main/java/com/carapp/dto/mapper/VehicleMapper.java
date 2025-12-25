package com.carapp.dto.mapper;

import com.carapp.dto.VehicleDTO;
import com.carapp.dto.VehicleRequestDTO;
import com.carapp.model.Customer;
import com.carapp.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleDTO toDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getCar_year());
        dto.setVin(vehicle.getVin());
        dto.setColor(vehicle.getColor());

        if (vehicle.getCustomer() != null) {
            dto.setCustomerId(vehicle.getCustomer().getId());
            dto.setCustomerName(vehicle.getCustomer().getName());
        }

        dto.setCreatedAt(vehicle.getCreatedAt());
        dto.setUpdatedAt(vehicle.getUpdatedAt());
        return dto;
    }

    public Vehicle toEntity(VehicleRequestDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setCar_year(dto.getCar_year());
        vehicle.setVin(dto.getVin());
        vehicle.setColor(dto.getColor());

        // Only set customer if provided
        if (dto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setId(dto.getCustomerId());
            vehicle.setCustomer(customer);
        }

        return vehicle;
    }
}