package com.kindsonthegenius.fleetmsv2.security.controllers;

import com.kindsonthegenius.fleetmsv2.assetm.services.AssetService;
import com.kindsonthegenius.fleetmsv2.buildingasset.repositories.BuildingRepository;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.BuildingService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleFuelService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleMaintenanceService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleService;
import com.kindsonthegenius.fleetmsv2.furnitureasset.repositories.FurnitureRepository;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.FurnitureService;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.hr.services.EmployeeService;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.OfficeEquipmentService;
import com.kindsonthegenius.fleetmsv2.project.services.ProjectService;
import com.kindsonthegenius.fleetmsv2.security.models.Role;
import com.kindsonthegenius.fleetmsv2.security.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SecurityController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VehicleMaintenanceService vehicleMaintenanceService;
    @Autowired
    private VehicleFuelService vehicleFuelService;
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private OfficeEquipmentService officeEquipmentService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private ProjectService projectService;

    public  Model addModelAttributes(Model model){
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("countEmployee", employeeService.getNumberEmployee());
        model.addAttribute("totalMaintenanceCost", vehicleMaintenanceService.findTotalCost());
        model.addAttribute("totalFuelCost", vehicleFuelService.fuelTotalCost());
        model.addAttribute("totalCars", vehicleService.totalCars());
        model.addAttribute("totalTrucks", vehicleService.totalTrucks());
        model.addAttribute("totalBuses", vehicleService.totalBuses());
        model.addAttribute("totalPickUps", vehicleService.totalPickUps());
        model.addAttribute("totalMotorBikes", vehicleService.motorBikes());

        //For Assets Total Numbers
        model.addAttribute("countAsset", assetService.getNumberAsset());
        model.addAttribute("countEquipment", officeEquipmentService.getNumberEquipment());
        model.addAttribute("countBuilding", buildingService.getNumberBuilding());
        model.addAttribute("countFurniture", furnitureService.getNumberFurniture());
        model.addAttribute("countProject", projectService.getNumberProject());

        //Find Total Asset Cost
        model.addAttribute("computerCost", assetService.findTotalCost());
        model.addAttribute("equipmentCost", officeEquipmentService.findTotalCost());
        model.addAttribute("buildingCost", buildingService.findTotalCost());
        model.addAttribute("furnitureCost", furnitureService.findTotalCost());
        model.addAttribute("projectCost", projectService.findTotalCost());


        return model;
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "security/login";
    }

    @GetMapping("/register")
    public String register() {
        return "security/register";
    }

    @RequestMapping("/index")
    public String homePage(Model model) {

        BigDecimal total = vehicleMaintenanceService.findTotalCost();

          addModelAttributes(model);
          System.out.println(total);
        return "index";
    }

    @RequestMapping("/index2")
    public String homePage2(Model model) {

        BigDecimal total = vehicleMaintenanceService.findTotalCost();

        addModelAttributes(model);
        System.out.println(total);
        return "index2";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
