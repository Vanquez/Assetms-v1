package com.kindsonthegenius.fleetmsv2.fleet.services;

import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleFuel;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import com.kindsonthegenius.fleetmsv2.fleet.repositories.VehicleFuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VehicleFuelService {


    @Autowired
    private VehicleFuelRepository vehicleFuelRepository;

    //Method to return
    public List<VehicleFuel> getVehicleFuels(){

        return vehicleFuelRepository.findAll();
    }


    //Method to save
    public void saveVehicleFuel(VehicleFuel vehicleFuel){

        vehicleFuelRepository.save(vehicleFuel);
    }

    public VehicleFuel findById(Integer id) {


        return vehicleFuelRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {

        vehicleFuelRepository.deleteById(id);
    }


    //Method to return fuel cost
    public BigDecimal fuelTotalCost(){

//        return vehicleFuelRepository.getTotalAmount();
        BigDecimal totalCost = BigDecimal.ZERO;
        if (vehicleFuelRepository != null) {
            BigDecimal costCar = vehicleFuelRepository.getTotalAmount();
            if (costCar != null) {
                totalCost = costCar;
            }
        }
        return totalCost;
    }

    // Find vehicle by keyword
    public List<VehicleFuel> findByKeyword(String keyword) {
        return vehicleFuelRepository.findByKeyword(keyword);
    }

    // Total Cost Car
    public BigDecimal findTotalCostCar() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (vehicleFuelRepository != null) {
            BigDecimal costCar = vehicleFuelRepository.totalCostCar();
            if (costCar != null) {
                totalCost = costCar;
            }
        }
        return totalCost;
    }

    // Total Cost Truck
    public BigDecimal findTotalCostTruck() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (vehicleFuelRepository != null) {
            BigDecimal costTruck = vehicleFuelRepository.totalCostTruck();
            if (costTruck != null) {
                totalCost = costTruck;
            }
        }
        return totalCost;
    }

    // Total Cost Bus
    public BigDecimal findTotalCostBus() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (vehicleFuelRepository != null) {
            BigDecimal costBus = vehicleFuelRepository.totalCostBus();
            if (costBus != null) {
                totalCost = costBus;
            }
        }
        return totalCost;
    }

    // Total Cost PickUp
    public BigDecimal findTotalCostPickUp() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (vehicleFuelRepository != null) {
            BigDecimal costPickUp = vehicleFuelRepository.totalCostPickUP();
            if (costPickUp != null) {
                totalCost = costPickUp;
            }
        }
        return totalCost;
    }

}
