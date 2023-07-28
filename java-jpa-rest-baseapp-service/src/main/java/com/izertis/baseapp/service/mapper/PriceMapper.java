package com.izertis.baseapp.service.mapper;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.PriceDto;
import com.izertis.baseapp.service.mapper.decorator.PriceMapperDecorator;
import com.izertis.baseapp.service.model.Prices;

@Mapper
@DecoratedWith(PriceMapperDecorator.class)
public interface PriceMapper extends BaseMapper<Prices, PriceDto> {

	/**
	 * Convierte la entidad en un Dto
	 * 
	 * @param the entity
	 * 
	 * @return the Dto
	 */
	PriceDto convertToDto(Prices entity);

	/**
	 * Devuelve una lista con las entidades convertidas a dto
	 * 
	 * @param la lista con los preciosç
	 * 
	 * @return la entidad Dto
	 */
	List<PriceDto> convertToDto(List<Prices> entitities);

	/**
	 * @param page
	 * 
	 * @return la pagina con todos los dto
	 */
	PageImplHelper<PriceDto> convertToDto(Page<Prices> page);
	
	Prices updateFromDto(PriceDto dto, @MappingTarget Prices entity);
}
