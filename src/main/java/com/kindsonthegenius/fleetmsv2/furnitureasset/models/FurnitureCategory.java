package com.kindsonthegenius.fleetmsv2.furnitureasset.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FurnitureCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int furnitureCategory_id;
    private String description;
    private String details;

}
