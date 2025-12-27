package com.carapp.dto.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleExportDTO {
    private String make;
    private String model;
    private Integer car_year;
    private String color;
    private Boolean sold;
    private String customerName;
}