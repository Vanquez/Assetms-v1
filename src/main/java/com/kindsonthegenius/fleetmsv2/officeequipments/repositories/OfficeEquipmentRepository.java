package com.kindsonthegenius.fleetmsv2.officeequipments.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OfficeEquipmentRepository extends JpaRepository<OfficeEquipment,Integer> {

    @Query(value = "select a from OfficeEquipment a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% or a.name like %?1%  or a.serialNumber like %?1% ")
    List<OfficeEquipment> findByKeyword(@Param("keyword") String keyword);


    OfficeEquipment findByIfmisNumber(String ifmisNumber);
    OfficeEquipment findByGrzNumber(String grzNumber);
    OfficeEquipment findBySerialNumber(String serialNumber);

    //Total Equipments
    @Query(value = "SELECT COUNT(*) AS total_officeEquipment FROM office_equipment",
            nativeQuery = true)
    List<Integer> getNumberEquipment();

    //Total Cost
    @Query(value = "SELECT SUM(capitalization_amount) FROM office_equipment", nativeQuery = true)
    BigDecimal findTotalCost();

}