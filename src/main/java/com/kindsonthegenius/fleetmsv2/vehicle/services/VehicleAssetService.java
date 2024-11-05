package com.kindsonthegenius.fleetmsv2.vehicle.services;

import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.vehicle.models.VehicleAsset;
import com.kindsonthegenius.fleetmsv2.vehicle.repositories.VehicleAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleAssetService {

    @Autowired
    private VehicleAssetRepository vehicleAssetRepository;


    //Method to save vehicle asset
    public void save(VehicleAsset vehicleAsset){

        vehicleAssetRepository.save(vehicleAsset);

    }


    //find all vehicle assets
    public List<VehicleAsset> findAll(){

        return vehicleAssetRepository.findAll();
    }

    //find unique records methods
    public boolean isIfmisNumberExists(String ifmisNumber) {
        return vehicleAssetRepository.findByIfmisNumber(ifmisNumber) != null;
    }

    public boolean isGrzNumberExists(String grzNumber) {
        return vehicleAssetRepository.findByGrzNumber(grzNumber) != null;
    }

    public boolean isVehicleNumberExists(String vehicleNumber) {
        return vehicleAssetRepository.findByVehicleNumber(vehicleNumber) != null;
    }

    //Get asset by Keyword
    public List<VehicleAsset> findByKeyword(String keyword) {
        return vehicleAssetRepository.findByKeyword(keyword);
    }

    //Method to find by Id
    public VehicleAsset findById(int id){

        return vehicleAssetRepository.findById(id).orElse(null);
    }

    //Delete by Id
    public void deleById(int id){

        vehicleAssetRepository.deleteById(id);
    }

}
