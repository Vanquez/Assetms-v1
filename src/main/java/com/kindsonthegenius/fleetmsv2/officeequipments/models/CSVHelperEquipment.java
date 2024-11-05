package com.kindsonthegenius.fleetmsv2.officeequipments.models;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelperEquipment {
    public static ByteArrayInputStream equipmentToCSV(List<OfficeEquipment> officeEquipments) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
                                                                      csvPrinter.printRecord("Id", "equipment_name", "ifmisNumber","grzNumber","dateOfAcquisition","serialNumber","capitalizationAmount","revaluationAmount","fairValue","useFullLife","disposalAmount","disposalDate","depreciationAmount","netBookValue");

            for (OfficeEquipment officeEquipment : officeEquipments) {
                csvPrinter.printRecord(officeEquipment.getId(),officeEquipment.getName(),officeEquipment.getIfmisNumber(), officeEquipment.getGrzNumber(), officeEquipment.getDateOfAcquisition(), officeEquipment.getSerialNumber(), officeEquipment.getCapitalizationAmount(),officeEquipment.getRevaluationAmount(),officeEquipment.getFairValue(), officeEquipment.getUseFullLife(), officeEquipment.getDisposalAmount(), officeEquipment.getDisposalDate(), officeEquipment.getDepreciationAmount(), officeEquipment.getNetBookValue());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV file: " + e.getMessage());
        }
    }
}