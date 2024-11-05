package com.kindsonthegenius.fleetmsv2.furnitureasset.services;

import com.kindsonthegenius.fleetmsv2.furnitureasset.models.FurnitureCategory;
import com.kindsonthegenius.fleetmsv2.furnitureasset.repositories.FurnitureCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FurnitureCategoryService {

    @Autowired
    private FurnitureCategoryRepository furnitureCategoryRepository;


    //Method to save equipments
    public void save(FurnitureCategory furnitureCategory){

        furnitureCategoryRepository.save(furnitureCategory);

    }

    //Method to retrieve saved equipments

    public List<FurnitureCategory> findAll(){

        return furnitureCategoryRepository.findAll();
    }

    //Method to find by Id
    public Optional<FurnitureCategory> findById(int id) {
        return furnitureCategoryRepository.findById(id);
    }

    public void deleById(int id){

        furnitureCategoryRepository.deleteById(id);
    }


}
