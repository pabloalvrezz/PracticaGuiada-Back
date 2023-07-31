package com.izertis.baseapp.service.proxy;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<ProductDto> findPaginatedAvaibles(ProductFilter filter,
             final Pageable pageable);
	
	
}
