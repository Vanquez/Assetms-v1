package com.kindsonthegenius.fleetmsv2.project.services;

import com.kindsonthegenius.fleetmsv2.project.models.Project;
import com.kindsonthegenius.fleetmsv2.project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;


    //Method to save equipments
    public void save(Project project){

        projectRepository.save(project);

    }


    //Method to retrieve saved equipments
    public List<Project> findAll(){

        return projectRepository.findAll();
    }

    //Method to find by Id
   public Project findById(int id){

        return projectRepository.findById(id).orElse(null);
   }

   public void deleById(int id){

        projectRepository.deleteById(id);
   }

    //find unique records methods
    public boolean isIfmisNumberExists(String ifmisNumber) {
        return projectRepository.findByIfmisNumber(ifmisNumber) != null;
    }

//    public boolean isGrzNumberExists(String grzNumber) {
//        return projectRepository.findByGrzNumber(grzNumber) != null;
//    }
//

    //Get asset by Keyword
    public List<Project> findByKeyword(String keyword) {
        return projectRepository.findByKeyword(keyword);
    }

    //Project Service
    public List<Integer> getNumberProject(){return  projectRepository.getNumberProject();}

    public BigDecimal findTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (projectRepository != null) {
            BigDecimal costCar = projectRepository.findTotalCost();
            if (costCar != null) {
                totalCost = costCar;
            }
        }
        return totalCost;
    }

}
