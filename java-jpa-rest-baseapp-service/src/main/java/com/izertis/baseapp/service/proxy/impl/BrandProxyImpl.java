package com.izertis.baseapp.service.proxy.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.dto.BrandDto;
import com.izertis.baseapp.service.filter.BrandFilter;
import com.izertis.baseapp.service.mapper.BrandMapper;
import com.izertis.baseapp.service.model.Brand;
import com.izertis.baseapp.service.proxy.BrandProxy;
import com.izertis.baseapp.service.service.BrandService;

@Service
public class BrandProxyImpl implements BrandProxy {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandService brandService;

    @Override
    public Optional<BrandDto> find(Long identifier) {
        return this.brandMapper.convertToDto(this.brandService.find(identifier));
    }
    
    @Override
    public List<BrandDto> findAll() {
        return null;
    }


    @Override
    public Page<BrandDto> findPaginated(BrandFilter filter, Pageable pageable) {
        return this.brandMapper.convertToDto(this.brandService.findPaginated(filter, pageable));
    }

    @Override
    public BrandDto save(BrandDto entity) {
        return null;
    }

    @Override
    public List<BrandDto> save(Iterable<BrandDto> entities) {
        return null;
    }

    @Override
    public BrandDto save(BrandDto dto, Long brandId) {
        Brand brand = this.brandMapper.convertFromDto(dto);
                        
        return this.brandMapper.convertToDto(brand);
    }
    
    @Override
    public BrandDto update(BrandDto brandDto) throws NoSuchEntityException {
        final Brand brand = this.brandMapper.updateFromDto(brandDto, this.brandService.find(brandDto.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format("Product %s not found", brandDto.getId()))));

        return this.brandMapper.convertToDto(this.brandService.update(brand));
    }

    @Override
    public void delete(BrandDto brandDto) {
        this.brandService.delete(this.brandMapper.convertFromDto(brandDto));
    }

    @Override
    public void delete(Long identifier) {
        this.brandService.delete(identifier);
    }
    

    @Override
    public void undelete(Long id) {
     this.brandService.unDelete(id);        
    }
    

}
