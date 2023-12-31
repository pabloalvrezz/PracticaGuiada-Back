package com.izertis.baseapp.service.proxy.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.izertis.abstractions.exception.NoSuchEntityException;
import com.izertis.baseapp.service.dto.ProductDto;
import com.izertis.baseapp.service.dto.UserDto;
import com.izertis.baseapp.service.filter.UserFilter;
import com.izertis.baseapp.service.mapper.ProductMapper;
import com.izertis.baseapp.service.mapper.UserMapper;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.model.User;
import com.izertis.baseapp.service.proxy.UserProxy;
import com.izertis.baseapp.service.service.ProductService;
import com.izertis.baseapp.service.service.UserService;

/**
 * Proxy service implementation for {@link User}. Performs DTO conversion and permission checks.
 */
@Service
public class UserProxyImpl implements UserProxy {

    /**
     * Service layer.
     */
    @Autowired
    private UserService service;

    /**
     * DTO to entity mapper.
     */
    @Autowired
    private UserMapper mapper;
    
    /**
     * ProductDto to entity mapper
     */
    @Autowired
    private ProductMapper productMapper;

    /**
     * Product service layer
     */
    @Autowired
    private ProductService productService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserDto> find(final String identifier) {
       Optional<User> optional =  this.service.find(identifier);
       return this.mapper.convertToDto(optional);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UserDto> findPaginated(final UserFilter filter, final Pageable pageable) {
        return this.mapper.convertToDto(this.service.findPaginated(filter, pageable));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> findAll() {
        return this.mapper.convertToDto(this.service.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto save(final UserDto entity) {
        return this.mapper.convertToDto(this.service.save(this.mapper.convertFromDto(entity)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> save(final Iterable<UserDto> entities) {
        return this.mapper.convertToDto(this.service.save(this.mapper.convertFromDto(entities)));
    }
    
    /**
     * {@inheritDoc}
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto update(final UserDto entity) throws NoSuchEntityException {
        final User user = this.mapper.updateFromDto(entity, this.service.find(entity.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format("User %s not found", entity.getId()))));
        return this.mapper.convertToDto(this.service.update(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final UserDto entity) {
        this.service.delete(this.mapper.convertFromDto(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String identifier) {
        this.service.delete(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undelete(final String identifier) {
        this.service.undelete(identifier);
    }


}
