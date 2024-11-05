package com.kindsonthegenius.fleetmsv2.furnitureasset.controllers;

import com.kindsonthegenius.fleetmsv2.furnitureasset.models.FurnitureCategory;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.FurnitureCategoryService;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class FurnitureCategoryController {

    @Autowired
    private FurnitureService officeEquipmentService;

    @Autowired
    private FurnitureCategoryService furnitureCategoryService;

    //Get equipment category page
    @GetMapping("/furniture/furniturecategories")
    public String furnitureCategory(Model model) {
        model.addAttribute("category", furnitureCategoryService.findAll());

        return "/furniture/furnitureCategories";
    }

    //Save equipment category
    @PostMapping(value = "/furniturecategory/save")
    public String furnitureCategorySave(FurnitureCategory furnitureCategory) {
        furnitureCategoryService.save(furnitureCategory);

        return "redirect:/furniture/furniturecategories";

    }


    // Edit category or view Details
    @RequestMapping("/furniturecategory/{id}")
    @ResponseBody
    public Optional<FurnitureCategory> findByIdFurnitureCategory(@PathVariable Integer id)
    {
        return furnitureCategoryService.findById(id);
    }

    // Method to delete Equipments
    @RequestMapping(value = "/furniturecatcategory/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdFurnitureCategory(@PathVariable int id){

        furnitureCategoryService.deleById(id);

        return "redirect:/furniture/furniturecategories";
    }

}
