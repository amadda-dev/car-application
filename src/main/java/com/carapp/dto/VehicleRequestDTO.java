package com.carapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {
    private String make;
    private String model;
    private Integer car_year;
    private String vin;
    private String color;
    private Boolean sold;
    private Long customerId;
}