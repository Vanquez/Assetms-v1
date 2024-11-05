package com.kindsonthegenius.fleetmsv2.procurement.services;

import com.kindsonthegenius.fleetmsv2.procurement.models.Procurement;
import com.kindsonthegenius.fleetmsv2.procurement.repositories.ProcurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProcurementService {
    @Autowired
    private ProcurementRepository projectRepository;


    //Method to save equipments
    public void save(Procurement project){

        projectRepository.save(project);

    }


    //Method to retrieve saved equipments
    public List<Procurement> findAll(){

        return projectRepository.findAll();
    }

    //Method to find by Id
   public Procurement findById(int id){

        return projectRepository.findById(id).orElse(null);
   }

   public void deleById(int id){

        projectRepository.deleteById(id);
   }

    //find unique records methods
    public boolean isPurchaseOrderNumberExists(Integer purchaseOrderNumber) {
        return projectRepository.findByPurchaseOrderNumber(purchaseOrderNumber) != null;
    }

//    public boolean isGrzNumberExists(String grzNumber) {
//        return projectRepository.findByGrzNumber(grzNumber) != null;
//    }
//

    //Get asset by Keyword
    public List<Procurement> findByKeyword(Integer keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException("Keyword cannot be null");
        }

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

    public boolean updateStatus(Integer id, String status) {
        // Find the procurement record by ID
        Procurement procurement = projectRepository.findById(id).orElse(null);
        if (procurement != null) {
            // Update the status
            procurement.setStatus(status);
            projectRepository.save(procurement); // Save the updated procurement
            return true; // Indicate success
        }
        return false; // Indicate failure (record not found)
    }

}
