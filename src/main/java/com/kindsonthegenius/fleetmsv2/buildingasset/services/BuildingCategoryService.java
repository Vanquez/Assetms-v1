package com.kindsonthegenius.fleetmsv2.buildingasset.services;

import com.kindsonthegenius.fleetmsv2.buildingasset.models.BuildingCategory;
import com.kindsonthegenius.fleetmsv2.buildingasset.repositories.BuildingCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingCategoryService {

    @Autowired
    private BuildingCategoryRepository buildingCategoryRepository;


    //Method to save equipments
    public void save(BuildingCategory buildingCategory){

        buildingCategoryRepository.save(buildingCategory);

    }

    //Method to retrieve saved equipments

    public List<BuildingCategory> findAll(){

        return buildingCategoryRepository.findAll();
    }

    //Method to find by Id
    public Optional<BuildingCategory> findById(int id) {
        return buildingCategoryRepository.findById(id);
    }

    public void deleById(int id){

        buildingCategoryRepository.deleteById(id);
    }


}
