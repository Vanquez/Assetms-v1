package com.kindsonthegenius.fleetmsv2.procurement.repositories;

import com.kindsonthegenius.fleetmsv2.procurement.models.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement,Integer> {

    @Query(value = "select a from Procurement a where " +
            "a.purchaseOrderNumber LIKE %?1% or  a.name like %?1%   ")
    List<Procurement> findByKeyword(@Param("keyword") Integer keyword);

    Procurement findByPurchaseOrderNumber(Integer purchaseOrderNumber);
//    Project findByGrzNumber(String grzNumber);

    //Total Furniture
    @Query(value = "SELECT COUNT(*) AS total_project FROM Project",
            nativeQuery = true)
    List<Integer> getNumberProject();



    //Total Cost
    @Query(value = "SELECT SUM( net_book_value) FROM Project", nativeQuery = true)
    BigDecimal findTotalCost();


}
