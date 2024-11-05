package com.kindsonthegenius.fleetmsv2.furnitureasset.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperFurniture {
    public static ByteArrayInputStream furnitureToCSV(List<Furniture> furnitures) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "furniture_name", "ifmisNumber","grzNumber","dateOfAcquisition","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (Furniture furniture : furnitures) {
                csvPrinter.printRecord(furniture.getId(),furniture.getName(),furniture.getIfmisNumber(), furniture.getGrzNumber(), furniture.getDateOfAcquisition(),  furniture.getCapitalizationAmount(),furniture.getRevaluationAmount(),furniture.getFairValue(), furniture.getUseFullLife(), furniture.getDisposalAmount(), furniture.getDisposalDate(), furniture.getDepreciationAmount(), furniture.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}