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
import com.izertis.baseapp.service.dto.PriceDto;
import com.izertis.baseapp.service.filter.PriceFilter;
import com.izertis.baseapp.service.mapper.PriceMapper;
import com.izertis.baseapp.service.model.Prices;
import com.izertis.baseapp.service.proxy.PriceProxy;
import com.izertis.baseapp.service.service.PriceService;
import com.izertis.baseapp.service.validation.group.Create;
import com.izertis.baseapp.service.validation.group.Update;


@RestController
@RequestMapping(PriceController.Mappings.BASE)
public class PriceController {

	/**
	 * Implementacion del price proxy
	 */
	@Autowired
	private PriceProxy priceProxy;

	/**
	 * Implementacion del price mapper
	 */
	@Autowired
	private PriceMapper priceMapper;
	
	/**
	 * Implementacion del price Service
	 */
	@Autowired
	private PriceService priceService;

	/**
	 * Devuelve una lista con todos los precios
	 * 
	 * @return the prices list
	 */
	@GetMapping(PriceController.Mappings.LIST)
	public List<PriceDto> getPrices() {
		return this.priceProxy.findAll();
	}

	/**
	 * Deuelve una lista con todos los precios en paginas
	 * 
	 * @param filtro de busqueda
	 * 
	 * @param Pageable
	 * 
	 * @return la lista con los precios
	 */
	@GetMapping(PriceController.Mappings.SEARCH)
	public Page<PriceDto> searchPrices(@ParameterObject final PriceFilter filter,
			@ParameterObject final Pageable Pageable) {
		return this.priceProxy.findPaginated(filter, Pageable);
	}
	
	/**
	 * Obtiene los precios por id
	 * 
	 * @param el id
	 * 
	 * @return el precio con el id correspondiente
	 */
	@GetMapping(PriceController.Mappings.ID)
	public PriceDto getPrice(@PathVariable("id") final Long id) {
		return this.priceProxy.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
		
	/**
	 * Actualiza el precio
	 * 
	 * @param dto
	 * 
	 * @return precio actualizado
	 * 
	 * @throws Exception
	 */
	@PutMapping
	public PriceDto updatePrice(@RequestBody @Validated(Update.class) final PriceDto dto) throws Exception {
		try {
			return this.priceProxy.update(dto);
		}
		
		catch (final NoSuchEntityException e){
			throw new Exception("Elemento no encontrado");
		}
	}
	
	@PostMapping(PriceController.Mappings.ID)
	public PriceDto save(@RequestBody @Validated(Create.class)final PriceDto dto,
	        @PathVariable("id") final Long productid) {    
		return this.priceProxy.save(dto,productid);
	}
			
	public static class Mappings {
		
		// Url para buscar en el apartado de precios
		private static final String BASE = "/prices";

		// Url para buscar en el apartado de precios
		private static final String LIST = "/list";
		
		
		// Url para buscar todos los precios por paginas
		private static final String SEARCH = "/search";

		// Url para buscar todos los precios por paginas
		private static final String ID = "/{id}";
				
	}
}
