package com.kindsonthegenius.fleetmsv2.officeequipments.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kindsonthegenius.fleetmsv2.parameters.models.CommonObject;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OfficeEquipment extends CommonObject{

  private String name;
  private String ifmisNumber;
  private String grzNumber;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateOfAcquisition;
  private String serialNumber;
  private BigDecimal capitalizationAmount;
  private BigDecimal revaluationAmount;
  private BigDecimal fairValue;
  private BigDecimal useFullLife;
  private BigDecimal disposalAmount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date disposalDate;
  private BigDecimal depreciationAmount;
  private BigDecimal netBookValue;

  @ManyToOne
  @JoinColumn(name = "officeEquipmentCategory_id",updatable = false,insertable = false)
  private OfficeEquipmentCategory officeEquipmentCategory;
  private Integer officeEquipmentCategory_id;

}
