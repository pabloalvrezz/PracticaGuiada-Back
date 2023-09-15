package com.izertis.baseapp.service.proxy;

import java.util.List;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.filter.ProductFilter;
import com.izertis.baseapp.service.model.Product;

/**
 * Proxy service for {@link Product}. Performs DTO conversion and permission
 * checks.
 */
public interface ProductProxy extends QueryService<ProductDto, Long, ProductFilter>, SaveService<ProductDto>,
		DeleteService<ProductDto, Long> {

	/**
	 * Unlocks a product
	 *
	 * @param identifier Product identifier
	 */
	void undelete(final Long identifier);

	/**
	 * Obtains the active price of the product
	 * @param id
	 * @return
	 */
	 ProductDto findActivePrice(Long productId);
	
	 /**
	  * Obtains the similar products
	  * 
	  * @param productId
	  * 
	  * @return an array with the products 
	  */
	 List<ProductDto>  findSimilars(Long productId);

    ProductDto update(ProductDto dto, String userId) throws NoSuchEntityException;

    

    
	 
}
