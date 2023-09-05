package com.izertis.baseapp.service.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.izertis.baseapp.service.model.User.Columns;
import com.izertis.libraries.audit.jpa.model.Auditable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Brand")
@Table(name = "Brand")

@Getter
@Setter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Brand extends Auditable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Columns.ID)
    public Long Id;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "Location")
    private String location;
    
    @Column(name = "MarketValue")
    private double marketValue;
    
    @Column(name = "CEO")
    private String CEO;
    
    @Column(name ="Sector")
    private String sector;
}
