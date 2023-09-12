package com.izertis.baseapp.service.mapper.decorator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.mapper.ProductMapper;
import com.izertis.baseapp.service.model.Product;

public abstract class ProductMapperDecorator implements ProductMapper {

    /**
     * Delegate {@link Product} mapper.
     */
    @Autowired
    @Qualifier("delegate")
    private ProductMapper delegate;

    @Override
    public Product convertFromDto(final ProductDto dto) {
        if (dto == null)
            return null;

        final Product product = this.delegate.convertFromDto(dto);

        return product;

    }

    @Override
    public ProductDto convertToDto(Product entity) {
        if (entity == null)
            return null;

        return delegate.convertToDto(entity);
    }

    @Override
    public PageImplHelper<ProductDto> convertToDto(final Page<Product> page) {
        if (page == null)
            return null;

        return new PageImplHelper<>(this.delegate.convertToDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
    }

    @Override
    public List<ProductDto> convertToDto(List<Product> entities) {
        List<ProductDto> products = new ArrayList<ProductDto>();

        if (entities == null)
            return null;

        for (Product entity : entities) {
            products.add(delegate.convertToDto(entity));
        }

        return products;
    }

    @Override
    public Product updateFromDto(final ProductDto dto, Product entity) {
        if (dto == null)
            return null;

        final Product product = this.delegate.updateFromDto(dto, entity);

        return product;
    }

}
