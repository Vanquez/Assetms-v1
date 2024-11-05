package com.kindsonthegenius.fleetmsv2.fleet.controllers;

import com.kindsonthegenius.fleetmsv2.fleet.models.*;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleFuelService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleService;
import com.kindsonthegenius.fleetmsv2.fleet.services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class FuelController {

    @Autowired
    private VehicleFuelService vehicleFuelService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleTypeService vehicleTypeService;

  public   Model addModelAttributes(Model model){

//        model.addAttribute("fuel",vehicleFuelService.getVehicleFuels());
        model.addAttribute("vehicles", vehicleService.findAll());
        model.addAttribute("vehicleTypes", vehicleTypeService.findAll());

        return model;
    }

    //controller to return fuel
    @GetMapping("/fleet/fuel")
    public String findAll(Model model, String keyword) {
        addModelAttributes(model);

        List<VehicleFuel> fuels;

        try {

            if (keyword == null) {
                fuels = vehicleFuelService.getVehicleFuels();
            } else {
                fuels = vehicleFuelService.findByKeyword(keyword);
            }
            model.addAttribute("fuel", fuels);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "/fleet/fuel";

    }

    //Controller to return add fuel
    @GetMapping("/fleet/fuelAdd")
    public String fuelAdd(Model model){

       addModelAttributes(model);
      return "/fleet/fuelAdd";
    }

    //Controller to save into database
    @PostMapping("/fleet/fuels")
    public String fuelsAdd(VehicleFuel vehicleFuel){

      vehicleFuelService.saveVehicleFuel(vehicleFuel);

      return "redirect:/fleet/fuel";
    }

    @GetMapping("/fleet/fuel/{op}/{id}")
    public String editMaintenance(Model model, @PathVariable Integer id, @PathVariable String op){
        VehicleFuel fuel = vehicleFuelService.findById(id);
        model.addAttribute("fuelid", fuel);
        addModelAttributes(model);
        return "/fleet/fuel"+op;
    }


    @RequestMapping(value="fleet/fuel/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Integer id) {
        vehicleFuelService.delete(id);
        return "redirect:/fleet/fuel";
    }

    @GetMapping("/fleet/fuel/export")
    public ResponseEntity<Resource> exportToCsv() throws IOException {
        String filename = "vehicle_fuel.csv";

        List<VehicleFuel> fuels = vehicleFuelService.getVehicleFuels();
        ByteArrayInputStream inputStream = CSVHelperFuel.fuelToCSV(fuels);

        InputStreamResource file = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType("application/csv"))
                .body(file);
    }


}
