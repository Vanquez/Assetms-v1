package com.kindsonthegenius.fleetmsv2.buildingasset.controllers;

import com.kindsonthegenius.fleetmsv2.buildingasset.models.BuildingCategory;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.BuildingCategoryService;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BuildingCategoryController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingCategoryService buildingCategoryService;

    //Get equipment category page
    @GetMapping("/building/buildingcategories")
    public String furnitureCategory(Model model) {
        model.addAttribute("category", buildingCategoryService.findAll());

        return "/building/buildingCategories";
    }

    //Save equipment category
    @PostMapping(value = "/buildingcategory/save")
    public String furnitureCategorySave(BuildingCategory buildingCategory) {
        buildingCategoryService.save(buildingCategory);

        return "redirect:/building/buildingcategories";

    }


    // Edit category or view Details
    @RequestMapping("/buildingcategory/{id}")
    @ResponseBody
    public Optional<BuildingCategory> findByIdFurnitureCategory(@PathVariable Integer id)
    {
        return buildingCategoryService.findById(id);
    }

    // Method to delete Equipments
    @RequestMapping(value = "/buildingcatcategory/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdFurnitureCategory(@PathVariable int id){

        buildingCategoryService.deleById(id);

        return "redirect:/building/buildingcategories";
    }

}
