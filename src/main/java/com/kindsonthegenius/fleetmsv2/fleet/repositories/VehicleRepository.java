package com.kindsonthegenius.fleetmsv2.fleet.repositories;

import com.kindsonthegenius.fleetmsv2.fleet.models.Vehicle;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.vehicle.models.VehicleAsset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {


    @Query(value = "select v from Vehicle v where " +
            "v.name LIKE %?1% or v.vehicleNumber LIKE %?1%")
    List<Vehicle> findByKeyword(String keyword);


    @Query(value = "SELECT COUNT(*) as total_cars FROM vehicle WHERE vehicletypeid = '2'",
    nativeQuery = true)
    List<Integer> vehicleCar();

    @Query(value = "SELECT COUNT(*) as total_trucks FROM vehicle WHERE vehicletypeid = '3'",
    nativeQuery = true)
    List<Integer> vehicleTruck();

   @Query(value = "SELECT COUNT(*) as total_buses FROM vehicle WHERE vehicletypeid = '4'",
   nativeQuery = true)
   List<Integer> vehicleBus();

   @Query(value = "SELECT COUNT(*) as total_pickups FROM vehicle WHERE vehicletypeid = '5'",
   nativeQuery = true)
    List<Integer> vehiclePickup();

    @Query(value = "SELECT COUNT(*) as total_pickups FROM vehicle WHERE vehicletypeid = '6'",
            nativeQuery = true)
    List<Integer> motorBike();



    //Finding existing
    Vehicle findByVehicleNumber(String vehicleNumber);



}
