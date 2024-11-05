package com.kindsonthegenius.fleetmsv2.furnitureasset.services;

import com.kindsonthegenius.fleetmsv2.furnitureasset.models.Furniture;
import com.kindsonthegenius.fleetmsv2.furnitureasset.repositories.FurnitureRepository;
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
public class ExcelFurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;


    public boolean saveExcelData(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Furniture> furnitureEntities = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row (first row)
            if (rows.hasNext()) {
                rows.next(); // Move to the next row (skipping the header)
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                Furniture furniture = new Furniture();

                // Assuming column indexes start from 0
                furniture.setName(row.getCell(0).getStringCellValue());
                furniture.setIfmisNumber(row.getCell(1).getStringCellValue());
                furniture.setGrzNumber(row.getCell(2).getStringCellValue());
                furniture.setDateOfAcquisition(row.getCell(3).getDateCellValue());
                furniture.setCapitalizationAmount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
                furniture.setRevaluationAmount(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                furniture.setFairValue(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                furniture.setUseFullLife(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
                furniture.setDisposalAmount(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                furniture.setDisposalDate(row.getCell(10).getDateCellValue());
                furniture.setDepreciationAmount(BigDecimal.valueOf(row.getCell(10).getNumericCellValue()));
                furniture.setNetBookValue(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));



//                vehicle.setFuelCapacity(row.getCell(2).getStringCellValue());

                furnitureEntities.add(furniture);

//                dataEntity.setField2((int) row.getCell(1).getNumericCellValue());
            }

            furnitureRepository.saveAll(furnitureEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
