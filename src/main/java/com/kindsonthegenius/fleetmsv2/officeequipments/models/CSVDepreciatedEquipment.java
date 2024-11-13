package com.kindsonthegenius.fleetmsv2.officeequipments.models;

import com.kindsonthegenius.fleetmsv2.assetm.models.DepreciatedAsset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVDepreciatedEquipment {
    public static ByteArrayInputStream assetComputerToCSV(List<DepreciatedOfficeEquipment> depreciatedAsset) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "asset_name", "asset_description", "asset_serial_number","ifmisNumber","grzNumber","dateOfAcquisition","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (DepreciatedOfficeEquipment depreciatedAssets : depreciatedAsset) {
                csvPrinter.printRecord(depreciatedAssets.getId(), depreciatedAssets.getName(), depreciatedAssets.getDescription(), depreciatedAssets.getSerialNumber(),depreciatedAssets.getIfmisNumber(), depreciatedAssets.getGrzNumber(), depreciatedAssets.getDateOfAcquisition(), depreciatedAssets.getCapitalizationAmount(),depreciatedAssets.getFairValue(),depreciatedAssets.getUseFullLife(),depreciatedAssets.getDisposalAmount(), depreciatedAssets.getDisposalDate(),depreciatedAssets.getDepreciationAmount(),depreciatedAssets.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }


}
