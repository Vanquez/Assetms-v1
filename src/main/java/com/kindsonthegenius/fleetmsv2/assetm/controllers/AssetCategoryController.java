package com.kindsonthegenius.fleetmsv2.assetm.controllers;

import com.kindsonthegenius.fleetmsv2.assetm.models.AssetCategory;
import com.kindsonthegenius.fleetmsv2.assetm.services.AssetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;





@Controller
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;
    // Method to save asset category


    // Method to return Asset Categories
    @GetMapping("/assetm/assetcategories")
    public String getAssetCategories(Model model){

          model.addAttribute("assetCategoryList", assetCategoryService.findAll());
        return "/assetm/assetCategories";
    }

    @PostMapping(value="/assetm/assetcategories")
    public String addNew(AssetCategory assetCategory) {
        assetCategoryService.save(assetCategory);
        return "redirect:/assetm/assetCategories";
    }

}
