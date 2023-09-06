package com.izertis.baseapp.service.proxy.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.dto.PriceDto;
import com.izertis.baseapp.service.filter.PriceFilter;
import com.izertis.baseapp.service.mapper.PriceMapper;
import com.izertis.baseapp.service.model.Prices;
import com.izertis.baseapp.service.proxy.PriceProxy;
import com.izertis.baseapp.service.service.PriceService;

@Service
public class PriceProxyImpl implements PriceProxy {

    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceMapper priceMapper;

    @Override
    public Optional<PriceDto> find(Long identifier) {
        return this.priceMapper.convertToDto(this.priceService.find(identifier));
    }

    @Override
    public Page<PriceDto> findPaginated(PriceFilter filter, Pageable pageable) {
        return this.priceMapper.convertToDto(this.priceService.findPaginated(filter, pageable));
    }

    /**
     * Devuelve una lista con todos los precios
     */
    @Override
    public List<PriceDto> findAll() {
        return this.priceMapper.convertToDto(this.priceService.findAll());
    }

    @Override
    public PriceDto save(PriceDto entity) {
        Prices price = this.priceMapper.convertFromDto(entity);

        Prices savedPrice = this.priceService.save(price);

        return this.priceMapper.convertToDto(savedPrice);
    }

    @Override
    public List<PriceDto> save(Iterable<PriceDto> entities) {
        return null;
    }

    @Override
    public PriceDto save(PriceDto dto, Long productid) {
        Prices price = this.priceMapper.convertFromDto(dto);

        Prices savedPrice = this.priceService.save(price, productid);

        return this.priceMapper.convertToDto(savedPrice);
    }

    @Override
    public PriceDto update(PriceDto entity) throws NoSuchEntityException {
        final Prices price = this.priceMapper.updateFromDto(entity, this.priceService.find(entity.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format("Product %s not found", entity.getId()))));

        return this.priceMapper.convertToDto(this.priceService.update(price));
    }

    @Override
    public void delete(PriceDto entity) {

    }

    @Override
    public void delete(Long identifier) {

    }

}
