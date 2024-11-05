package com.kindsonthegenius.fleetmsv2.assetm.models;

import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperAssetComputer {
    public static ByteArrayInputStream assetComputerToCSV(List<Asset> assets) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "asset_name", "asset_description", "asset_model","asset_serial_number","ifmisNumber","grzNumber","dateOfAcquisition","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (Asset asset : assets) {
                csvPrinter.printRecord(asset.getAsset_id(), asset.getAsset_name(), asset.getAsset_description(), asset.getAsset_model(),asset.getAsset_serial_number(),asset.getIfmisNumber(), asset.getGrzNumber(), asset.getDateOfAcquisition(), asset.getCapitalizationAmount(),asset.getFairValue(),asset.getUseFullLife(),asset.getDisposalAmount(), asset.getDisposalDate(),asset.getDepreciationAmount(),asset.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}
