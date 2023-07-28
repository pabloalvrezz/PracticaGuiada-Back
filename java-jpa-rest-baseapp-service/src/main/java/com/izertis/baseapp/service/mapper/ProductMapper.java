package com.izertis.baseapp.service.mapper;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.dto.UserDto;
import com.izertis.baseapp.service.mapper.decorator.ProductMapperDecorator;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.model.User;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper extends BaseMapper<Product, ProductDto> {
	
	/**
	 * Convert entity to DTO
	 * 
	 * @param entity
	 * 
	 * @return the DTO
	 */
	@Override
	ProductDto convertToDto(Product entity);
	
	/**
	 * Convert the entity to Dto
	 * 
	 * @param price entity
	 * 
	 * @return the entity Dto
	 */
	List<ProductDto> convertToDto(List<Product> entities);

	Product updateFromDto(ProductDto dto, @MappingTarget Product entity);

	PageImplHelper<ProductDto> convertToDto(Page<Product> page);

	/**
     * Convert DTO list to entity.
     *
     * @param dto
     *            the list of DTOs
     * @return the list
     */
	Iterable<Product> convertFromDto(Iterable<ProductDto> entities);

	
}
 	