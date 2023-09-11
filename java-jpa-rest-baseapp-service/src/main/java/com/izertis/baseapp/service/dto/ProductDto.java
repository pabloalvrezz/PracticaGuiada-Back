package com.izertis.baseapp.service.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.izertis.baseapp.service.model.Prices;
import com.izertis.baseapp.service.validation.group.Create;
import com.izertis.baseapp.service.validation.group.Update;
import com.izertis.libraries.audit.dto.AuditableDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Clase que usaremos para agregar las restricciones 
 * a los campos de la clase Product
 */

@Getter
@Setter
@ToString(includeFieldNames = true)
public class ProductDto extends AuditableDto {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @NotNull
    @Size(min = 1, max = 25)
    private String description;

    @NotNull
    @Min(1)
    private int stock;

    private double activePrice;

    @NotNull
    private boolean enabled;

    private List<Prices> price;
}
