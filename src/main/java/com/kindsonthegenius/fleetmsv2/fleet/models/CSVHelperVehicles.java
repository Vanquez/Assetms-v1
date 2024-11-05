package com.kindsonthegenius.fleetmsv2.fleet.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperVehicles {
    public static ByteArrayInputStream vehicleToCSV(List<Vehicle> vehicles) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "Vehicle Name", "Vehicle Type", "Vehicle Number","Registration Date","Description","Acquisition Date","Vehicle Make","Power","Fuel Capacity","Net Weight","Remarks","In Charge","Current Status");

            for (Vehicle vehicle : vehicles) {
                csvPrinter.printRecord(vehicle.getId(), vehicle.getName(), vehicle.getVehicleType(), vehicle.getVehicleNumber(), vehicle.getRegistrationDate(), vehicle.getDescription(),vehicle.getAcquisitionDate(), vehicle.getVehicleMake(), vehicle.getPower(), vehicle.getFuelCapacity(), vehicle.getNetWeight(), vehicle.getRemarks(), vehicle.getInCharge(), vehicle.getVehicleStatus());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}
