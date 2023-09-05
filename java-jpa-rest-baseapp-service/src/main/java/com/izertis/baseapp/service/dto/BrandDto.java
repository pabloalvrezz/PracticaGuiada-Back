package com.izertis.baseapp.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.izertis.baseapp.service.validation.group.Create;
import com.izertis.baseapp.service.validation.group.Update;
import com.izertis.libraries.audit.dto.AuditableDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = true)
public class BrandDto extends AuditableDto{
    
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;
    
    @NotNull
    @Min(1)
    private double marketValue;
    
    @NotNull
    private String name;
    
    @NotNull
    private String location;
    
    @NotNull
    private String CEO;
    
    @NotNull
    private String sector;
}
