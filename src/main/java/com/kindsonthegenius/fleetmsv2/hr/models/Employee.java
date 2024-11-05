package com.kindsonthegenius.fleetmsv2.hr.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.security.models.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee extends Person {
		
	@ManyToOne
	@JoinColumn(name="employeetypeid", insertable=false, updatable=false)
	private EmployeeType employeeType;
	private Integer employeetypeid;

	private String photo;
	private String username;
	
	@ManyToOne
	@JoinColumn(name="jobtitleid", insertable=false, updatable=false)
	private JobTitle jobTitle;
	private Integer jobtitleid;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDate;

	;

	@ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER )
	@JoinTable(
			name = "employee_asset_final",
			joinColumns = {@JoinColumn(name = "employee_id")},
			inverseJoinColumns = {@JoinColumn(name = "asset_id")}
	)
   Set<Asset> assets = new HashSet<>();// used to store a collection of unique elements



}
