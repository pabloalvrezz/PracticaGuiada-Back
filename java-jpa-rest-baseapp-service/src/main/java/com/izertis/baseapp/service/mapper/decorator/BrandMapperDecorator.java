package com.izertis.baseapp.service.mapper.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.BrandDto;
import com.izertis.baseapp.service.mapper.BrandMapper;
import com.izertis.baseapp.service.model.Brand;

public abstract class BrandMapperDecorator implements BrandMapper {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public BrandDto convertToDto(Brand entity) {
        if (entity == null)
            return null;

        return brandMapper.convertToDto(entity);
    }

    @Override
    public PageImplHelper<BrandDto> convertToDto(Page<Brand> page) {
        if (page == null)
            return null;

        return new PageImplHelper<>(this.brandMapper.convertToDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
    }

    @Override
    public Brand convertFromDto(BrandDto dto) {
        final Brand brand;

        if (dto == null)
            return null;

        brand = this.brandMapper.convertFromDto(dto);

        return brand;
    }
}
