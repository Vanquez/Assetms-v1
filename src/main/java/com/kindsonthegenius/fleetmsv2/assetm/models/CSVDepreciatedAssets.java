package com.kindsonthegenius.fleetmsv2.assetm.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVDepreciatedAssets {
    public static ByteArrayInputStream assetComputerToCSV(List<DepreciatedAsset> depreciatedAsset) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "asset_name", "asset_description", "asset_model","asset_serial_number","ifmisNumber","grzNumber","dateOfAcquisition","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (DepreciatedAsset depreciatedAssets : depreciatedAsset) {
                csvPrinter.printRecord(depreciatedAssets.getAsset_id(), depreciatedAssets.getAsset_name(), depreciatedAssets.getAsset_description(), depreciatedAssets.getAsset_model(),depreciatedAssets.getAsset_serial_number(),depreciatedAssets.getIfmisNumber(), depreciatedAssets.getGrzNumber(), depreciatedAssets.getDateOfAcquisition(), depreciatedAssets.getCapitalizationAmount(),depreciatedAssets.getFairValue(),depreciatedAssets.getUseFullLife(),depreciatedAssets.getDisposalAmount(), depreciatedAssets.getDisposalDate(),depreciatedAssets.getDepreciationAmount(),depreciatedAssets.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}
