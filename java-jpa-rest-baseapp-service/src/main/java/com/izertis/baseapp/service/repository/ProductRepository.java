package com.izertis.baseapp.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.izertis.baseapp.service.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
			
	/**
	 * Finds a product by the id
	 * 
	 * @param product id
	 * 
	 * @return an {@link Product} entity stored in the database
	 */
	Optional <Product> findById(Long id);

	@Modifying
	@Query("update Product p set p.enabled = ?1 where p.id = ?2")
	void setProductNonLocked(boolean productNonLocked, Long identifier);
	
	}
