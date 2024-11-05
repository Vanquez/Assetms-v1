package com.kindsonthegenius.fleetmsv2.vehicle.controllers;

import com.kindsonthegenius.fleetmsv2.officeequipments.models.CSVHelperEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.vehicle.models.CSVHelperVehicleAsset;
import com.kindsonthegenius.fleetmsv2.vehicle.models.VehicleAsset;
import com.kindsonthegenius.fleetmsv2.vehicle.services.ExcelVehicleAssetService;
import com.kindsonthegenius.fleetmsv2.vehicle.services.VehicleAssetService;
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
public class VehicleAssetController {

    @Autowired
    private VehicleAssetService vehicleAssetService;

    @Autowired
    private ExcelVehicleAssetService excelVehicleAssetService;

    public Model addModelAttributes(Model model){

//         model.addAttribute("officeEquipments", officeEquipmentService.findAll());

        return model;
    }

    //Get vehicle asset home
    @GetMapping("/vehicleassethome")
    public String vehicleAsset(){


        return "/vehicleasset/index";
    }

    //Get vehicle asset page
    @GetMapping("/vehicleasset/assets")
    public String getvehicleAsset(Model model, String keyword){
        addModelAttributes(model);
        List<VehicleAsset> vehicleAssets;
        try {
            if (keyword == null){
                vehicleAssets = vehicleAssetService.findAll();
            } else {
                vehicleAssets = vehicleAssetService.findByKeyword(keyword);

            }
            model.addAttribute("vehicleAssets",vehicleAssets);
            System.out.println(vehicleAssets);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            vehicleAssets = new ArrayList<>(); // or handle
            model.addAttribute("vehicleAssets", vehicleAssets);
            model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
        }


        return "/vehicleasset/vehicleassets";
    }


    @GetMapping("/vehicleasset/assetadd")
    public String equipmentAdd(Model model){
        model.addAttribute("assetCategoryList", vehicleAssetService.findAll());
        model.addAttribute("vehicleAsset", new VehicleAsset());
        return "/vehicleasset/vehicleassetAdd";
    }



    @PostMapping("/vehicleasset/assets")
    public String addNew(VehicleAsset vehicleAsset, BindingResult result, Model model){

        if (vehicleAsset.getIfmisNumber() != null && vehicleAssetService.isIfmisNumberExists(vehicleAsset.getIfmisNumber())) {
            result.rejectValue("ifmisNumber", "error.user", "IFMIS Number already exists");
        }
        if (vehicleAsset.getGrzNumber() != null && vehicleAssetService.isGrzNumberExists(vehicleAsset.getGrzNumber())) {
            result.rejectValue("grzNumber", "error.user", "GRZ Number already exists");
        }
        if (vehicleAsset.getVehicleNumber() != null && vehicleAssetService.isVehicleNumberExists(vehicleAsset.getVehicleNumber())) {
            result.rejectValue("vehicleNumber", "error.user", "Asset Serial Number already exists");
        }

        model.addAttribute("vehicleAsset", new VehicleAsset());
        addModelAttributes(model);

        if (result.hasErrors()) {
            model.addAttribute("vehicleAsset", vehicleAsset);
            return "/vehicleasset/vehicleassetAdd";
        }

        vehicleAssetService.save(vehicleAsset);

        return "redirect:/vehicleasset/assets";
    }

    @PostMapping("/vehicleasset/assetss")
    public String addNeww(VehicleAsset vehicleAsset, Model model){

        vehicleAssetService.save(vehicleAsset);

        return "redirect:/vehicleasset/assets";
    }

    //Export
    @GetMapping("/vehicleasset/asset/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "vehicleasset.csv";

        List<VehicleAsset> vehicleAssets = vehicleAssetService.findAll();
        ByteArrayInputStream inputStream = CSVHelperVehicleAsset.vehicleToCSV(vehicleAssets);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @RequestMapping("/vehicleasset/asset/{op}/{id}")
    public String findById(@PathVariable int id, @PathVariable String op, Model model){
        VehicleAsset vehicleAsset =  vehicleAssetService.findById(id);

        model.addAttribute("vehicleAsset", vehicleAsset);

         System.out.println("test:"+vehicleAsset);
        return "/vehicleasset/vehicleasset" + op;
    }

    // Method to delete Equipments
    @RequestMapping(value = "/vehicleasset/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteById(@PathVariable int id){

        vehicleAssetService.deleById(id);

        return "redirect:/vehicleasset/assets";
    }


    @PostMapping("/vehicleasset/asset/import")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        if (excelVehicleAssetService.saveExcelData(file)) {
            model.addAttribute("message", "File uploaded successfully!");
        } else {
            model.addAttribute("message", "Failed to upload file. Please try again.");
        }
        return "redirect:/vehicleasset/assets";
    }

}
