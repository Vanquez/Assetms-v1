package com.kindsonthegenius.fleetmsv2.officeequipments.controllers;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.models.CSVHelperAssetComputer;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.CSVHelperEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipmentCategory;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.ExcelEquipmentService;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.OfficeEquipmentCategoryService;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.OfficeEquipmentService;
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
public class OfficeEquipmentController {

     @Autowired
     private OfficeEquipmentService officeEquipmentService;

     @Autowired
     private OfficeEquipmentCategoryService officeEquipmentCategoryService;

     @Autowired
     private ExcelEquipmentService excelEquipmentService;


     public Model addModelAttributes(Model model){

//         model.addAttribute("officeEquipments", officeEquipmentService.findAll());

         return model;
     }

    @GetMapping("/officeequipmenthome")
    public String officeEquipmentHome(){


        return "officeequipment/index";
    }
    @GetMapping("/officeequipments/equipments")
    public String officeEquipments(Model model, String keyword){
         addModelAttributes(model);
        List<OfficeEquipment> officeEquipments;
        try {
            if (keyword == null){
                officeEquipments = officeEquipmentService.findAll();
            } else {
                officeEquipments = officeEquipmentService.findByKeyword(keyword);

            }
            model.addAttribute("officeEquipments",officeEquipments);
            System.out.println(officeEquipments);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            officeEquipments = new ArrayList<>(); // or handle
            model.addAttribute("officeEquipments", officeEquipments);
            model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
        }

        return "/officeequipment/equipments";
    }



    @GetMapping("/officeequipment/equipmentadd")
    public String equipmentAdd(Model model){
         model.addAttribute("assetCategoryList", officeEquipmentCategoryService.findAll());
        model.addAttribute("officeEquipment", new OfficeEquipment());
        return "/officeequipment/equipmentAdd";
    }



    @RequestMapping("/officeequipment/equipment/{op}/{id}")
    public String findById(@PathVariable int id, @PathVariable String op, Model model){
       OfficeEquipment officeEquipmentt =  officeEquipmentService.findById(id);
        model.addAttribute("assetCategoryList", officeEquipmentCategoryService.findAll());

       model.addAttribute("officeEquipment", officeEquipmentt);
         return "/officeequipment/equipment" + op;
    }



    @PostMapping("/officeequipments/equipments")
    public String addNew(OfficeEquipment officeEquipment, BindingResult result, Model model){

        if (officeEquipment.getIfmisNumber() != null && officeEquipmentService.isIfmisNumberExists(officeEquipment.getIfmisNumber())) {
            result.rejectValue("ifmisNumber", "error.user", "IFMIS Number already exists");
        }
        if (officeEquipment.getGrzNumber() != null && officeEquipmentService.isGrzNumberExists(officeEquipment.getGrzNumber())) {
            result.rejectValue("grzNumber", "error.user", "GRZ Number already exists");
        }
        if (officeEquipment.getSerialNumber() != null && officeEquipmentService.isSerialNumberExists(officeEquipment.getSerialNumber())) {
            result.rejectValue("serialNumber", "error.user", "Asset Serial Number already exists");
        }
        model.addAttribute("officeEquipment", new OfficeEquipment());
        addModelAttributes(model);

        if (result.hasErrors()) {
            model.addAttribute("officeEquipment",officeEquipment);
            return "/officeequipment/equipmentAdd";
        }

        officeEquipmentService.save(officeEquipment);

        return "redirect:/officeequipments/equipments";
    }

    @PostMapping("/officeequipments/equipmentss")
    public String addNeww(OfficeEquipment officeEquipment,Model model){

        officeEquipmentService.save(officeEquipment);

        return "redirect:/officeequipments/equipments";
    }


    // Method to delete Equipments
    @RequestMapping(value = "/officeequipment/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteById(@PathVariable int id){

         officeEquipmentService.deleById(id);

         return "redirect:/officeequipments/equipments";
    }

    //Export
    @GetMapping("/officeequipment/equipment/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "equipment.csv";

        List<OfficeEquipment> officeEquipments = officeEquipmentService.findAll();
        ByteArrayInputStream inputStream = CSVHelperEquipment.equipmentToCSV(officeEquipments);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/officeequipment/equipment/import")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        if (excelEquipmentService.saveExcelData(file)) {
            model.addAttribute("message", "File uploaded successfully!");
        } else {
            model.addAttribute("message", "Failed to upload file. Please try again.");
        }
        return "redirect:/officeequipments/equipments";
    }

}
