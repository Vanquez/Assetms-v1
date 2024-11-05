package com.kindsonthegenius.fleetmsv2.accounts.controllers;


import com.kindsonthegenius.fleetmsv2.procurement.models.Procurement;
import com.kindsonthegenius.fleetmsv2.procurement.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProcurementAccountsController {

    @Autowired
    private ProcurementService procurementService;


    public Model addModelAttributes(Model model){

//         model.addAttribute("officeEquipments", officeEquipmentService.findAll());

        return model;
    }



    @GetMapping("/accounts/accountsasset")
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

        return "/accounts/accountsasset";
    }


    //Method to approve order
    @PostMapping("/es/verify")
    public String approveProcurement(@RequestParam("status") String status, @RequestParam("id") Integer id, Model model) {
        // Call the service to update the procurement status
        boolean updated = procurementService.updateStatus(id, status);

        // Optionally handle the result of the update (e.g., show a message)
        if (updated) {
            model.addAttribute("message", "Procurement verified successfully!");
        } else {
            model.addAttribute("error", "Failed to verify the procurement. Please try again.");
        }

        // Redirect or return the appropriate view
        return "redirect:/accounts/accountsasset"; // Adjust the redirect as needed
    }

    //Method to approve order
    @PostMapping("/es/reject")
    public String rejectProcurement(@RequestParam("status") String status, @RequestParam("id") Integer id, Model model) {
        // Call the service to update the procurement status
        boolean updated = procurementService.updateStatus(id, status);

        // Optionally handle the result of the update (e.g., show a message)
        if (updated) {
            model.addAttribute("message", "Procurement rejected successfully!");
        } else {
            model.addAttribute("error", "Failed to reject the procurement. Please try again.");
        }

        // Redirect or return the appropriate view
        return "redirect:/accounts/accountsasset"; // Adjust the redirect as needed
    }
}
