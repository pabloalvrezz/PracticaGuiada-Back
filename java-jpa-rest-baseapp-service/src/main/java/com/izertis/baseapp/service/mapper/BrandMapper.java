package com.izertis.baseapp.service.mapper;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.BrandDto;
import com.izertis.baseapp.service.mapper.decorator.BrandMapperDecorator;
import com.izertis.baseapp.service.model.Brand;

@Mapper
@DecoratedWith(BrandMapperDecorator.class)
public interface BrandMapper extends BaseMapper<Brand, BrandDto>{
    
    @Override
    BrandDto convertToDto(Brand entity);
    
    List<BrandDto> convertToDto (List<Brand> entities);
    
    PageImplHelper<BrandDto> convertToDto(Page<Brand> page);
    
    Brand updateFromDto(BrandDto brandDto, @MappingTarget Brand entity);
}
