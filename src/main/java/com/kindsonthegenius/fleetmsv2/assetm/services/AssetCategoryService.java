package com.kindsonthegenius.fleetmsv2.assetm.services;

import com.kindsonthegenius.fleetmsv2.assetm.models.AssetCategory;
import com.kindsonthegenius.fleetmsv2.assetm.repositories.AssetCategoryRepository;
import com.kindsonthegenius.fleetmsv2.fleet.models.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetCategoryService {
    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    // Save asset Category
    public void save(AssetCategory assetCategory){
       assetCategoryRepository.save(assetCategory);
    }


    //Method to return Asset Categories
    public List<AssetCategory> findAll(){

        return assetCategoryRepository.findAll();
    }

    // Method to find by Id
    public Optional<AssetCategory> findById(int id) {
        return assetCategoryRepository.findById(id);
    }
}
