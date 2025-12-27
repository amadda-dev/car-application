package com.carapp.service.export;

import com.carapp.dto.export.CustomerExportDTO;
import com.carapp.dto.export.VehicleExportDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class CsvExportService {

    public byte[] exportCustomersToCSV(List<CustomerExportDTO> customers) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (CSVPrinter printer = new CSVPrinter(
                new OutputStreamWriter(out),
                CSVFormat.DEFAULT.withHeader("Name", "Email", "Phone"))) {

            for (CustomerExportDTO customer : customers) {
                printer.printRecord(
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhone()
                );
            }

            printer.flush();
        }

        return out.toByteArray();
    }

    public byte[] exportVehiclesToCSV(List<VehicleExportDTO> vehicles) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (CSVPrinter printer = new CSVPrinter(
                new OutputStreamWriter(out),
                CSVFormat.DEFAULT.withHeader("Make", "Model", "Year", "Color", "Customer Name"))) {

            for (VehicleExportDTO vehicle : vehicles) {
                printer.printRecord(
                        vehicle.getMake(),
                        vehicle.getModel(),
                        vehicle.getCar_year(),
                        vehicle.getColor(),
                        vehicle.getCustomerName() != null ? vehicle.getCustomerName() : "N/A"
                );
            }

            printer.flush();
        }

        return out.toByteArray();
    }
}