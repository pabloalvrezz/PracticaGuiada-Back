package com.izertis.baseapp.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.izertis.baseapp.service.filter.ProductFilter;
import com.izertis.baseapp.service.mapper.ProductMapper;
import com.izertis.baseapp.service.model.Prices;
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

    @Autowired
    private ProductMapper mapper;

    // @Autowired
    // private PriceRepository priceRepository;

    @Override
    public Optional<Product> find(Long identifier) {
        return this.productRepository.findById(identifier);
    }

    @Override
    public Page<Product> findPaginated(ProductFilter filter, Pageable pageable) {
        Page<Product> page = this.productRepository.findAll(filter, pageable);

        // recuperamos el precio activo de cada producto
        for (Product product : page) {
            this.findActivePrice(product.getId());
        }

        return page;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Indexable(Product.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Product findActivePrice(Long productId) {
        Optional<Product> aux = this.productRepository.findById(productId);
        Product product = aux.get();
        List<Prices> prices = product.getPrices();
        Date actualDate = new Date();
        ProductDto dto = this.mapper.convertToDto(product);

        // recorremos todos los precios del producto
        for (Prices price : prices) {
            // comparamos con la fecha actual
            if (price.getStartDate().before(actualDate) && price.getEndDate().after(actualDate))
                // el que este entre los valores que queremos lo establecemos como precio activo
                product.setActivePrice(price.getCuantity());
        }

        return product;
    }

    @Override
    public List<Product> findSimilars(Long productId) {
        List<Product> productos = this.productRepository.findAll(); // Lista con todos los productos
        List<Product> similares = new ArrayList<Product>(); // Lista que almacenara todos los productos similares
        Product productoSeleccionado = this.productRepository.findById(productId).get();
        
        for(Product product: productos) {
            if((product.getId() != productoSeleccionado.getId()) && (product.getTipo() == productoSeleccionado.getTipo()))
                similares.add(product);
        }
        
        return similares;
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
