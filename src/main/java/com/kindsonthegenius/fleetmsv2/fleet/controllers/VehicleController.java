package com.kindsonthegenius.fleetmsv2.fleet.controllers;

import com.kindsonthegenius.fleetmsv2.fleet.models.CSVHelper;
import com.kindsonthegenius.fleetmsv2.fleet.models.CSVHelperVehicles;
import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import com.kindsonthegenius.fleetmsv2.fleet.services.*;
import com.kindsonthegenius.fleetmsv2.hr.services.EmployeeService;
import com.kindsonthegenius.fleetmsv2.parameters.services.LocationService;
import com.kindsonthegenius.fleetmsv2.vehicle.models.VehicleAsset;
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
import java.util.List;

@Controller
public class VehicleController {
	
	@Autowired private VehicleService vehicleService;
	@Autowired private VehicleTypeService vehicleTypeService;
	@Autowired private VehicleMakeService vehicleMakeService;
	@Autowired private VehicleModelService vehicleModelService;
	@Autowired private LocationService locationService;
	@Autowired private EmployeeService employeeService ;
	@Autowired private VehicleStatusService vehicleStatusService;
	@Autowired private ExcelService excelService;

	public Model addModelAttributes(Model model){
//		model.addAttribute("vehicles", vehicleService.findAll());
		model.addAttribute("vehicleTypes", vehicleTypeService.findAll());
		model.addAttribute("vehicleModels", vehicleModelService.findAll());
		model.addAttribute("vehicleMakes", vehicleMakeService.findAll());
		model.addAttribute("locations", locationService.findAll());
		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("vehicleStatuses", vehicleStatusService.findAll());
		return model;
	}

	//Get All Vehicles
	@GetMapping("/fleet/vehicles")
	public String findAll(Model model, String keyword){
		addModelAttributes(model);

		List<Vehicle> vehicles;

		try{

			if (keyword == null){
				vehicles = vehicleService.findAll();
			} else {
				vehicles=vehicleService.findByKeyword(keyword);
			}
			model.addAttribute("vehicles",vehicles);

		}catch (Exception e){
			e.printStackTrace();
		}

		return "/fleet/vehicles";
	}


	@GetMapping("/fleet/vehicleAdd")
	public String addVehicle(Model model){
		model.addAttribute("vehicle", new Vehicle());
		addModelAttributes(model);
		return "fleet/vehicleAdd";
	}

	//The op parameter is either Edit or Details
	@GetMapping("/fleet/vehicle/{op}/{id}")
	public String editVehicle(@PathVariable Integer id, @PathVariable String op, Model model){
		Vehicle vehicle = vehicleService.findById(id);
		model.addAttribute("vehicle", vehicle);
		addModelAttributes(model);
		return "/fleet/vehicle"+ op; //returns vehicleEdit or vehicleDetails
	}

	//Add Vehicle
	@PostMapping("/fleet/vehicles")
	public String addNew(Vehicle vehicle, BindingResult result, Model model) {


		if (vehicle.getVehicleNumber() != null && vehicleService.isVehicleNumberExists(vehicle.getVehicleNumber())) {
			result.rejectValue("vehicleNumber", "error.user", "Vehicle Number already exists");
		}

		model.addAttribute("vehicle", new Vehicle());
		addModelAttributes(model);

		if (result.hasErrors()) {
			model.addAttribute("vehicle", vehicle);
			return "fleet/vehicleAdd";
		}
		vehicleService.save(vehicle);
		return "redirect:/fleet/vehicles";
	}

	//Add Vehicle
	@PostMapping("/fleet/vehicless")
	public String addNeww(Vehicle vehicle, Model model) {


		vehicleService.save(vehicle);
		return "redirect:/fleet/vehicles";
	}

	@RequestMapping(value="/fleet/vehicle/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete(@PathVariable Integer id) {
		vehicleService.delete(id);
		return "redirect:/fleet/vehicles";
	}

	@GetMapping("/fleet/vehicle/export")
	public ResponseEntity<Resource> exportToCsv() throws IOException {
		String filename = "vehicle.csv";

		List<Vehicle> vehicles = vehicleService.findAll();
		ByteArrayInputStream inputStream = CSVHelperVehicles.vehicleToCSV(vehicles);

		InputStreamResource file = new InputStreamResource(inputStream);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
				.body(file);
	}

	@PostMapping("/fleet/vehicle/import")
	public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
		if (excelService.saveExcelData(file)) {
			model.addAttribute("message", "File uploaded successfully!");
		} else {
			model.addAttribute("message", "Failed to upload file. Please try again.");
		}
		return "redirect:/fleet/vehicles";
	}

	@GetMapping("/fleet/vehiclez")
	public String testVehicles(Model model){

		return "/fleet/vehiclez";
	}



}
