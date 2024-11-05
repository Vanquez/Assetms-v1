package com.kindsonthegenius.fleetmsv2.fleet.repositories;

import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleFuel;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleFuelRepository extends JpaRepository<VehicleFuel,Integer> {
    //Total Cost
//    @Query(value = "SELECT SUM(price) FROM vehicle_maintenance", nativeQuery = true)
//    BigDecimal findTotalCost();

      @Query(value = "select f from VehicleFuel f where " +
              "f.vehicle.name LIKE %?1% or f.vehicleNumber LIKE %?1%")
      List<VehicleFuel> findByKeyword(String keyword);

      @Query(value = "SELECT SUM(amount) FROM vehicle_fuel ", nativeQuery = true)
      BigDecimal getTotalAmount();

      //Total Cost
      @Query(value = "SELECT SUM(amount) FROM vehicle_fuel", nativeQuery = true)
      BigDecimal findTotalCost();

      // Total Cost Cars
      @Query(value = "SELECT SUM(amount) FROM vehicle_fuel WHERE vehicletypeid = '9'",
              nativeQuery = true)
      BigDecimal totalCostCar();

      // Total Cost Trucks
      @Query(value = "SELECT SUM(amount) FROM vehicle_fuel WHERE vehicletypeid = '10'",
              nativeQuery = true)
      BigDecimal totalCostTruck();

      // Total Cost Buses
      @Query(value = "SELECT SUM(amount) FROM vehicle_fuel WHERE vehicletypeid = '11'",
              nativeQuery = true)
      BigDecimal totalCostBus();

      // Total Cost PickUps
      @Query(value = "SELECT SUM(amount) FROM vehicle_fuel WHERE vehicletypeid = '12'",
              nativeQuery = true)
      BigDecimal totalCostPickUP();
}
