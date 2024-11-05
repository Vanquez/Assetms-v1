package com.kindsonthegenius.fleetmsv2.assetm.services;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.repositories.AssetRepository;
import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.fleet.repositories.VehicleRepository;
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
public class ExcelAssetService {

    @Autowired
    private AssetRepository assetRepository;


    public boolean saveExcelData(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Asset> assetEntities = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row (first row)
            if (rows.hasNext()) {
                rows.next(); // Move to the next row (skipping the header)
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                Asset asset = new Asset();

                // Assuming column indexes start from 0
                asset.setAsset_name(row.getCell(0).getStringCellValue());
                asset.setAsset_description(row.getCell(1).getStringCellValue());
                asset.setAsset_model(row.getCell(2).getStringCellValue());
                asset.setAsset_serial_number(row.getCell(3).getStringCellValue());
                asset.setIfmisNumber(row.getCell(4).getStringCellValue());
                asset.setGrzNumber(row.getCell(5).getStringCellValue());
                asset.setDateOfAcquisition(row.getCell(6).getDateCellValue());
                asset.setCapitalizationAmount(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
                asset.setRevaluationAmount(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                asset.setFairValue(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
                asset.setUseFullLife(BigDecimal.valueOf(row.getCell(10).getNumericCellValue()));
                asset.setDisposalAmount(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
                asset.setDisposalDate(row.getCell(12).getDateCellValue());
                asset.setDepreciationAmount(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));
                asset.setNetBookValue(BigDecimal.valueOf(row.getCell(14).getNumericCellValue()));



//                vehicle.setFuelCapacity(row.getCell(2).getStringCellValue());

                assetEntities.add(asset);

//                dataEntity.setField2((int) row.getCell(1).getNumericCellValue());
            }

            assetRepository.saveAll(assetEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
