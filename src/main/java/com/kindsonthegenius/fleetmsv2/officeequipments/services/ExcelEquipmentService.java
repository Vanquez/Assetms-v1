package com.kindsonthegenius.fleetmsv2.officeequipments.services;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.repositories.AssetRepository;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.repositories.OfficeEquipmentRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelEquipmentService {

    @Autowired
    private OfficeEquipmentRepository officeEquipmentRepository;


    public boolean saveExcelData(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<OfficeEquipment> officeEquipmentEntities = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row (first row)
            if (rows.hasNext()) {
                rows.next(); // Move to the next row (skipping the header)
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                OfficeEquipment officeEquipment = new OfficeEquipment();

                // Assuming column indexes start from 0
                officeEquipment.setName(row.getCell(0).getStringCellValue());
                officeEquipment.setIfmisNumber(row.getCell(1).getStringCellValue());
                officeEquipment.setGrzNumber(row.getCell(2).getStringCellValue());
                officeEquipment.setDateOfAcquisition(row.getCell(3).getDateCellValue());
                officeEquipment.setSerialNumber(row.getCell(4).getStringCellValue());
                officeEquipment.setCapitalizationAmount(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                officeEquipment.setRevaluationAmount(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                officeEquipment.setFairValue(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
                officeEquipment.setUseFullLife(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                officeEquipment.setDisposalAmount(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
                officeEquipment.setDisposalDate(row.getCell(10).getDateCellValue());
                officeEquipment.setDepreciationAmount(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
                officeEquipment.setNetBookValue(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));



//                vehicle.setFuelCapacity(row.getCell(2).getStringCellValue());

                officeEquipmentEntities.add(officeEquipment);

//                dataEntity.setField2((int) row.getCell(1).getNumericCellValue());
            }

            officeEquipmentRepository.saveAll(officeEquipmentEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
