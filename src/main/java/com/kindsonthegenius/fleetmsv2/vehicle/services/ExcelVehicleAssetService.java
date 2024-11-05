package com.kindsonthegenius.fleetmsv2.vehicle.services;


import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.repositories.OfficeEquipmentRepository;
import com.kindsonthegenius.fleetmsv2.vehicle.models.VehicleAsset;
import com.kindsonthegenius.fleetmsv2.vehicle.repositories.VehicleAssetRepository;
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
public class ExcelVehicleAssetService {

    @Autowired
    private VehicleAssetRepository vehicleAssetRepository;


    public boolean saveExcelData(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<VehicleAsset> vehicleAssetEntities = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row (first row)
            if (rows.hasNext()) {
                rows.next(); // Move to the next row (skipping the header)
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                VehicleAsset vehicleAsset = new VehicleAsset();

                // Assuming column indexes start from 0
                vehicleAsset.setName(row.getCell(0).getStringCellValue());
                vehicleAsset.setVehicleNumber(row.getCell(1).getStringCellValue());
                vehicleAsset.setIfmisNumber(row.getCell(2).getStringCellValue());
                vehicleAsset.setGrzNumber(row.getCell(3).getStringCellValue());
                vehicleAsset.setDateOfAcquisition(row.getCell(4).getDateCellValue());
                vehicleAsset.setCapitalizationAmount(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                vehicleAsset.setRevaluationAmount(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                vehicleAsset.setFairValue(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
                vehicleAsset.setUseFullLife(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                vehicleAsset.setDisposalAmount(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
                vehicleAsset.setDisposalDate(row.getCell(10).getDateCellValue());
                vehicleAsset.setDepreciationAmount(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
                vehicleAsset.setNetBookValue(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));



//                vehicle.setFuelCapacity(row.getCell(2).getStringCellValue());

                vehicleAssetEntities.add(vehicleAsset);

//                dataEntity.setField2((int) row.getCell(1).getNumericCellValue());
            }

            vehicleAssetRepository.saveAll(vehicleAssetEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

