package com.izertis.baseapp.service.proxy;

import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;
import com.izertis.baseapp.service.dto.BrandDto;
import com.izertis.baseapp.service.filter.BrandFilter;

public interface BrandProxy extends QueryService<BrandDto, Long, BrandFilter>,
SaveService<BrandDto>, DeleteService<BrandDto, Long>{
     

}
