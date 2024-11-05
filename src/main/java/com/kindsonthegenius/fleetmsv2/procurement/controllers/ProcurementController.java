package com.kindsonthegenius.fleetmsv2.procurement.controllers;


import com.kindsonthegenius.fleetmsv2.assetm.services.AssetCategoryService;
import com.kindsonthegenius.fleetmsv2.assetm.services.AssetService;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.hr.services.EmployeeService;
import com.kindsonthegenius.fleetmsv2.mail.services.EmailService;
import com.kindsonthegenius.fleetmsv2.procurement.models.Procurement;
import com.kindsonthegenius.fleetmsv2.procurement.repositories.ProcurementRepository;
import com.kindsonthegenius.fleetmsv2.procurement.services.ProcurementService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProcurementController {

     @Autowired
     private ProcurementService procurementService;

     @Autowired
     private ProcurementRepository procurementRepository;

     @Autowired
     private EmployeeService employeeService;

     @Autowired
     private AssetService assetService;

     @Autowired
     private EmailService emailService;

     @Autowired
     private AssetCategoryService assetCategoryService;



    public Model addModelAttributes(Model model){
        model.addAttribute("assetCategoryList", assetCategoryService.findAll());
        model.addAttribute("assetList", assetService.findAll());
        model.addAttribute("assignEmployee", employeeService.findAll());
        model.addAttribute("depreciateAssets", assetService.findDepreciatedAssest());

        return model;
    }

    @GetMapping("/procurement")
    public String procurementHome(){


        return "procurement/index";
    }
    @GetMapping("/procurement/procurements")
    public String procurements(Model model, Integer keyword){
         addModelAttributes(model);
        List<Procurement> procurements;
        try {
            if (keyword == null){
                procurements = procurementService.findAll();
            } else {
                procurements = procurementService.findByKeyword(keyword);

            }
            model.addAttribute("procurements",procurements);
            System.out.println(procurements);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            procurements = new ArrayList<>(); // or handle
            model.addAttribute("procurements", procurements);
            model.addAttribute("errorMessage", "An error occurred while fetching procurements. Please try again later");
        }

        return "/procurement/procurements";
    }


    @GetMapping("/procurement/procurementadd")
    public String furnitureAdd(Model model){
        model.addAttribute("procurement", new Procurement());
        return "/procurement/procurementAdd";
    }



    @RequestMapping("/procurement/procurement/{op}/{id}")
    public String findByIdBuilding(@PathVariable int id, @PathVariable String op, Model model){
       Procurement procurement =  procurementService.findById(id);
        model.addAttribute("procurement", procurementService.findAll());

       model.addAttribute("procurement", procurement);
         return "/procurement/procurement" + op;
    }



    @PostMapping("/procurement/procurements")
    public String addNewFurniture(Procurement procurement, BindingResult result, Model model) {
        // Check if the Purchase Order Number already exists
        if (procurement.getPurchaseOrderNumber() != null &&
                procurementService.isPurchaseOrderNumberExists(procurement.getPurchaseOrderNumber())) {
            result.rejectValue("purchaseOrderNumber", "error.user", "Purchase Order Number already exists");
        }

        // Set status to "pending" if it's empty
        if (procurement.getStatus() == null || procurement.getStatus().isEmpty()) {
            procurement.setStatus("pending");
        }

        // Calculate total amount if amount is null
        if (procurement.getAmount() == null) {
            // Ensure price and quantity are not null before performing multiplication
            if (procurement.getPrice() != null && procurement.getQuantity() != null) {
                BigDecimal totalAmount = procurement.getPrice().multiply(new BigDecimal(procurement.getQuantity()));
                procurement.setAmount(totalAmount); // Set the calculated amount
            } else {
                // Optionally add an error message if price or quantity is null
                result.rejectValue("amount", "error.user", "Price and Quantity must not be null");
            }
        }

        // Add model attributes
        model.addAttribute("procurement", new Procurement());
        addModelAttributes(model);

        // Check for validation errors
        if (result.hasErrors()) {
            model.addAttribute("procurement", procurement);
            return "/procurement/procurementAdd";
        }

        // Save the procurement
        procurementService.save(procurement);

        // Redirect to procurements list
        return "redirect:/procurement/procurements";
    }


    @PostMapping("/procurement/procurementss")
    public String addNeww(Procurement procurement, Model model){

        procurementService.save(procurement);

        return "redirect:/procurement/procurements";
    }

//    //Method to approve order
//    @PostMapping("/es/approve")
//    public String approveProcurement(@RequestParam("status") String status, @RequestParam("id") Integer id, Model model) {
//        // Call the service to update the procurement status
//        boolean updated = procurementService.updateStatus(id, status);
//
//        // Optionally handle the result of the update (e.g., show a message)
//        if (updated) {
//            model.addAttribute("message", "Procurement approved successfully!");
//        } else {
//            model.addAttribute("error", "Failed to approve the procurement. Please try again.");
//        }
//
//        // Redirect or return the appropriate view
//        return "redirect:/procurement/procurements"; // Adjust the redirect as needed
//    }
//
//    //Method to approve order
//    @PostMapping("/es/reject")
//    public String rejectProcurement(@RequestParam("status") String status, @RequestParam("id") Integer id, Model model) {
//        // Call the service to update the procurement status
//        boolean updated = procurementService.updateStatus(id, status);
//
//        // Optionally handle the result of the update (e.g., show a message)
//        if (updated) {
//            model.addAttribute("message", "Procurement approved successfully!");
//        } else {
//            model.addAttribute("error", "Failed to approve the procurement. Please try again.");
//        }
//
//        // Redirect or return the appropriate view
//        return "redirect:/procurement/procurements"; // Adjust the redirect as needed
//    }


    // Method to delete orders
    @RequestMapping(value = "/procurement/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdFurniture(@PathVariable int id){

         procurementService.deleById(id);

         return "redirect:/procurement/procurements";
    }

//    @GetMapping("/procurement/assignemployees")
//    public String procurementGetAll(Model model){
//
//        addModelAttributes(model);
//        return "/procurement/procurementAssignEmployees";
//    }

    //Get All Employees
    @GetMapping("/procurement/assignemployees")
    public String procurementGetAll(Model model, String keyword) {
        addModelAttributes(model);
        List<Employee> employees;
        try {
            if (keyword == null) {
                employees = employeeService.findAll();
            } else {
                employees = employeeService.findByKeyword(keyword);
            }
            model.addAttribute("assignEmployee", employees);
            System.out.println(employees);
        } catch (Exception e) {
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            employees = new ArrayList<>(); // or handle
            model.addAttribute("employees", employees);
            model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
        }

        return "/procurement/procurementAssignEmployees";
    }

    @GetMapping("/procurement/assign/{op}/{id}")
    public String procurementEditAssign(@PathVariable int id, @PathVariable String op,
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

        return "/procurement/Assign" + op; // returns assignEdit or assignDetail
    }




    // Controller method to assign
    @RequestMapping("/procurement/asset/assign/{employeeId}/{assetId}")
    public String procurementAssignAsset(@PathVariable Integer employeeId,
                              @PathVariable Integer assetId, Model model) {
        assetService.assignAsset(employeeId, assetId);

        Employee employees = employeeService.findById(employeeId);
        model.addAttribute("employee", employees);
        model.addAttribute("assigned", assetService.getAssets(employees));
        model.addAttribute("unassigned", assetService.getNotAssets(employees));

        String email = employees.getEmail();
        String name = employees.getFullName();


        emailService.sendEmail(email, "Smart Zambia Device","Hi"+" "+name+" "+"you have been assigned a device collect it from procurement department");

        return "redirect:/procurement/assign/Edit/" + employeeId;
    }

    // Controller method to unassign
    @RequestMapping("/procurement/asset/unassign/{employeeId}/{assetId}")
    public String procurementUnassignAsset(@PathVariable Integer employeeId,
                                @PathVariable Integer assetId) {
        assetService.unassignAsset(employeeId, assetId);
        return "redirect:/procurement/assign/Edit/" + employeeId;
    }







}
