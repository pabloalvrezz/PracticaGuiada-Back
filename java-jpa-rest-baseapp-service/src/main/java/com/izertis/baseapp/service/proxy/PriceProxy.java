package com.izertis.baseapp.service.proxy;

import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;
import com.izertis.baseapp.service.dto.PriceDto;
import com.izertis.baseapp.service.filter.PriceFilter;
import com.izertis.baseapp.service.model.Prices;

public interface PriceProxy extends QueryService<PriceDto, Long, PriceFilter>,
SaveService<PriceDto>, DeleteService<PriceDto, Long>{

    PriceDto save(PriceDto dto, Long productid);

}
