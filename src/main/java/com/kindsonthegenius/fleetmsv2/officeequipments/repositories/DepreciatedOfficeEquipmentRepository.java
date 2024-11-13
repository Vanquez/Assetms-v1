package com.kindsonthegenius.fleetmsv2.officeequipments.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.DepreciatedAsset;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.DepreciatedOfficeEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepreciatedOfficeEquipmentRepository extends JpaRepository<DepreciatedOfficeEquipment,Long> {

    @Query(value = "select a from DepreciatedOfficeEquipment a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% ")
    List<DepreciatedOfficeEquipment> findByKeyword(@Param("keyword") String keyword);

    // Custom query method to check if an asset with a specific serial number already exists
    @Query("SELECT COUNT(a) > 0 FROM DepreciatedOfficeEquipment a WHERE a.serialNumber = ?1")
    boolean existsByAssetSerialNumber(String serialNumber);
}
