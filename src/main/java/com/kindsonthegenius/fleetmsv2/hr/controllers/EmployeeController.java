package com.kindsonthegenius.fleetmsv2.hr.controllers;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.hr.services.EmployeeService;
import com.kindsonthegenius.fleetmsv2.hr.services.EmployeeTypeService;
import com.kindsonthegenius.fleetmsv2.hr.services.JobTitleService;
import com.kindsonthegenius.fleetmsv2.parameters.services.CountryService;
import com.kindsonthegenius.fleetmsv2.parameters.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
	
	@Autowired private EmployeeService employeeService;
	@Autowired private StateService stateService;
	@Autowired private JobTitleService jobTitleService;
	@Autowired private EmployeeTypeService employeeTypeService;
	@Autowired private CountryService countryService;

	public Model addModelAttributes(Model model){
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("states", stateService.findAll());
//		model.addAttribute("employees", employeeService.findAll());
		model.addAttribute("jobTitles", jobTitleService.findAll());
		model.addAttribute("employeeTypes", employeeTypeService.findAll());
		return model;
	}

	//Get All Employees
	@GetMapping("/hr/employees")
	public String findAll(Model model, String keyword){
		addModelAttributes(model);
		List<Employee> employees;
		try {
			if (keyword == null){
				employees = employeeService.findAll();
			} else {
				employees = employeeService.findByKeyword(keyword);
			}
			model.addAttribute("employees",employees);
			System.out.println(employees);
		} catch (Exception e){
			// Handle the exception, for example by logging it and setting an empty list or error message
			System.err.println("An error occurred while fetching employees:" + e.getMessage());
			employees = new ArrayList<>(); // or handle
			model.addAttribute("employees", employees);
			model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
		}

		return "/hr/employees";
	}	

	@GetMapping("/hr/employeeAdd")
	public String addEmployee(Model model){

		model.addAttribute("employee", new Employee());
		addModelAttributes(model);
		return "/hr/employeeAdd";
	}

	//The op parameter is either Edit or Details
	@GetMapping("/hr/employee/{op}/{id}")
	public String editEmployee(@PathVariable Integer id, @PathVariable String op, Model model){
		Employee employee = employeeService.findById(id);
		model.addAttribute("employee", employee);
		addModelAttributes(model);
		return "/hr/employee"+ op; //returns employeeEdit or employeeDetails
	}

	//Add Employee
	@PostMapping("/hr/employees")
	public String addNew(Employee employee, BindingResult result, Model model) {

		if (employee.getSocialSecurityNumber() != null && employeeService.isSocialSecurityNumber(employee.getSocialSecurityNumber())) {
			result.rejectValue("socialSecurityNumber", "error.user", "NRC Number already exists");
		}
//
		model.addAttribute("employee", new Employee());
		addModelAttributes(model);

		if (result.hasErrors()) {
			model.addAttribute("employee",employee);
			addModelAttributes(model);
			return "/hr/employeeAdd";
		}
		employeeService.save(employee);
		return "redirect:/hr/employees";
	}
	//Add Employee
	@PostMapping("/hr/employeess")
	public String addNew(Employee employee, Model model) {

		employeeService.save(employee);
		return "redirect:/hr/employees";
	}

	@RequestMapping(value="/hr/employee/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete(@PathVariable Integer id) {
		employeeService.delete(id);
		return "redirect:/hr/employees";
	}	

	@RequestMapping(value="/employees/uploadPhoto", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		File newFile = new File("D:\\SOLUTIONS\\fleetms\\uploads" + file.getOriginalFilename());
		newFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(newFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
	}	

	@PostMapping("/employees/uploadPhoto2")
	public String uploadFile2(@RequestParam("file") MultipartFile file, Principal principal) 
			throws IllegalStateException, IOException {
			String baseDirectory = "D:\\SOLUTIONS\\fleetms\\src\\main\\resources\\static\\img\\photos\\" ;
			file.transferTo(new File(baseDirectory + principal.getName() + ".jpg"));
			return "redirect:/employees";
	}

	@RequestMapping(value="/employee/profile")
	public String profile(Model model, Principal principal) {
		String un = principal.getName();
		addModelAttributes(model);
		model.addAttribute("employee", employeeService.findByUsername(un));
		return "profile";
	}

//	@GetMapping("/hr/assignemployee/{op}/{id}")
//	public String editAssign(@PathVariable int id, @PathVariable String op, Model model){
//		Employee employees = employeeService.findById(id);
//		model.addAttribute("employee", employees);
//		model.addAttribute("assigned", employeeService.getAssign());
//		model.addAttribute("assigned", employeeService.getNotAssigned());
//
//		return "";
//	}
}
