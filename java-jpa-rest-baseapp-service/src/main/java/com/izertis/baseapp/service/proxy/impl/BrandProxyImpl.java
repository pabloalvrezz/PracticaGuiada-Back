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
import com.izertis.baseapp.service.proxy.BrandProxy;
import com.izertis.baseapp.service.service.BrandService;

@Service
public class BrandProxyImpl implements BrandProxy{

    @Autowired
    private BrandMapper brandMapper;
    
    @Autowired
    private BrandService brandService;
    
    @Override
    public Optional<BrandDto> find(Long identifier) {
        return this.brandMapper.convertToDto(this.brandService.find(identifier));
    }

    @Override
    public Page<BrandDto> findPaginated(BrandFilter filter, Pageable pageable) {
       return this.brandMapper.convertToDto(this.brandService.findPaginated(filter, pageable));
    }

    @Override
    public List<BrandDto> findAll() {
        return this.brandMapper.convertToDto(this.brandService.findAll());
    }

    @Override
    public BrandDto save(BrandDto entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BrandDto> save(Iterable<BrandDto> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BrandDto update(BrandDto entity) throws NoSuchEntityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(BrandDto entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Long identifier) {
        // TODO Auto-generated method stub
        
    }

}
