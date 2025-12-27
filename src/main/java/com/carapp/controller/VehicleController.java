package com.carapp.controller;

import com.carapp.dto.VehicleDTO;
import com.carapp.dto.VehicleRequestDTO;
import com.carapp.dto.export.VehicleExportDTO;
import com.carapp.service.VehicleService;
import com.carapp.service.export.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CsvExportService csvExportService;

    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleRequestDTO requestDTO) {
        VehicleDTO createdVehicle = vehicleService.createVehicle(requestDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> searchVehicles(
            @RequestParam(required = false) Boolean sold,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer car_year,
            @RequestParam(required = false) String color
    ) {
        List<VehicleDTO> vehicles = vehicleService.searchVehicles(sold, make, model, car_year, color);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByCustomerId(@PathVariable Long customerId) {
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByCustomerId(customerId);
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequestDTO requestDTO) {
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, requestDTO);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportVehiclesToCSV() throws IOException {
        List<VehicleExportDTO> vehicles = vehicleService.getAllVehiclesForExport();
        byte[] csvData = csvExportService.exportVehiclesToCSV(vehicles);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "vehicles.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    @GetMapping("/sold")
    public ResponseEntity<List<VehicleDTO>> getSoldVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getSoldVehicles();
        return ResponseEntity.ok(vehicles);
    }
}