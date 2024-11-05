package com.kindsonthegenius.fleetmsv2.buildingasset.repositories;

import com.kindsonthegenius.fleetmsv2.buildingasset.models.DepreciatedBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepreciatedBuildingRepository extends JpaRepository<DepreciatedBuilding,Long> {
}
