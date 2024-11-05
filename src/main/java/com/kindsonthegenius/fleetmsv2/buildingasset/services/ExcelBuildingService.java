package com.kindsonthegenius.fleetmsv2.buildingasset.services;

import com.kindsonthegenius.fleetmsv2.buildingasset.models.Building;
import com.kindsonthegenius.fleetmsv2.buildingasset.repositories.BuildingRepository;
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
public class ExcelBuildingService {

    @Autowired
    private BuildingRepository buildingRepository;


    public boolean saveExcelData(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Building> buildingEntities = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row (first row)
            if (rows.hasNext()) {
                rows.next(); // Move to the next row (skipping the header)
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                Building building = new Building();

                // Assuming column indexes start from 0
                building.setName(row.getCell(0).getStringCellValue());
                building.setIfmisNumber(row.getCell(1).getStringCellValue());
                building.setGrzNumber(row.getCell(2).getStringCellValue());
                building.setLocation(row.getCell(3).getStringCellValue());
                building.setPlotNumber(row.getCell(4).getStringCellValue());
                building.setDateOfAcquisition(row.getCell(5).getDateCellValue());
                building.setCapitalizationAmount(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                building.setRevaluationAmount(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
                building.setFairValue(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                building.setUseFullLife(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
                building.setDisposalAmount(BigDecimal.valueOf(row.getCell(10).getNumericCellValue()));
                building.setDisposalDate(row.getCell(11).getDateCellValue());
                building.setDepreciationAmount(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
                building.setNetBookValue(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));



//                vehicle.setFuelCapacity(row.getCell(2).getStringCellValue());

                buildingEntities.add(building);

//                dataEntity.setField2((int) row.getCell(1).getNumericCellValue());
            }

            buildingRepository.saveAll(buildingEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
