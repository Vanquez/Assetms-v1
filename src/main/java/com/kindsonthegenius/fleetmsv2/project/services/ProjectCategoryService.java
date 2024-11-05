package com.kindsonthegenius.fleetmsv2.project.services;

import com.kindsonthegenius.fleetmsv2.project.models.ProjectCategory;
import com.kindsonthegenius.fleetmsv2.project.repositories.ProjectCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectCategoryService {

    @Autowired
    private ProjectCategoryRepository projectCategoryRepository;


    //Method to save equipments
    public void save(ProjectCategory projectCategory){

        projectCategoryRepository.save(projectCategory);

    }

    //Method to retrieve saved equipments

    public List<ProjectCategory> findAll(){

        return projectCategoryRepository.findAll();
    }

    //Method to find by Id
    public Optional<ProjectCategory> findById(int id) {
        return projectCategoryRepository.findById(id);
    }

    public void deleById(int id){

        projectCategoryRepository.deleteById(id);
    }


}
