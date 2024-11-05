package com.kindsonthegenius.fleetmsv2.buildingasset.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperFurniture {
    public static ByteArrayInputStream furnitureToCSV(List<Building> buildings) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "building_name", "ifmisNumber","grzNumber","location","plotNumber","dateOfAcquisition","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (Building building : buildings) {
                csvPrinter.printRecord(building.getId(),building.getName(),building.getIfmisNumber(), building.getGrzNumber(),building.getLocation(),building.getPlotNumber(), building.getDateOfAcquisition(),  building.getCapitalizationAmount(),building.getRevaluationAmount(),building.getFairValue(), building.getUseFullLife(), building.getDisposalAmount(), building.getDisposalDate(), building.getDepreciationAmount(), building.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}