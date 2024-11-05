package com.kindsonthegenius.fleetmsv2.officeequipments.controllers;

import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleType;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipmentCategory;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.OfficeEquipmentCategoryService;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.OfficeEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class OfficeEquipmentCategoryController {

    @Autowired
    private OfficeEquipmentService officeEquipmentService;

    @Autowired
    private OfficeEquipmentCategoryService officeEquipmentCategoryService;

    //Get equipment category page
    @GetMapping("/officeequipment/equipmentcategories")
    public String equipmentCategory(Model model) {
        model.addAttribute("category", officeEquipmentCategoryService.findAll());

        return "/officeequipment/equipmentCategories";
    }

    //Save equipment category
    @PostMapping("/officequipmentcategory/save")
    public String officeequipmentCategorySave(OfficeEquipmentCategory officeEquipmentCategory) {
        officeEquipmentCategoryService.save(officeEquipmentCategory);

        return "redirect:/officeequipment/equipmentcategories";

    }


    // Edit category or view Details
    @RequestMapping("/equipmentcategory/{id}")
    @ResponseBody
    public Optional<OfficeEquipmentCategory> findById(@PathVariable Integer id)
    {
        return officeEquipmentCategoryService.findById(id);
    }

    // Method to delete Equipments
    @RequestMapping(value = "/officeequipmentcatcategory/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdEquipment(@PathVariable int id){

        officeEquipmentCategoryService.deleById(id);

        return "redirect:/officeequipment/equipmentcategories";
    }

}
