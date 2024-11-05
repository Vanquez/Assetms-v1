package com.kindsonthegenius.fleetmsv2.assetm.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.models.DepreciatedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepreciatedAssetRepository extends JpaRepository<DepreciatedAsset, Long> {

    @Query(value = "select a from DepreciatedAsset a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% or a.asset_name like %?1%  or a.asset_model like %?1% ")
    List<DepreciatedAsset> findByKeyword(@Param("keyword") String keyword);
}