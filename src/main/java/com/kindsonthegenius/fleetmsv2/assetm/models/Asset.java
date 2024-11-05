package com.kindsonthegenius.fleetmsv2.assetm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.hr.models.EmployeeType;
import com.kindsonthegenius.fleetmsv2.hr.models.JobTitle;
import com.kindsonthegenius.fleetmsv2.hr.models.Person;
import com.kindsonthegenius.fleetmsv2.security.models.Auditable;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Asset extends Auditable<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int asset_id;

 private String asset_name;
 private String asset_description;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private Date created_at;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private Date updated_at;
 private String asset_model;
 private String asset_serial_number;

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


//    @ManyToOne
//    @JoinColumn(name="jobtitleid", insertable=false, updatable=false)
//    private JobTitle jobTitle;
//    private Integer jobtitleid;
      @ManyToOne
      @JoinColumn(name="assetCategoryid", insertable = false, updatable = false)
      private  AssetCategory assetCategory;
      private Integer assetCategoryid;


//machine_asset_id
//
//
//    equipmentAsset_equioment_asset_id
//    furnitureAsset_furniture_asset_id


}
