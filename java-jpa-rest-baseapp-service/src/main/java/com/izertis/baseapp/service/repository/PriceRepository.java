package com.izertis.baseapp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.izertis.baseapp.service.model.Prices;

public interface PriceRepository extends JpaRepository<Prices, Long>, JpaSpecificationExecutor<Prices>{

}
