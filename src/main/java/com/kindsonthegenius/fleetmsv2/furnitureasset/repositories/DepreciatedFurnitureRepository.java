package com.kindsonthegenius.fleetmsv2.furnitureasset.repositories;

import com.kindsonthegenius.fleetmsv2.furnitureasset.models.DepreciatedFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepreciatedFurnitureRepository extends JpaRepository<DepreciatedFurniture,Long> {
}
