package com.kindsonthegenius.fleetmsv2.furnitureasset.services;

import com.kindsonthegenius.fleetmsv2.furnitureasset.models.Furniture;
import com.kindsonthegenius.fleetmsv2.furnitureasset.repositories.FurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FurnitureService {
    @Autowired
    private FurnitureRepository furnitureRepository;


    //Method to save equipments
    public void save(Furniture furniture){

        furnitureRepository.save(furniture);

    }


    //Method to retrieve saved equipments
    public List<Furniture> findAll(){

        return furnitureRepository.findAll();
    }

    //Method to find by Id
   public Furniture findById(int id){

        return furnitureRepository.findById(id).orElse(null);
   }

   public void deleById(int id){

        furnitureRepository.deleteById(id);
   }

    //find unique records methods
    public boolean isIfmisNumberExists(String ifmisNumber) {
        return furnitureRepository.findByIfmisNumber(ifmisNumber) != null;
    }

    public boolean isGrzNumberExists(String grzNumber) {
        return furnitureRepository.findByGrzNumber(grzNumber) != null;
    }



    //Get asset by Keyword
    public List<Furniture> findByKeyword(String keyword) {
        return furnitureRepository.findByKeyword(keyword);
    }

    // Get total number of FurnitureAsset
    public List<Integer> getNumberFurniture(){return  furnitureRepository.getNumberFurniture();}

    public BigDecimal findTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (furnitureRepository != null) {
            BigDecimal costCar = furnitureRepository.findTotalCost();
            if (costCar != null) {
                totalCost = costCar;
            }
        }
        return totalCost;
    }

}
