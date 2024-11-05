package com.kindsonthegenius.fleetmsv2.fleet.controllers;

import com.kindsonthegenius.fleetmsv2.fleet.models.CSVHelper;
import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleMaintenanceService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleTypeService;
import com.kindsonthegenius.fleetmsv2.parameters.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;

@Controller
public class VehicleMaintenanceController {

	@Autowired private VehicleMaintenanceService vehicleMaintenanceService;
	@Autowired private VehicleService vehicleService;
	@Autowired private SupplierService supplierService;
	@Autowired private VehicleTypeService vehicleTypeService;

	public Model addModelAttributes(Model model){
		model.addAttribute("vehicles", vehicleService.findAll());
		model.addAttribute("suppliers", supplierService.findAll());
		model.addAttribute("vehicleTypes", vehicleTypeService.findAll());
		return model;
	}


	//Get All Maintenances
	@GetMapping("/fleet/maintenances")
	public String findAll(Model model, String keyword) {
		addModelAttributes(model);

		List<VehicleMaintenance> maintenances;

		try {

			if (keyword == null) {
				maintenances = vehicleMaintenanceService.findAll();
			} else {
				maintenances = vehicleMaintenanceService.findAll();
			}
			model.addAttribute("maintenances", maintenances);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/fleet/maintenances";

	}

	@GetMapping("/fleet/maintenanceAdd")
	public String addMaintenance(Model model){
		addModelAttributes(model);
		return "/fleet/maintenanceAdd";
	}

	@GetMapping("/fleet/maintenance/{op}/{id}")
	public String editMaintenance(Model model, @PathVariable Integer id, @PathVariable String op){
		VehicleMaintenance maintenance = vehicleMaintenanceService.findById(id);
		model.addAttribute("maintenance", maintenance);
		addModelAttributes(model);
		return "/fleet/maintenance"+op;
	}

	//Add VehicleMaintenance
	@PostMapping("/fleet/maintenances")
	public String addNew(VehicleMaintenance vehicleMaintenance) {
		vehicleMaintenanceService.save(vehicleMaintenance);
		return "redirect:/fleet/maintenances";
	}
	
	@RequestMapping(value="fleet/maintenance/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete(@PathVariable Integer id) {
		vehicleMaintenanceService.delete(id);
		return "redirect:/fleet/maintenances";
	}

	@GetMapping("/fleet/maintenance/export")
	public ResponseEntity<Resource> exportToCsv() throws IOException {
		String filename = "vehicle_maintenance.csv";

		List<VehicleMaintenance> maintenances = vehicleMaintenanceService.findAll();
		ByteArrayInputStream inputStream = CSVHelper.maintenancesToCSV(maintenances);

		InputStreamResource file = new InputStreamResource(inputStream);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
				.body(file);
	}

}
