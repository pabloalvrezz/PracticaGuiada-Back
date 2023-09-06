package com.izertis.baseapp.back.controller.security;

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
import com.izertis.baseapp.service.dto.BrandDto;
import com.izertis.baseapp.service.filter.BrandFilter;
import com.izertis.baseapp.service.proxy.BrandProxy;
import com.izertis.baseapp.service.validation.group.Create;
import com.izertis.baseapp.service.validation.group.Update;

@RestController
@RequestMapping(BrandController.Mappings.BASE)
public class BrandController {

    /**
     * Implementacion del Brand Proxy
     */
    @Autowired
    private BrandProxy brandProxy;

    /**
     * Devuelve una lista con todas las marcas
     * 
     * @return las marcas paginadas
     */
    @GetMapping(BrandController.Mappings.SEARCH)
    public Page<BrandDto> searchBrands(@ParameterObject final BrandFilter brandFilter,
            @ParameterObject final Pageable Pageable) {
        return this.brandProxy.findPaginated(brandFilter, Pageable);
    }

    /**
     * Busca una marca por su id
     * 
     * @param id
     * 
     * @return marca
     */
    @GetMapping(BrandController.Mappings.GET)
    public BrandDto getBrandById(@PathVariable("id") final Long id) {
        return this.brandProxy.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Devuelve la marca actualizada
     * 
     * @param el Dto de la marca
     * 
     * @return la marca actualizada
     * 
     * @throws Exception en caso de no encontrar el id que quiera el usuario
     */
    @PutMapping
    public BrandDto updateBrand(@RequestBody @Validated(Update.class) final BrandDto brandDto) throws Exception {
        try {
            return this.brandProxy.update(brandDto);
        }

        catch (final NoSuchEntityException e) {
            throw new Exception("Elemento no encontrado");
        }
    }
    
    /**
     * Guarda las marcas
     * 
     * @param dto de la marca
     * 
     * @param id de la marca
     * 
     * @return la marca guardada
     */
    @PostMapping
    public BrandDto save(@RequestBody @Validated(Create.class) final BrandDto dto,
            @PathVariable("id")final Long brandId) {
        return this.brandProxy.save(dto, brandId);
    }
    
    @PutMapping(BrandController.Mappings.ENABLE)
    public void enable(@PathVariable("id") final Long id) {
         this.brandProxy.undelete(id);
    }
    
    @PutMapping(BrandController.Mappings.DISABLE)
    public void disable(@PathVariable("id") final Long id) {
         this.brandProxy.delete(id);
    }

    public static class Mappings {
        private static final String BASE = "/brand";

        private static final String SEARCH = "/search";

        private static final String GET = "/{id}";
        
        private static final String ENABLE = "/enable";
        
        private static final String DISABLE = "/disable";

    }
}
