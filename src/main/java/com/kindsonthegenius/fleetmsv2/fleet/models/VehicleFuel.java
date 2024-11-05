package com.kindsonthegenius.fleetmsv2.fleet.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VehicleFuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fillDate;

    @ManyToOne
    @JoinColumn(name="vehicle_id", insertable=false, updatable=false)
    private Vehicle vehicle;
    private int vehicle_id;

    @ManyToOne
    @JoinColumn(name = "vehicletypeid", insertable = false, updatable = false)
    private VehicleType vehicleType;
    private Integer vehicletypeid;

    private String vehicleNumber;


    private int quantity;

    private int odometerReading;

    private BigDecimal amount;



}
