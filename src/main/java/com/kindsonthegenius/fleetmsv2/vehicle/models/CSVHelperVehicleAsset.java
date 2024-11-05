package com.kindsonthegenius.fleetmsv2.vehicle.models;

import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperVehicleAsset {
    public static ByteArrayInputStream vehicleToCSV(List<VehicleAsset> vehicleAssets) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
                                                                 csvPrinter.printRecord("Id", "vehicle_name","vehicleNumber", "ifmisNumber","grzNumber","dateOfAcquisition","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (VehicleAsset vehicleAsset : vehicleAssets) {
                csvPrinter.printRecord(vehicleAsset.getId(),vehicleAsset.getName(),vehicleAsset.getVehicleNumber(),vehicleAsset.getIfmisNumber(), vehicleAsset.getGrzNumber(), vehicleAsset.getDateOfAcquisition(),vehicleAsset.getCapitalizationAmount(),vehicleAsset.getRevaluationAmount(),vehicleAsset.getFairValue(), vehicleAsset.getUseFullLife(), vehicleAsset.getDisposalAmount(), vehicleAsset.getDisposalDate(), vehicleAsset.getDepreciationAmount(), vehicleAsset.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}