package com.izertis.baseapp.back.controller.security;

import java.util.List;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.izertis.baseapp.service.dto.BrandDto;
import com.izertis.baseapp.service.filter.BrandFilter;
import com.izertis.baseapp.service.proxy.BrandProxy;

@RestController
@RequestMapping(BrandController.Mappings.BASE)
public class BrandController {

    @Autowired
    private BrandProxy brandProxy;

    /**
     * Devuelve una lista con todas las marcas
     */
    @GetMapping(BrandController.Mappings.LIST)
    public List<BrandDto> getBrands() {
        return this.brandProxy.findAll();
    }

    /**
     * Devuelve una lista con todas las marcas
     */
    @GetMapping(BrandController.Mappings.SEARCH)
    public Page<BrandDto> searchBrands(@ParameterObject final BrandFilter brandFilter,
            @ParameterObject final Pageable Pageable) {
        return this.brandProxy.findPaginated(brandFilter, Pageable);
    }

    /**
     * Obtiene las marcas por id
     */
    @GetMapping(BrandController.Mappings.GET)
    public BrandDto getBrandById(@PathVariable("id") final Long id) {
        return this.brandProxy.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public static class Mappings {
        private static final String BASE = "/brand";

        private static final String LIST = "/list";

        private static final String SEARCH = "/search";

        private static final String GET = "/{id}";
    }
}
