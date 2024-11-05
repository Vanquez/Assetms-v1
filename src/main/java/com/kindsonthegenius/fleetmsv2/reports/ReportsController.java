package com.kindsonthegenius.fleetmsv2.reports;

import com.kindsonthegenius.fleetmsv2.accounts.repositories.TransactionRepository;
import com.kindsonthegenius.fleetmsv2.assetm.services.AssetService;
import com.kindsonthegenius.fleetmsv2.buildingasset.services.BuildingService;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import com.kindsonthegenius.fleetmsv2.fleet.repositories.VehicleMaintenanceRepository;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleFuelService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleMaintenanceService;
import com.kindsonthegenius.fleetmsv2.furnitureasset.models.Furniture;
import com.kindsonthegenius.fleetmsv2.furnitureasset.services.FurnitureService;
import com.kindsonthegenius.fleetmsv2.hr.repositories.EmployeeRepository;
import com.kindsonthegenius.fleetmsv2.officeequipments.services.OfficeEquipmentService;
import com.kindsonthegenius.fleetmsv2.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class ReportsController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VehicleMaintenanceRepository vehicleMaintenanceRepository;
    @Autowired
    private VehicleMaintenanceService vehicleMaintenanceService;

    @Autowired
    private VehicleFuelService vehicleFuelService;

    //Asset dependencies
    @Autowired
    private AssetService assetService;

    @Autowired
    private OfficeEquipmentService officeEquipmentService;

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ProjectService projectService;



    @GetMapping("/reports/vehicles")
    public String vehicles(Model model) {
        //Bar chart
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("Car", vehicleMaintenanceService.findTotalCostCar().intValue());
        data.put("Bus", vehicleMaintenanceService.findTotalCostBus().intValue());
        data.put("Truck", vehicleMaintenanceService.findTotalCostTruck().intValue());
        data.put("Pick-Up", vehicleMaintenanceService.findTotalCostPickUp().intValue());
        data.put("Total", vehicleMaintenanceService.findTotalCost().intValue());

        //Bar chart fuel
        Map<String, Integer> dataFuel = new LinkedHashMap<String, Integer>();
        dataFuel.put("CarFuel", vehicleFuelService.findTotalCostCar().intValue());
        dataFuel.put("BusFuel", vehicleFuelService.findTotalCostBus().intValue());
        dataFuel.put("TruckFuel", vehicleFuelService.findTotalCostTruck().intValue());
        dataFuel.put("Pick-UpFuel", vehicleFuelService.findTotalCostPickUp().intValue());
        dataFuel.put("TotalFuel", vehicleFuelService.fuelTotalCost().intValue());



        //Bar Maintenance
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());

        //Bar Fuel
        model.addAttribute("keySetFuel", dataFuel.keySet());
        model.addAttribute("valuesFuel", dataFuel.values());

        //Pie Chart
        model.addAttribute("pass", vehicleMaintenanceService.findAll().size());
        model.addAttribute("fail", vehicleFuelService.getVehicleFuels().size());

        return "/reports/vehicles";
    }

    @GetMapping("/reports/assets")
    public String assets(Model model) {
        //Bar chart
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("Computer", assetService.findTotalCost().intValue());
        data.put("Equipment", officeEquipmentService.findTotalCost().intValue());
        data.put("Furniture", furnitureService.findTotalCost().intValue());
        data.put("Building", buildingService.findTotalCost().intValue());
        data.put("Project", projectService.findTotalCost().intValue());

        //Bar chart fuel
//        Map<String, Integer> dataFuel = new LinkedHashMap<String, Integer>();
//        dataFuel.put("CarFuel", vehicleFuelService.findTotalCostCar().intValue());
//        dataFuel.put("BusFuel", vehicleFuelService.findTotalCostBus().intValue());
//        dataFuel.put("TruckFuel", vehicleFuelService.findTotalCostTruck().intValue());
//        dataFuel.put("Pick-UpFuel", vehicleFuelService.findTotalCostPickUp().intValue());
//        dataFuel.put("TotalFuel", vehicleFuelService.fuelTotalCost().intValue());



        //Bar Maintenance
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());

        //Bar Fuel
//        model.addAttribute("keySetFuel", dataFuel.keySet());
//        model.addAttribute("valuesFuel", dataFuel.values());

        //Pie Chart
        model.addAttribute("computer", assetService.findAll().size());
        model.addAttribute("equipment", officeEquipmentService.findAll().size());
        model.addAttribute("furniture", furnitureService.findAll().size());
        model.addAttribute("building", buildingService.findAll().size());

        return "/reports/assetreports";
    }




    @GetMapping("/reports/accounts")
    public String accounts(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        model.addAttribute("employeeCount", employeeRepository.getCountByCountry());
        return "/reports/under";
    }

    @GetMapping("/reports/hires")
    public String hires(){
        return "/reports/under";
    }

    @GetMapping("/reports/helpdesk")
    public String helpdesk(){
        return "/reports/under";
    }

    @GetMapping("/reports/maintenance")
    public String maintenance(){
        return "/reports/under";
    }

    @GetMapping("reports/hr")
    public String hr(){
        return "/reports/under";
    }
}
