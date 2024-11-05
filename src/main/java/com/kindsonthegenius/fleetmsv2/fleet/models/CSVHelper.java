package com.kindsonthegenius.fleetmsv2.fleet.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelper {
    public static ByteArrayInputStream maintenancesToCSV(List<VehicleMaintenance> maintenances) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "Vehicle", "Start Date", "Remarks","Company","Cost");

            for (VehicleMaintenance maintenance : maintenances) {
                csvPrinter.printRecord(maintenance.getId(), maintenance.getVehicle(),
                        maintenance.getStartDate(), maintenance.getRemarks(), maintenance.getCompany(), maintenance.getPrice());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}
