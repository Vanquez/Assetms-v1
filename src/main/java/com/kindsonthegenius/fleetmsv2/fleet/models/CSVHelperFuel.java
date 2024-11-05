package com.kindsonthegenius.fleetmsv2.fleet.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperFuel {
    public static ByteArrayInputStream fuelToCSV(List<VehicleFuel> fuels) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "Vehicle","Fuel Number", "Fill Date", "Quantity","Odometer Reading","Amount");

            for (VehicleFuel fuel : fuels) {
                csvPrinter.printRecord(fuel.getId(), fuel.getVehicle(), fuel.getVehicleNumber(), fuel.getFillDate(), fuel.getQuantity(),
                fuel.getOdometerReading(), fuel.getAmount());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}
