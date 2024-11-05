package com.kindsonthegenius.fleetmsv2.assetm.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
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
public interface AssetRepository extends JpaRepository<Asset, Integer> {

    @Query("SELECT a FROM Asset a WHERE a.createdDate <= :cutoffDate")
    List<Asset> findOldAssets(@Param("cutoffDate") Date cutoffDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Asset a WHERE a.createdDate <= :cutoffDate")
    void deleteOldAssets(@Param("cutoffDate") Date cutoffDate);


    @Query(value = "select a from Asset a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% or a.asset_name like %?1%  or a.asset_model like %?1% ")
    List<Asset> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM asset a " +
            "WHERE a.asset_id NOT IN (SELECT asset_id FROM employee_asset_final) " +
            "AND (a.ifmis_number LIKE %?1% OR a.grz_number LIKE %?1% OR a.asset_name LIKE %?1% OR a.asset_model LIKE %?1%)",
            nativeQuery = true)
    List<Asset> findNotAssignedAssetsByKeyword(String keyword);




//    @Query(
//            value = "SELECT * FROM role WHERE id NOT IN (SELECT role_id FROM user_role WHERE user_id = ?1)",
//            nativeQuery = true
//    )
//    List<Role> getUserNotRoles(Integer userId);

    @Query(
            value = "SELECT * FROM asset WHERE asset_id NOT IN (SELECT asset_id FROM employee_asset_final WHERE employee_id= ?1)",
            nativeQuery = true
    )
   List<Asset> getNotAssets(Integer employeeId);


    //Find unique records to avoid duplicates
    Asset findByIfmisNumber(String ifmisNumber);
    Asset findByGrzNumber(String grzNumber);
//    Asset findByAssetSerialNumber(String asset_serial_number);

    //Total Computers
    @Query(value = "SELECT COUNT(*) AS total_computers FROM Asset",
            nativeQuery = true)
    List<Integer> getNumberAsset();

    //Total Cost
    @Query(value = "SELECT SUM(capitalization_amount) FROM Asset", nativeQuery = true)
    BigDecimal findTotalCost();



}
