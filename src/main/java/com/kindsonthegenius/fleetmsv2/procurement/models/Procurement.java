package com.kindsonthegenius.fleetmsv2.procurement.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kindsonthegenius.fleetmsv2.parameters.models.CommonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class Procurement extends CommonObject{

    private String name;
    private Integer purchaseOrderNumber;
    private Integer quantity;
    private String code;
    private String uom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfAcquisition;
    private BigDecimal price;
    private BigDecimal amount;
    private String status;


    // Correct the multiplication
    public void calculateAmount() {
        if (price != null) {
            // Convert 'quantity' to BigDecimal and multiply by 'price'
            this.amount = BigDecimal.valueOf(quantity).multiply(price);
        } else {
            this.amount = BigDecimal.ZERO;
        }
    }


}
