package com.kindsonthegenius.fleetmsv2.project.controllers;

import com.kindsonthegenius.fleetmsv2.project.models.ProjectCategory;
import com.kindsonthegenius.fleetmsv2.project.services.ProjectCategoryService;
import com.kindsonthegenius.fleetmsv2.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ProjectCategoryController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    //Get equipment category page
    @GetMapping("/project/projectcategories")
    public String projectCategory(Model model) {
        model.addAttribute("category", projectCategoryService.findAll());

        return "/project/projectCategories";
    }

    //Save equipment category
    @PostMapping(value = "/projectcategory/save")
    public String projectCategorySave(ProjectCategory projectCategory) {
        projectCategoryService.save(projectCategory);

        return "redirect:/project/projectcategories";

    }


    // Edit category or view Details
    @RequestMapping("/projectcategory/{id}")
    @ResponseBody
    public Optional<ProjectCategory> findByIdProjectCategory(@PathVariable Integer id)
    {
        return projectCategoryService.findById(id);
    }

    // Method to delete Projects
    @RequestMapping(value = "/project/projectcatcategory/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdProjectCategory(@PathVariable int id){

        projectCategoryService.deleById(id);

        return "redirect:/project/projectcategories";
    }

}
