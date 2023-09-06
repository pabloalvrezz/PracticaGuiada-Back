package com.izertis.baseapp.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.izertis.baseapp.service.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand>{
    
    Optional<Brand> findById(Long id);

    @Modifying
    @Query("update Brand b set b.enabled = ?1 where b.id = ?2")
    void setBrandNonLocked(boolean b, Long id);
    
}
