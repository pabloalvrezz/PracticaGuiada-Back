package com.izertis.baseapp.service.service;

import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;
import com.izertis.baseapp.service.filter.BrandFilter;
import com.izertis.baseapp.service.model.Brand;

public interface BrandService extends QueryService<Brand, Long, BrandFilter>,
SaveService<Brand>, DeleteService<Brand, Long>{

}
