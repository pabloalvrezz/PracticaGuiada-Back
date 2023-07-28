package com.izertis.baseapp.service.service;

import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;
import com.izertis.baseapp.service.dto.PriceDto;
import com.izertis.baseapp.service.filter.PriceFilter;
import com.izertis.baseapp.service.model.Prices;

public interface PriceService extends QueryService<Prices, Long, PriceFilter>,
SaveService<Prices>, DeleteService<Prices, Long>{

    /**
     * Metodo para guardar el precio y el id del producto a la vez
     * 
     * @param el dto del price
     * 
     * @param el id del prodcuto
     * 
     * @return el dto del precio ya guardado
     */
    Prices save(Prices entity, Long productid);

	
}
