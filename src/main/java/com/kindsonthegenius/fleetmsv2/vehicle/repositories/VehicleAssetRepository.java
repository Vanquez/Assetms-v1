package com.kindsonthegenius.fleetmsv2.vehicle.repositories;

import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.vehicle.models.VehicleAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleAssetRepository extends JpaRepository<VehicleAsset, Integer> {

    @Query(value = "select a from VehicleAsset a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% or a.name like %?1%  or a.vehicleNumber like %?1% ")
    List<VehicleAsset> findByKeyword(@Param("keyword") String keyword);

    VehicleAsset findByIfmisNumber(String ifmisNumber);
    VehicleAsset findByGrzNumber(String grzNumber);
    VehicleAsset findByVehicleNumber(String vehicleNumber);

}
