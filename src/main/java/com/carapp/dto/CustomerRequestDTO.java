package com.carapp.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
}