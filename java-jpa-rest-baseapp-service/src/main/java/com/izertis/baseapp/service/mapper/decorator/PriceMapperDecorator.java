package com.izertis.baseapp.service.mapper.decorator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.PriceDto;
import com.izertis.baseapp.service.mapper.PriceMapper;
import com.izertis.baseapp.service.model.Prices;

public class PriceMapperDecorator implements PriceMapper{

	@Autowired
	@Qualifier("delegate")
	private PriceMapper delegate;
	
	@Override
	public Prices convertFromDto(PriceDto dto) {
		final Prices price;
		
		if(dto == null) return null;
		
		price = this.delegate.convertFromDto(dto);
		
		return price;
	}

	@Override
	public PriceDto convertToDto(Prices entity) {
		if(entity == null)
			return null;
		return delegate.convertToDto(entity);
	}

	@Override
	public List<PriceDto> convertToDto(List<Prices> entitities) {
		return null;
	}

	@Override
	public PageImplHelper<PriceDto> convertToDto(Page<Prices> page) {
		if(page == null) return null;
		
		return new PageImplHelper<>(this.delegate.convertToDto(page.getContent()),
				PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
	}

	@Override
	public Prices updateFromDto(PriceDto dto, Prices entity) {
		final Prices price;
		
		if(dto == null) return null;
				
		return price= this.delegate.updateFromDto(dto, entity);
	}

}
