package com.izertis.baseapp.service.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.filter.ProductFilter;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.repository.ProductRepository;
import com.izertis.baseapp.service.service.ProductService;
import com.izertis.libraries.solr.annotation.Indexable;
import com.izertis.libraries.solr.annotation.IndexableClass;

@IndexableClass
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {

    /**
     * Spring data repository for {@link Product}
     */
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> find(Long identifier) {
        return this.productRepository.findById(identifier);
    }

    @Override
    public Page<Product> findPaginated(ProductFilter filter, Pageable pageable) {
        Page<Product> page = this.productRepository.findAll(filter, pageable);

        return page;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Page<Product> findPaginatedAvaibles(ProductFilter filter, Pageable pageable) {
       List<Product> avaibles = new ArrayList<Product>();
        
       for(Product p: this.productRepository.findAll()) {
           if(p.getPrice() == null) avaibles.add(p);
       }
       
       Page<Product> page = new PageImpl<Product>(avaibles);
       return page;
    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Product save(final Product entity) {

        return this.productRepository.save(entity);
    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<Product> save(Iterable<Product> entities) {
        return this.productRepository.saveAll(entities);
    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Product update(Product entity) throws NoSuchEntityException {
        return this.productRepository.save(entity);
    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(Product entity) {

    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(final Long identifier) {
        this.productRepository.setProductNonLocked(false, identifier);

    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void unDelete(final Long identifier) {
        this.productRepository.setProductNonLocked(true, identifier);

    }

}
