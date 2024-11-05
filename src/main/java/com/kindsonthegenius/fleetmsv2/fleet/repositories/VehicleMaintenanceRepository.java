package com.kindsonthegenius.fleetmsv2.fleet.repositories;

import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenance, Integer> {

    @Query(value = "select m from VehicleMaintenance m where " +
            "m.vehicle.name LIKE %?1% or m.company LIKE %?1% or m.vehicleNumber LIKE %?1%")
    List<VehicleMaintenance> findByKeyword(String keyword);

    //Total Cost
    @Query(value = "SELECT SUM(price) FROM vehicle_maintenance", nativeQuery = true)
    BigDecimal findTotalCost();

    // Total Cost Cars
    @Query(value = "SELECT SUM(price) FROM vehicle_maintenance WHERE vehicletypid = '9'",
            nativeQuery = true)
    BigDecimal totalCostCar();

    // Total Cost Trucks
    @Query(value = "SELECT SUM(price) FROM vehicle_maintenance WHERE vehicletypid = '10'",
            nativeQuery = true)
    BigDecimal totalCostTruck();

    // Total Cost Buses
    @Query(value = "SELECT SUM(price) FROM vehicle_maintenance WHERE vehicletypid = '11'",
            nativeQuery = true)
    BigDecimal totalCostBus();

    // Total Cost PickUps
    @Query(value = "SELECT SUM(price) FROM vehicle_maintenance WHERE vehicletypid = '12'",
            nativeQuery = true)
    BigDecimal totalCostPickUP();


}
