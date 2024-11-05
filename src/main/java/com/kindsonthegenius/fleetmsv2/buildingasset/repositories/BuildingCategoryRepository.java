package com.kindsonthegenius.fleetmsv2.buildingasset.repositories;

import com.kindsonthegenius.fleetmsv2.buildingasset.models.BuildingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingCategoryRepository extends JpaRepository<BuildingCategory, Integer> {
}
