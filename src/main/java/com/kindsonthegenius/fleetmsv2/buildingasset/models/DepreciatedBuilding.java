package com.kindsonthegenius.fleetmsv2.buildingasset.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kindsonthegenius.fleetmsv2.parameters.models.CommonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DepreciatedBuilding extends CommonObject {

    private String name;
    private String ifmisNumber;
    private String grzNumber;
    private String location;
    private String plotNumber;
    private Date dateOfAcquisition;
    private String serialNumber;
    private BigDecimal capitalizationAmount;
    private BigDecimal revaluationAmount;
    private BigDecimal fairValue;
    private BigDecimal useFullLife;
    private BigDecimal disposalAmount;
    private Date disposalDate;
    private BigDecimal depreciationAmount;
    private BigDecimal netBookValue;

}
