package com.kindsonthegenius.fleetmsv2.furnitureasset.repositories;

import com.kindsonthegenius.fleetmsv2.furnitureasset.models.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture,Integer> {

    @Query(value = "select a from Furniture a where " +
            "a.ifmisNumber LIKE %?1% or a.grzNumber like %?1% or a.name like %?1%   ")
    List<Furniture> findByKeyword(@Param("keyword") String keyword);

    Furniture findByIfmisNumber(String ifmisNumber);
    Furniture findByGrzNumber(String grzNumber);

    //Total Furniture
    @Query(value = "SELECT COUNT(*) AS total_furniture FROM Furniture",
            nativeQuery = true)
    List<Integer> getNumberFurniture();

    //Total Cost
    @Query(value = "SELECT SUM(capitalization_amount) FROM Furniture", nativeQuery = true)
    BigDecimal findTotalCost();


}
