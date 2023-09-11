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
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.filter.PriceFilter;
import com.izertis.baseapp.service.mapper.ProductMapper;
import com.izertis.baseapp.service.model.Prices;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.repository.PriceRepository;
import com.izertis.baseapp.service.repository.ProductRepository;
import com.izertis.baseapp.service.service.PriceService;
import com.izertis.baseapp.service.service.ProductService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Optional<Prices> find(Long identifier) {
        return this.priceRepository.findById(identifier);
    }

    @Override
    public Page<Prices> findPaginated(PriceFilter filter, Pageable pageable) {
        Page<Prices> page = this.priceRepository.findAll(pageable);

        return page;
    }

    @Override
    public List<Prices> findAll() {
        return this.priceRepository.findAll();
    }

    @Override
    public Prices save(Prices entity) {
        return this.priceRepository.save(entity);
    }

    @Override
    public List<Prices> save(Iterable<Prices> entities) {
        return null;
    }

    @Override
    public Prices save(Prices entity, Long productid) {
        Optional<Product> productOptional = this.productRepository.findById(productid);
        Product product = productOptional.get();
                
        entity.setProduct(entity.getProduct());

        this.productRepository.save(product);
        
        return this.priceRepository.save(entity);
    }

    @Override
    public Prices update(Prices entity) throws NoSuchEntityException {
        entity.setProduct(entity.getProduct());

        return this.priceRepository.save(entity);
    }

    @Override
    public void delete(Prices entity) {

    }

    @Override
    public void delete(Long identifier) {

    }

}
