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
import com.izertis.libraries.solr.annotation.Indexable;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandServiceImpl implements BrandService {

    @Autowired
    public BrandRepository brandRepository;

    @Override
    public Optional<Brand> find(Long identifier) {
        return Optional.empty();
    }
    
    @Override
    public List<Brand> findAll() {
        return null;
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Page<Brand> findPaginated(BrandFilter filter, Pageable pageable) {
        Page<Brand> brand = this.brandRepository.findAll(filter, pageable);

        return brand;
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Brand save(Brand entity) {
        return null;
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Brand> save(Iterable<Brand> entities) {
        return null;
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Brand save(Brand entity, Long brandId) {
        return this.brandRepository.save(entity);
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Brand update(Brand entity) throws NoSuchEntityException {
        return this.brandRepository.save(entity);
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Brand entity) {

    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Long id) {
        this.brandRepository.setBrandNonLocked(false, id);
    }

    @Override
    @Indexable(Brand.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void unDelete(Long id) {
        this.brandRepository.setBrandNonLocked(true, id);
    }

}
