package com.izertis.baseapp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.izertis.baseapp.service.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, String>, JpaSpecificationExecutor<Brand>{

}
