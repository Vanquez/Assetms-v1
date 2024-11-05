package com.kindsonthegenius.fleetmsv2.fleet.services;

import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.fleet.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	//Get All Vehicles
	public List<Vehicle> findAll(){
		return vehicleRepository.findAll();
	}	
	
	//Get Vehicle By Id
	public Vehicle findById(int id) {
		return vehicleRepository.findById(id).orElse(null);
	}	
	
	//Delete Vehicle
	public void delete(int id) {
		vehicleRepository.deleteById(id);
	}
	
	//Update Vehicle
	public void save(Vehicle vehicle) {
		vehicleRepository.save(vehicle);
	}

	// Find total cars
	public List<Integer> totalCars(){

		return vehicleRepository.vehicleCar();
	}

	//Find total trucks
	public List<Integer> totalTrucks(){

		return vehicleRepository.vehicleTruck();
	}

	//Find total buses
	public List<Integer> totalBuses(){

		return vehicleRepository.vehicleTruck();
	}

	//Find total pickups
	public List<Integer> totalPickUps(){

		return vehicleRepository.vehiclePickup();
	}

	//Find total motorBikes
	public List<Integer> motorBikes(){

		return vehicleRepository.motorBike();
	}

	// Find vehicle by keyword
	public List<Vehicle> findByKeyword(String keyword){

		return vehicleRepository.findByKeyword(keyword);
	}

	public boolean isVehicleNumberExists(String vehicleNumber) {
		return vehicleRepository.findByVehicleNumber(vehicleNumber) != null;
	}
}
