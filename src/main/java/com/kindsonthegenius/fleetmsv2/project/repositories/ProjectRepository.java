package com.kindsonthegenius.fleetmsv2.project.repositories;

import com.kindsonthegenius.fleetmsv2.project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    @Query(value = "select a from Project a where " +
            "a.ifmisNumber LIKE %?1% or  a.name like %?1%   ")
    List<Project> findByKeyword(@Param("keyword") String keyword);

    Project findByIfmisNumber(String ifmisNumber);
//    Project findByGrzNumber(String grzNumber);

    //Total Furniture
    @Query(value = "SELECT COUNT(*) AS total_project FROM Project",
            nativeQuery = true)
    List<Integer> getNumberProject();



    //Total Cost
    @Query(value = "SELECT SUM( net_book_value) FROM Project", nativeQuery = true)
    BigDecimal findTotalCost();


}
