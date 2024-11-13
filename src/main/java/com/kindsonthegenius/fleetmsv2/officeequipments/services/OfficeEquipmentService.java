package com.kindsonthegenius.fleetmsv2.officeequipments.services;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.models.DepreciatedAsset;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.DepreciatedOfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.models.OfficeEquipment;
import com.kindsonthegenius.fleetmsv2.officeequipments.repositories.DepreciatedOfficeEquipmentRepository;
import com.kindsonthegenius.fleetmsv2.officeequipments.repositories.OfficeEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Access;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeEquipmentService {
    @Autowired
    private OfficeEquipmentRepository officeEquipmentRepository;

    @Autowired
    private DepreciatedOfficeEquipmentRepository depreciatedOfficeEquipmentRepository;


    //Method to save equipments
    public void save(OfficeEquipment officeEquipment) {

        officeEquipmentRepository.save(officeEquipment);

    }


    //Method to retrieve saved equipments

    public List<OfficeEquipment> findAll() {

        return officeEquipmentRepository.findAll();
    }

    //Method to find by Id
    public OfficeEquipment findById(int id) {

        return officeEquipmentRepository.findById(id).orElse(null);
    }

    public void deleById(int id) {

        officeEquipmentRepository.deleteById(id);
    }

    //find unique records methods
    public boolean isIfmisNumberExists(String ifmisNumber) {
        return officeEquipmentRepository.findByIfmisNumber(ifmisNumber) != null;
    }

    public boolean isGrzNumberExists(String grzNumber) {
        return officeEquipmentRepository.findByGrzNumber(grzNumber) != null;
    }

    public boolean isSerialNumberExists(String serialNumber) {
        return officeEquipmentRepository.findBySerialNumber(serialNumber) != null;
    }

    //Get asset by Keyword
    public List<OfficeEquipment> findByKeyword(String keyword) {
        return officeEquipmentRepository.findByKeyword(keyword);
    }

    // Get total number of Equipments
    public List<Integer> getNumberEquipment() {
        return officeEquipmentRepository.getNumberEquipment();
    }

    public BigDecimal findTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (officeEquipmentRepository != null) {
            BigDecimal costCar = officeEquipmentRepository.findTotalCost();
            if (costCar != null) {
                totalCost = costCar;
            }
        }
        return totalCost;
    }


    @Transactional
    public void migrateAssets() {
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        Date date = Date.from(fiveYearsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<OfficeEquipment> oldAssets = officeEquipmentRepository.findOldAssets(date);

        for (OfficeEquipment asset : oldAssets) {
            // Check if the asset already exists in the depreciated table
            if (depreciatedOfficeEquipmentRepository.existsByAssetSerialNumber(asset.getSerialNumber())) {
                continue; // Skip this asset if it has already been migrated
            }

            DepreciatedOfficeEquipment depreciatedAsset = new DepreciatedOfficeEquipment();
            depreciatedAsset.setName(asset.getName());
            depreciatedAsset.setCreatedDate(asset.getCreatedDate());
            depreciatedAsset.setDescription(asset.getDescription());
//            depreciatedAsset.setAsset_model(asset.getGrzNumber());
            depreciatedAsset.setSerialNumber(asset.getSerialNumber());
            depreciatedAsset.setIfmisNumber(asset.getIfmisNumber());
            depreciatedAsset.setGrzNumber(asset.getGrzNumber());
            depreciatedAsset.setDateOfAcquisition(asset.getDateOfAcquisition());
            depreciatedAsset.setSerialNumber(asset.getSerialNumber());
            depreciatedAsset.setCapitalizationAmount(asset.getCapitalizationAmount());
            depreciatedAsset.setRevaluationAmount(asset.getRevaluationAmount());
            depreciatedAsset.setFairValue(asset.getFairValue());
            depreciatedAsset.setUseFullLife(asset.getUseFullLife());
            depreciatedAsset.setDisposalAmount(asset.getDisposalAmount());
            depreciatedAsset.setDisposalDate(asset.getDisposalDate());
            depreciatedAsset.setDepreciationAmount(asset.getDepreciationAmount());
            depreciatedAsset.setNetBookValue(asset.getNetBookValue());
            // Copy other fields as needed

            depreciatedOfficeEquipmentRepository.save(depreciatedAsset);
        }


    }

    public List<DepreciatedOfficeEquipment> findDepreciatedAssest() {

        List<DepreciatedOfficeEquipment>  depreciatedAssets = depreciatedOfficeEquipmentRepository.findAll();
        return depreciatedAssets;
    }

    //Get depreciated asset by Keyword
    public List<DepreciatedOfficeEquipment> findByKeywordDepreciatedAsset(String keyword) {
        return depreciatedOfficeEquipmentRepository.findByKeyword(keyword);
    }

}
