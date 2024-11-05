package com.kindsonthegenius.fleetmsv2.project.controllers;


import com.kindsonthegenius.fleetmsv2.project.models.Project;
import com.kindsonthegenius.fleetmsv2.project.models.CSVHelperProject;
import com.kindsonthegenius.fleetmsv2.project.services.ProjectCategoryService;
import com.kindsonthegenius.fleetmsv2.project.services.ProjectService;
import com.kindsonthegenius.fleetmsv2.project.services.ExcelProjectService;
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
public class ProjectController {

     @Autowired
     private ProjectService projectService;

     @Autowired
     private ProjectCategoryService projectCategoryService;

     @Autowired
     private ExcelProjectService excelProjectService;


     public Model addModelAttributes(Model model){

//         model.addAttribute("officeEquipments", officeEquipmentService.findAll());

         return model;
     }

    @GetMapping("/projecthome")
    public String buildingHome(){


        return "project/index";
    }
    @GetMapping("/project/projects")
    public String buildingEquipments(Model model, String keyword){
         addModelAttributes(model);
        List<Project> projects;
        try {
            if (keyword == null){
                projects = projectService.findAll();
            } else {
                projects = projectService.findByKeyword(keyword);

            }
            model.addAttribute("projects",projects);
            System.out.println(projects);
            System.out.println(keyword);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            projects = new ArrayList<>(); // or handle
            model.addAttribute("projects", projects);
            model.addAttribute("errorMessage", "An error occurred while fetching furnitures. Please try again later");
        }

        return "/project/projects";
    }


    @GetMapping("/project/projectadd")
    public String furnitureAdd(Model model){
         model.addAttribute("projectCategoryList", projectCategoryService.findAll());
        model.addAttribute("project", new Project());
        return "/project/projectAdd";
    }



    @RequestMapping("/project/project/{op}/{id}")
    public String findByIdBuilding(@PathVariable int id, @PathVariable String op, Model model){
       Project project =  projectService.findById(id);
        model.addAttribute("projectCategoryList", projectCategoryService.findAll());

       model.addAttribute("project", project);
         return "/project/project" + op;
    }



    @PostMapping("/project/projects")
    public String addNewFurniture(Project project, BindingResult result, Model model){

        if (project.getIfmisNumber() != null && projectService.isIfmisNumberExists(project.getIfmisNumber())) {
            result.rejectValue("ifmisNumber", "error.user", "IFMIS Number already exists");
        }
//        if (project.getGrzNumber() != null && buildingService.isGrzNumberExists(building.getGrzNumber())) {
//            result.rejectValue("grzNumber", "error.user", "GRZ Number already exists");
//        }

        model.addAttribute("project", new Project());
        addModelAttributes(model);

        if (result.hasErrors()) {
            model.addAttribute("project",project);
            return "/project/projectAdd";
        }

        projectService.save(project);

        return "redirect:/project/projects";
    }

    @PostMapping("/project/projectss")
    public String addNeww(Project project, Model model){

        projectService.save(project);

        return "redirect:/project/projects";
    }


    // Method to delete Equipments
    @RequestMapping(value = "/project/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteByIdFurniture(@PathVariable int id){

         projectService.deleById(id);

         return "redirect:/project/projects";
    }

    //Export
    @GetMapping("/project/project/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "project.csv";

        List<Project> projects = projectService.findAll();
        ByteArrayInputStream inputStream = CSVHelperProject.projectToCSV(projects);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/project/project/import")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        if (excelProjectService.saveExcelData(file)) {
            model.addAttribute("message", "File uploaded successfully!");
        } else {
            model.addAttribute("message", "Failed to upload file. Please try again.");
        }
        return "redirect:/project/projects";
    }

}
