package com.izertis.baseapp.back.controller.security;

import java.util.List;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.filter.ProductFilter;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.proxy.ProductProxy;
import com.izertis.baseapp.service.validation.group.Create;
import com.izertis.baseapp.service.validation.group.Update;

@RestController
@RequestMapping(ProductController.Mappings.BASE)
public class ProductController {

    /**
     * Proxy service implementation for {@link Product}
     */
    @Autowired
    private ProductProxy productProxy;

    /**
     * DTO to entity mapper
     */
   
    /**
     * Get all the products
     * 
     * @return the products
     */
    @GetMapping(ProductController.Mappings.LIST)
    private List<ProductDto> getProducts() {

        return this.productProxy.findAll();
    }

    /**
     * Get all the products without price
     * 
     * @return the products
     */
   @GetMapping(ProductController.Mappings.AVAIBLES)
   private Page<ProductDto> getAvaibles(@ParameterObject final ProductFilter filter,
           @ParameterObject final Pageable pageable){
       return this.productProxy.findPaginatedAvaibles(filter, pageable);
   }

    @GetMapping(ProductController.Mappings.SEARCH)
    private Page<ProductDto> searchProducts(@ParameterObject final ProductFilter filter,
            @ParameterObject final Pageable pageable) {
        return this.productProxy.findPaginated(filter, pageable);
    }

    /**
     * Get the product by id
     * 
     * @param id
     * @return the product
     */
    @GetMapping(ProductController.Mappings.GET)
    public ProductDto getProduct(@PathVariable("id") final Long id) {
        return this.productProxy.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Update a product
     * 
     * @param ProductDto
     * 
     * @return the application product dto
     */
    // @Secured(Role.ADMINISTRATOR_ROLE)
    @PutMapping
    public ProductDto update(@RequestBody @Validated(Update.class) final ProductDto dto) {
        try {
            return this.productProxy.update(dto);
        }

        catch (final NoSuchEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Disable the product by its id
     * 
     * @param id
     */
    // @Secured(Role.ADMINISTRATOR_ROLE)
    @PutMapping(ProductController.Mappings.DISABLE)
    public void disable(@PathVariable("id") final Long id) {
        this.productProxy.delete(id);
    }

    @PutMapping(ProductController.Mappings.ENABLE)
    public void enable(@PathVariable("id") final Long id) {
        this.productProxy.undelete(id);
    }

    /**
     * Save the products
     * 
     * @param the
     *            product
     * 
     * @return the saved product
     */

    // @Secured(Role.ADMINISTRATOR_ROLE)
    @PostMapping
    public ProductDto save(@RequestBody @Validated(Create.class) final ProductDto dto) {
        return this.productProxy.save(dto);

    }

    /*
     * Mappings
     */

    static final class Mappings {

        /**
         * Controller request mapping
         */
        protected static final String BASE = "/product";

        /**
         * Mapping for list
         */
        protected static final String LIST = "/list";

        /**
         * Mapping for avaibles list
         */
        protected static final String AVAIBLES = "/avaibles";

        /*
         * Mapping for getbyId
         */
        protected static final String GET = "/{id}";

        /**
         * 
         */
        protected static final String SEARCH = "/search";

        /**
         * Mapping for disable
         */
        protected static final String DISABLE = GET + "/disable";

        /**
         * Mapping for enable
         */
        protected static final String ENABLE = GET + "/enable";
    }
}
