package com.carapp.dto.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExportDTO {
    private String name;
    private String email;
    private String phone;

}
