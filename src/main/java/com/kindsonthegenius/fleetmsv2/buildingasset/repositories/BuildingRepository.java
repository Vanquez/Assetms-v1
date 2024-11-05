package com.kindsonthegenius.fleetmsv2.buildingasset.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.buildingasset.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Integer> {

    @Query("SELECT a FROM Building a WHERE a.createdDate <= :cutoffDate")
    List<Asset> findOldAssets(@Param("cutoffDate") Date cutoffDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Asset a WHERE a.createdDate <= :cutoffDate")
    void deleteOldAssets(@Param("cutoffDate") Date cutoffDate);





    @Query(value = "select a from Building a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% or a.name like %?1%   ")
    List<Building> findByKeyword(@Param("keyword") String keyword);

    Building findByIfmisNumber(String ifmisNumber);
    Building findByGrzNumber(String grzNumber);


    //Total Buildings
    @Query(value = "SELECT COUNT(*) AS total_buildings FROM Building",
            nativeQuery = true)
    List<Integer> getNumberbuilding();

    //Total Cost
    @Query(value = "SELECT SUM(capitalization_amount) FROM building", nativeQuery = true)
    BigDecimal findTotalCost();


}
