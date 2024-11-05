package com.kindsonthegenius.fleetmsv2.assetm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AssetAssignment {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int   asset_assignment_id;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private Date assignment_date;
 private String assignment_details;
 private Date created_at;
 private Date updated_at;

 @ManyToOne
 @JoinColumn(name = "asset_id", insertable = false, updatable = false)
 private Asset asset;
 private int asset_id;


}
