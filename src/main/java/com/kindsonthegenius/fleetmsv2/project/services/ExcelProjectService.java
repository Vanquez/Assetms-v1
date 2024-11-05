package com.kindsonthegenius.fleetmsv2.project.services;

import com.kindsonthegenius.fleetmsv2.project.models.Project;
import com.kindsonthegenius.fleetmsv2.project.repositories.ProjectRepository;
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
public class ExcelProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public boolean saveExcelData(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Project> projectEntities = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row (first row)
            if (rows.hasNext()) {
                rows.next(); // Move to the next row (skipping the header)
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                Project project = new Project();

                // Assuming column indexes start from 0
                project.setName(row.getCell(0).getStringCellValue());
                project.setIfmisNumber(row.getCell(1).getStringCellValue());
                project.setLocation(row.getCell(2).getStringCellValue());
                project.setPlotNumber(row.getCell(3).getStringCellValue());
                project.setDateOfAcquisition(row.getCell(4).getDateCellValue());
                project.setDisposalDate(row.getCell(5).getDateCellValue());
                project.setDepreciationAmount(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                project.setNetBookValue(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));



//                vehicle.setFuelCapacity(row.getCell(2).getStringCellValue());

                projectEntities.add(project);

//                dataEntity.setField2((int) row.getCell(1).getNumericCellValue());
            }

            projectRepository.saveAll(projectEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
