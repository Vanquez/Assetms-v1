package com.kindsonthegenius.fleetmsv2.assetm.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SourceRepository extends JpaRepository<Asset, Integer> {

//    @Query(
//            value = "SELECT * FROM asset WHERE create_date = date",
//            nativeQuery = true
//    )
//    List<Asset> findByDateInsertedBefore(Date date);

    @Query(
            value = "SELECT * FROM asset WHERE create_date = fiveYearsAgo",
            nativeQuery = true
    )
    List<Asset> findByCreatedDateBefore(Date fiveYearsAgo);
}