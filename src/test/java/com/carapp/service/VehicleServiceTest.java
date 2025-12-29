package com.carapp.service;

import com.carapp.dto.VehicleDTO;
import com.carapp.dto.mapper.VehicleMapper;
import com.carapp.model.Vehicle;
import com.carapp.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shouldGetVehicleById() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setMake("Ford");
        vehicle.setModel("Ranger");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(1L);
        vehicleDTO.setMake("Ford");
        vehicleDTO.setModel("Ranger");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.getVehicleById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMake()).isEqualTo("Ford");
        assertThat(result.getModel()).isEqualTo("Ranger");
    }

    @Test
    void shouldGetAllVehicles() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setMake("Toyota");
        vehicle1.setModel("Camry");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setMake("Honda");
        vehicle2.setModel("Accord");

        VehicleDTO dto1 = new VehicleDTO();
        dto1.setId(1L);
        dto1.setMake("Toyota");
        dto1.setModel("Camry");

        VehicleDTO dto2 = new VehicleDTO();
        dto2.setId(2L);
        dto2.setMake("Honda");
        dto2.setModel("Accord");

        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle1, vehicle2));
        when(vehicleMapper.toDTO(vehicle1)).thenReturn(dto1);
        when(vehicleMapper.toDTO(vehicle2)).thenReturn(dto2);

        List<VehicleDTO> result = vehicleService.getAllVehicles();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getMake()).isEqualTo("Toyota");
        assertThat(result.get(1).getMake()).isEqualTo("Honda");
    }

    @Test
    void shouldDeleteVehicleSuccessfully() {
        when(vehicleRepository.existsById(1L)).thenReturn(true);

        vehicleService.deleteVehicle(1L);

        verify(vehicleRepository).existsById(1L);
        verify(vehicleRepository).deleteById(1L);
    }




}
