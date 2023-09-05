package com.izertis.baseapp.service.service;

import com.izertis.baseapp.service.filter.ProductFilter;
import com.izertis.baseapp.service.model.Product;

import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;

/**
 * Service to handle {@link Product} entity related operations
 */
public interface ProductService
		extends QueryService<Product, Long, ProductFilter>, SaveService<Product>, DeleteService<Product, Long> {

	/**
	 * Unlocks the product by id
	 * 
	 * @param id
	 */
	void unDelete(final Long id);

    Product findActivePrice(Long productId);
       
}
