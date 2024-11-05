package com.kindsonthegenius.fleetmsv2.buildingasset.controllers;


import com.kindsonthegenius.fleetmsv2.buildingasset.models.CSVHelperFurniture;
import com.kindsonthegenius.fleetmsv2.buildingasset.models.Building;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.ExcelBuildingService;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.BuildingCategoryService;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BuildingController {

     @Autowired
     private BuildingService buildingService;

     @Autowired
     private BuildingCategoryService buildingCategoryService;

     @Autowired
     private ExcelBuildingService excelBuildingService;


     public Model addModelAttributes(Model model){

//         model.addAttribute("officeEquipments", officeEquipmentService.findAll());

         return model;
     }

    @GetMapping("/buildinghome")
    public String buildingHome(){


        return "building/index";
    }
    @GetMapping("/building/buildings")
    public String buildingEquipments(Model model, String keyword){
         addModelAttributes(model);
        List<Building> buildings;
        try {
            if (keyword == null){
                buildings = buildingService.findAll();
            } else {
                buildings = buildingService.findByKeyword(keyword);

            }
            model.addAttribute("buildings",buildings);
            System.out.println(buildings);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            buildings = new ArrayList<>(); // or handle
            model.addAttribute("buildings", buildings);
            model.addAttribute("errorMessage", "An error occurred while fetching furnitures. Please try again later");
        }

        return "/building/buildings";
    }


    @GetMapping("/building/buildingadd")
    public String furnitureAdd(Model model){
         model.addAttribute("buildingCategoryList", buildingCategoryService.findAll());
        model.addAttribute("building", new Building());
        return "/building/buildingAdd";
    }



    @RequestMapping("/building/building/{op}/{id}")
    public String findByIdBuilding(@PathVariable int id, @PathVariable String op, Model model){
       Building building =  buildingService.findById(id);
        model.addAttribute("buildingCategoryList", buildingCategoryService.findAll());

       model.addAttribute("building", building);
         return "/building/building" + op;
    }



    @PostMapping("/building/buildings")
    public String addNewFurniture(Building building, BindingResult result, Model model){

        if (building.getIfmisNumber() != null && buildingService.isIfmisNumberExists(building.getIfmisNumber())) {
            result.rejectValue("ifmisNumber", "error.user", "IFMIS Number already exists");
        }
        if (building.getGrzNumber() != null && buildingService.isGrzNumberExists(building.getGrzNumber())) {
            result.rejectValue("grzNumber", "error.user", "GRZ Number already exists");
        }

        model.addAttribute("building", new Building());
        addModelAttributes(model);

        if (result.hasErrors()) {
            model.addAttribute("building",building);
            return "/building/buildingAdd";
        }

        buildingService.save(building);

        return "redirect:/building/buildings";
    }

    @PostMapping("/building/buildingss")
    public String addNeww(Building building, Model model){

        buildingService.save(building);

        return "redirect:/building/buildings";
    }


    // Method to delete Equipments
    @RequestMapping(value = "/building/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdFurniture(@PathVariable int id){

         buildingService.deleById(id);

         return "redirect:/building/buildings";
    }

    //Export
    @GetMapping("/building/building/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "building.csv";

        List<Building> buildings = buildingService.findAll();
        ByteArrayInputStream inputStream = CSVHelperFurniture.furnitureToCSV(buildings);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/building/building/import")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        if (excelBuildingService.saveExcelData(file)) {
            model.addAttribute("message", "File uploaded successfully!");
        } else {
            model.addAttribute("message", "Failed to upload file. Please try again.");
        }
        return "redirect:/building/buildings";
    }

}
