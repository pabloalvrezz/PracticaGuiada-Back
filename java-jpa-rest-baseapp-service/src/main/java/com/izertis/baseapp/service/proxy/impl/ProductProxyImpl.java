package com.izertis.baseapp.service.proxy.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.filter.ProductFilter;
import com.izertis.baseapp.service.mapper.ProductMapper;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.proxy.ProductProxy;
import com.izertis.baseapp.service.service.ProductService;

@Service
public class ProductProxyImpl implements ProductProxy {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Optional<ProductDto> find(Long identifier) {
        return this.productMapper.convertToDto(this.productService.find(identifier));
    }

    @Override
    public Page<ProductDto> findPaginated(ProductFilter filter, Pageable pageable) {
        return this.productMapper.convertToDto(this.productService.findPaginated(filter, pageable));
    }

    @Override
    public List<ProductDto> findAll() {
        return this.productMapper.convertToDto(this.productService.findAll());
    }

    @Override
    public ProductDto findActivePrice(Long productId) {
        return this.productMapper.convertToDto(this.productService.findActivePrice(productId));
    }

    @Override
    public List<ProductDto> findSimilars(Long productId) {
        return this.productMapper.convertToDto(this.productService.findSimilars(productId));
    }

    @Override
    public ProductDto save(ProductDto entity) {
        return this.productMapper.convertToDto(this.productService.save(this.productMapper.convertFromDto(entity)));
    }

    @Override
    public List<ProductDto> save(Iterable<ProductDto> entities) {
        return this.productMapper.convertToDto(this.productService.save(this.productMapper.convertFromDto(entities)));
    }

    @Override
    public ProductDto update(ProductDto entity) throws NoSuchEntityException {
        final Product product = this.productMapper.updateFromDto(entity, this.productService.find(entity.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format("Product %s not found", entity.getId()))));

        return this.productMapper.convertToDto(this.productService.update(product));

    }

    @Override
    public ProductDto update(ProductDto dto, String userId) {
        return this.productMapper.convertToDto(this.productService.update(this.productMapper.convertFromDto(dto), userId));
     
    }

    @Override
    public void delete(ProductDto entity) {
        this.productService.delete(this.productMapper.convertFromDto(entity));
    }

    @Override
    public void delete(Long identifier) {
        this.productService.delete(identifier);
    }

    @Override
    public void undelete(Long identifier) {
        this.productService.unDelete(identifier);
    }

}
