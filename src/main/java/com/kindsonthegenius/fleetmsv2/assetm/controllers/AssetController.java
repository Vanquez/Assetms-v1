package com.kindsonthegenius.fleetmsv2.assetm.controllers;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.models.CSVDepreciatedAssets;
import com.kindsonthegenius.fleetmsv2.assetm.models.CSVHelperAssetComputer;
import com.kindsonthegenius.fleetmsv2.assetm.models.DepreciatedAsset;
import com.kindsonthegenius.fleetmsv2.assetm.services.AssetCategoryService;
import com.kindsonthegenius.fleetmsv2.assetm.services.AssetService;
import com.kindsonthegenius.fleetmsv2.assetm.services.ExcelAssetService;
import com.kindsonthegenius.fleetmsv2.fleet.models.CSVHelperVehicles;
import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.hr.services.EmployeeService;
import com.kindsonthegenius.fleetmsv2.mail.services.EmailService;
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
public class AssetController {

     @Autowired
     private AssetService assetService;
     @Autowired
     private AssetCategoryService assetCategoryService;

     @Autowired
     private EmployeeService employeeService;

     @Autowired
     private EmailService emailService;

     @Autowired
     private ExcelAssetService excelAssetService;




     public Model addModelAttributes(Model model){
         model.addAttribute("assetCategoryList", assetCategoryService.findAll());
         model.addAttribute("assetList", assetService.findAll());
         model.addAttribute("assignEmployee", employeeService.findAll());
//         model.addAttribute("depreciateAssets", assetService.findDepreciatedAssest());

        return model;
     }


     // Review assets page
     @GetMapping("/assetm/assets")
     public String findAll(Model model, String keyword){
         addModelAttributes(model);
         List<Asset> asset;
         try {
             if (keyword == null){
                 asset = assetService.findAll();
             } else {
                 asset = assetService.findByKeyword(keyword);

             }
             model.addAttribute("asset",asset);
             System.out.println(asset);
             System.out.println(keyword);
         } catch (Exception e){
             // Handle the exception, for example by logging it and setting an empty list or error message
             System.err.println("An error occurred while fetching employees:" + e.getMessage());
             asset = new ArrayList<>(); // or handle
             model.addAttribute("asset", asset);
             model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
         }
         return "assetm/Assets";
     }



      //  Endpoint to save an Asset
    @GetMapping("/assetm/assetadd")
    public String assetAdd(Model model) {
       addModelAttributes(model);
       model.addAttribute("asset", new Asset());
        return "/assetm/assetAdd";
    }

    // Method to save asset


//    @PostMapping("/register")
//    public String registerUser(User user, BindingResult result, Model model) {
//        if (user.getSsn() != null && userService.isSSNExists(user.getSsn())) {
//            result.rejectValue("ssn", "error.user", "SSN already exists");
//        }
//        if (result.hasErrors()) {
//            return "register";
//        }
//
//        userService.saveUser(user);
//        return "redirect:/success";
//    }


    @PostMapping(  "/assetm/assets")
    public String addNew(Asset asset, BindingResult result, Model model) {
        if (asset.getIfmisNumber() != null && assetService.isIfmisNumberExists(asset.getIfmisNumber())) {
            result.rejectValue("ifmisNumber", "error.user", "IFMIS Number already exists");
        }
        if (asset.getGrzNumber() != null && assetService.isGrzNumberExists(asset.getGrzNumber())) {
            result.rejectValue("grzNumber", "error.user", "GRZ Number already exists");
        }
//        if (asset.getAsset_serial_number() != null && assetService.isAssetSerialNumberExists(asset.getAsset_serial_number())) {
//            result.rejectValue("asset_serial_number", "error.user", "Asset Serial Number already exists");
//        }
        model.addAttribute("asset", new Asset());
        addModelAttributes(model);

        if (result.hasErrors()) {
          model.addAttribute("asset",asset);
            return "/assetm/assetAdd";
        }

        assetService.save(asset);
        return "redirect:/assetm/assets";
    }
   // From editing submission
    @PostMapping(  "/assetm/assetss")
    public String addNeww(Asset asset,Model model) {

        assetService.save(asset);
        return "redirect:/assetm/assets";
    }


    //The op parameter is either Edit or Details
    @GetMapping("/assetm/asset/{op}/{id}")
    public String editAsset(@PathVariable Integer id, @PathVariable String op, Model model){
        Asset asset = assetService.findById(id);
        model.addAttribute("asset", asset);
        addModelAttributes(model);
        return "/assetm/asset"+ op; //returns assetEdit or assetDetails
    }

    //The op parameter is either Edit or Details depreciated
    @GetMapping("/depreciated/asset/{op}/{id}")
    public String editDepreciatedAsset(@PathVariable Long id, @PathVariable String op, Model model){
        DepreciatedAsset asset = assetService.findByIdDepreciatedAsset(id);
        model.addAttribute("asset", asset);
        addModelAttributes(model);
        return "/assetm/asset"+ op; //returns assetEdit or assetDetails
    }

    @RequestMapping(value="/assetm/asset/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Integer id) {
        assetService.deleteById(id);
        return "redirect:/assetm/assets";
    }

    @RequestMapping(value="/depreciated/asset/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdDepreciatedAsset(@PathVariable Long id) {
        assetService.deleteById(id);
        return "redirect:/assetm/depreciatedassets";
    }

@GetMapping("/assetm/assignemployees")
    public String getAll(Model model){

           addModelAttributes(model);
         return "/assetm/assignEmployees";
    }

    @GetMapping("/assetm/assign/{op}/{id}")
    public String editAssign(@PathVariable int id, @PathVariable String op,
                             @RequestParam(value = "keyword", required = false) String keyword, Model model) {
        Employee employees = employeeService.findById(id);

        model.addAttribute("employee", employees);

        // Fetch assigned assets
        model.addAttribute("assigned", assetService.getAssets(employees));

        // Fetch unassigned assets by keyword
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("unassigned", assetService.findByKeywordUnassignedAsset(keyword));
        } else {
            model.addAttribute("unassigned", assetService.getNotAssets(employees));
        }

        return "/assetm/Assign" + op; // returns assignEdit or assignDetail
    }




    // Controller method to assign
    @RequestMapping("/assetm/asset/assign/{employeeId}/{assetId}")
    public String assignAsset(@PathVariable Integer employeeId,
                             @PathVariable Integer assetId, Model model) {
        assetService.assignAsset(employeeId, assetId);

        Employee employees = employeeService.findById(employeeId);
        model.addAttribute("employee", employees);
        model.addAttribute("assigned", assetService.getAssets(employees));
        model.addAttribute("unassigned", assetService.getNotAssets(employees));

        String email = employees.getEmail();
        emailService.sendEmail(email, "Email Testing from SpringBoot","This is a test email");

        return "redirect:/assetm/assign/Edit/" + employeeId;
    }

    // Controller method to unassign
    @RequestMapping("/assetm/asset/unassign/{employeeId}/{assetId}")
    public String unassignAsset(@PathVariable Integer employeeId,
                               @PathVariable Integer assetId) {
        assetService.unassignAsset(employeeId, assetId);
        return "redirect:/assetm/assign/Edit/" + employeeId;
    }

//    @GetMapping("/assetm/depreciatedassets")
//    public String depreciatedAssets(Model model){
//
//        assetService.migrateAssets();
//        addModelAttributes(model);
//
//         return "/assetm/depreciatedassets";
//    }

    // Review assets page
    @GetMapping("/assetm/depreciatedassets")
    public String findAllDepreciatedAssets(Model model, String keyword){
        addModelAttributes(model);
        assetService.migrateAssets();
        List<DepreciatedAsset> asset;
        try {
            if (keyword == null){
                asset = assetService.findDepreciatedAssest();
            } else {
                asset = assetService.findByKeywordDepreciatedAsset(keyword);

            }
            model.addAttribute("depreciateAssets",asset);
            System.out.println(asset);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            asset = new ArrayList<>(); // or handle
            model.addAttribute("depreciateAssets", asset);
            model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
        }
        return "/assetm/depreciatedassets";
    }

    @GetMapping("/assetm/asset/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "asset.csv";

        List<Asset> assets = assetService.findAll();
        ByteArrayInputStream inputStream = CSVHelperAssetComputer.assetComputerToCSV(assets);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/depreciated/asset/export")
    public ResponseEntity<Resource> exportDepreciatedAssetToCsv() throws IOException {
        String filename = "depreciatedDevices.csv";

        List<DepreciatedAsset> depreciatedAssets = assetService.findDepreciatedAssest();
        ByteArrayInputStream inputStream = CSVDepreciatedAssets.assetComputerToCSV(depreciatedAssets);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/assetm/asset/import")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        if (excelAssetService.saveExcelData(file)) {
            model.addAttribute("message", "File uploaded successfully!");
        } else {
            model.addAttribute("message", "Failed to upload file. Please try again.");
        }
        return "redirect:/assetm/assets";
    }






}
