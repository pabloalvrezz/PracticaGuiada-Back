package com.izertis.baseapp.service.dto;

import java.sql.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.validation.group.Create;
import com.izertis.baseapp.service.validation.group.Update;
import com.izertis.libraries.audit.dto.AuditableDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto extends AuditableDto{

	@NotNull(groups = Update.class)
	@Null(groups = Create.class)
	private Long id;
	
	@NotNull
	@Min(1)
	private double cuantity;
	
	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;
	
	private Product product;
}
