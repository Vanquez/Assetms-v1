package com.kindsonthegenius.fleetmsv2.fleet.services;

import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import com.kindsonthegenius.fleetmsv2.fleet.repositories.VehicleMaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VehicleMaintenanceService {

	@Autowired
	private VehicleMaintenanceRepository vehicleMaintenanceRepository;
	
	//Get All VehicleMaintenances
	public List<VehicleMaintenance> findAll(){
		return vehicleMaintenanceRepository.findAll();
	}	
	
	//Get VehicleMaintenance By Id
	public VehicleMaintenance findById(int id) {
		return vehicleMaintenanceRepository.findById(id).orElse(null);
	}	
	
	//Delete VehicleMaintenance
	public void delete(int id) {
		vehicleMaintenanceRepository.deleteById(id);
	}
	
	//Update VehicleMaintenance
	public void save(VehicleMaintenance vehicleMaintenance) {
		vehicleMaintenanceRepository.save(vehicleMaintenance);
	}

	//Total Cost
//	public BigDecimal findTotalCost(){
//		return vehicleMaintenanceRepository.findTotalCost();
//	}

	public BigDecimal findTotalCost() {
		BigDecimal totalCost = BigDecimal.ZERO;
		if (vehicleMaintenanceRepository != null) {
			BigDecimal costCar = vehicleMaintenanceRepository.findTotalCost();
			if (costCar != null) {
				totalCost = costCar;
			}
		}
		return totalCost;
	}

	// Total Cost Car
	public BigDecimal findTotalCostCar() {
		BigDecimal totalCost = BigDecimal.ZERO;
		if (vehicleMaintenanceRepository != null) {
			BigDecimal costCar = vehicleMaintenanceRepository.totalCostCar();
			if (costCar != null) {
				totalCost = costCar;
			}
		}
		return totalCost;
	}

	// Total Cost Truck
	public BigDecimal findTotalCostTruck() {
		BigDecimal totalCost = BigDecimal.ZERO;
		if (vehicleMaintenanceRepository != null) {
			BigDecimal costTruck = vehicleMaintenanceRepository.totalCostTruck();
			if (costTruck != null) {
				totalCost = costTruck;
			}
		}
		return totalCost;
	}

	// Total Cost Bus
	public BigDecimal findTotalCostBus() {
		BigDecimal totalCost = BigDecimal.ZERO;
		if (vehicleMaintenanceRepository != null) {
			BigDecimal costBus = vehicleMaintenanceRepository.totalCostBus();
			if (costBus != null) {
				totalCost = costBus;
			}
		}
		return totalCost;
	}

	// Total Cost PickUp
	public BigDecimal findTotalCostPickUp() {
		BigDecimal totalCost = BigDecimal.ZERO;
		if (vehicleMaintenanceRepository != null) {
			BigDecimal costPickUp = vehicleMaintenanceRepository.totalCostPickUP();
			if (costPickUp != null) {
				totalCost = costPickUp;
			}
		}
		return totalCost;
	}

	// Find vehicle by keyword
	public List<VehicleMaintenance> findByKeyword(String keyword) {

		return vehicleMaintenanceRepository.findByKeyword(keyword);
	}

}
