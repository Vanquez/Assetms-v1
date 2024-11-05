package com.kindsonthegenius.fleetmsv2.officeequipments.repositories;

import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface OfficeEquipmentCategoryRepository extends JpaRepository<OfficeEquipmentCategory, Integer> {
}
