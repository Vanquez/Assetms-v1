package com.kindsonthegenius.fleetmsv2.furnitureasset.controllers;


import com.kindsonthegenius.fleetmsv2.furnitureasset.models.CSVHelperFurniture;
import com.kindsonthegenius.fleetmsv2.furnitureasset.models.Furniture;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.ExcelFurnitureService;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.FurnitureCategoryService;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.FurnitureService;
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
public class FurnitureController {

     @Autowired
     private FurnitureService furnitureService;

     @Autowired
     private FurnitureCategoryService furnitureCategoryService;

     @Autowired
     private ExcelFurnitureService excelFurnitureService;


     public Model addModelAttributes(Model model){

//         model.addAttribute("officeEquipments", officeEquipmentService.findAll());

         return model;
     }

    @GetMapping("/furniturehome")
    public String furnitureHome(){


        return "furniture/index";
    }
    @GetMapping("/furniture/furnitures")
    public String furnitureEquipments(Model model, String keyword){
         addModelAttributes(model);
        List<Furniture> furnitures;
        try {
            if (keyword == null){
                furnitures = furnitureService.findAll();
            } else {
                furnitures = furnitureService.findByKeyword(keyword);

            }
            model.addAttribute("furnitures",furnitures);
            System.out.println(furnitures);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            furnitures = new ArrayList<>(); // or handle
            model.addAttribute("furnitures", furnitures);
            model.addAttribute("errorMessage", "An error occurred while fetching furnitures. Please try again later");
        }

        return "/furniture/furnitures";
    }


    @GetMapping("/furniture/furnitureadd")
    public String furnitureAdd(Model model){
         model.addAttribute("furnitureCategoryList", furnitureCategoryService.findAll());
        model.addAttribute("furniture", new Furniture());
        return "/furniture/furnitureAdd";
    }



    @RequestMapping("/furniture/furniture/{op}/{id}")
    public String findByIdFurniture(@PathVariable int id, @PathVariable String op, Model model){
       Furniture furniture =  furnitureService.findById(id);
        model.addAttribute("furnitureCategoryList", furnitureCategoryService.findAll());

       model.addAttribute("furniture", furniture);
         return "/furniture/furniture" + op;
    }



    @PostMapping("/furniture/furnitures")
    public String addNewFurniture(Furniture furniture, BindingResult result, Model model){

        if (furniture.getIfmisNumber() != null && furnitureService.isIfmisNumberExists(furniture.getIfmisNumber())) {
            result.rejectValue("ifmisNumber", "error.user", "IFMIS Number already exists");
        }
        if (furniture.getGrzNumber() != null && furnitureService.isGrzNumberExists(furniture.getGrzNumber())) {
            result.rejectValue("grzNumber", "error.user", "GRZ Number already exists");
        }

        model.addAttribute("furniture", new Furniture());
        addModelAttributes(model);

        if (result.hasErrors()) {
            model.addAttribute("furniture",furniture);
            return "/furniture/furnitureAdd";
        }

        furnitureService.save(furniture);

        return "redirect:/furniture/furnitures";
    }

    @PostMapping("/furniture/furnituress")
    public String addNeww(Furniture furniture, Model model){

        furnitureService.save(furniture);

        return "redirect:/furniture/furnitures";
    }


    // Method to delete Equipments
    @RequestMapping(value = "/furniture/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdFurniture(@PathVariable int id){

         furnitureService.deleById(id);

         return "redirect:/furniture/furnitures";
    }

    //Export
    @GetMapping("/furniture/furniture/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "furniture.csv";

        List<Furniture> furnitures = furnitureService.findAll();
        ByteArrayInputStream inputStream = CSVHelperFurniture.furnitureToCSV(furnitures);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/furniture/furniture/import")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        if (excelFurnitureService.saveExcelData(file)) {
            model.addAttribute("message", "File uploaded successfully!");
        } else {
            model.addAttribute("message", "Failed to upload file. Please try again.");
        }
        return "redirect:/furniture/furnitures";
    }

}
