package com.kindsonthegenius.fleetmsv2.buildingasset.services;

import com.kindsonthegenius.fleetmsv2.buildingasset.models.Building;
import com.kindsonthegenius.fleetmsv2.buildingasset.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;


    //Method to save equipments
    public void save(Building furniture){

        buildingRepository.save(furniture);

    }


    //Method to retrieve saved equipments
    public List<Building> findAll(){

        return buildingRepository.findAll();
    }

    //Method to find by Id
   public Building findById(int id){

        return buildingRepository.findById(id).orElse(null);
   }

   public void deleById(int id){

        buildingRepository.deleteById(id);
   }

    //find unique records methods
    public boolean isIfmisNumberExists(String ifmisNumber) {
        return buildingRepository.findByIfmisNumber(ifmisNumber) != null;
    }

    public boolean isGrzNumberExists(String grzNumber) {
        return buildingRepository.findByGrzNumber(grzNumber) != null;
    }



    //Get asset by Keyword
    public List<Building> findByKeyword(String keyword) {
        return buildingRepository.findByKeyword(keyword);
    }

    // Get total number of Buildings
    public List<Integer> getNumberBuilding(){return  buildingRepository.getNumberbuilding();}

    public BigDecimal findTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (buildingRepository != null) {
            BigDecimal costCar = buildingRepository.findTotalCost();
            if (costCar != null) {
                totalCost = costCar;
            }
        }
        return totalCost;
    }

}
