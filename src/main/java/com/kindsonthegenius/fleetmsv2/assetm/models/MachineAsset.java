package com.kindsonthegenius.fleetmsv2.assetm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
public class MachineAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int machine_asset_id;
    private  String serial_number;
    private String model;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchase_date;
    private String operational_status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operation_hours;
    private String manufacturer;



}
