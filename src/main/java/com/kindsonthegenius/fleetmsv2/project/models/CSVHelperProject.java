package com.kindsonthegenius.fleetmsv2.project.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperProject {
    public static ByteArrayInputStream projectToCSV(List<Project> projects) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord("Id", "project_name", "ifmisNumber","location","plotNumber","CommencementDate","EndDate","depreciationAmount","netBookValue");

            for (Project project : projects) {
                csvPrinter.printRecord(project.getId(),project.getName(),project.getIfmisNumber(),project.getLocation(),project.getPlotNumber(), project.getDateOfAcquisition(), project.getDisposalDate(), project.getDepreciationAmount(), project.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}