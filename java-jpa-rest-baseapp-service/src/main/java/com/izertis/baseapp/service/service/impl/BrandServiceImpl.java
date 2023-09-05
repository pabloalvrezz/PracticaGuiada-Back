package com.izertis.baseapp.service.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.filter.BrandFilter;
import com.izertis.baseapp.service.model.Brand;
import com.izertis.baseapp.service.repository.BrandRepository;
import com.izertis.baseapp.service.service.BrandService;
import com.izertis.libraries.solr.annotation.IndexableClass;

@IndexableClass
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandServiceImpl implements BrandService{

    @Autowired
    public BrandRepository brandRepository;
    
    @Override
    public Optional<Brand> find(Long identifier) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Page<Brand> findPaginated(BrandFilter filter, Pageable pageable) {
        Page<Brand> brand = this.brandRepository.findAll(filter, pageable);
        
        return brand;
    }

    @Override
    public List<Brand> findAll() {
        return this.brandRepository.findAll();
    }

    @Override
    public Brand save(Brand entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Brand> save(Iterable<Brand> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Brand update(Brand entity) throws NoSuchEntityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Brand entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Long identifier) {
        // TODO Auto-generated method stub
        
    }

}
