package com.kindsonthegenius.fleetmsv2.officeequipments.services;

import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleType;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipmentCategory;
import com.kindsonthegenius.fleetmsv2.officeequipments.repositories.OfficeEquipmentCategoryRepository;
import com.kindsonthegenius.fleetmsv2.officeequipments.repositories.OfficeEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeEquipmentCategoryService {

    @Autowired
    private OfficeEquipmentCategoryRepository officeEquipmentCategoryRepository;


    //Method to save equipments
    public void save(OfficeEquipmentCategory officeEquipmentCategory){

        officeEquipmentCategoryRepository.save(officeEquipmentCategory);

    }

    //Method to retrieve saved equipments

    public List<OfficeEquipmentCategory> findAll(){

        return officeEquipmentCategoryRepository.findAll();
    }

    //Method to find by Id
    public Optional<OfficeEquipmentCategory> findById(int id) {
        return officeEquipmentCategoryRepository.findById(id);
    }

    public void deleById(int id){

        officeEquipmentCategoryRepository.deleteById(id);
    }
}
