package com.izertis.baseapp.service.solr.mapper.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.model.User;
import com.izertis.baseapp.service.solr.mapper.UserSolrMapper;
import com.izertis.baseapp.service.solr.model.UserSolr;

/**
 * MapStruct Mapper decorator for {@link UserSolr}.
 */
public abstract class UserSolrMapperDecorator implements UserSolrMapper {

    /**
     * Delegate {@link User} mapper.
     */
    @Autowired
    @Qualifier("delegate")
    private UserSolrMapper delegate;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageImplHelper<User> convertFromSolr(final Page<UserSolr> page) {
        if (page == null) {
            return null;
        }

        return new PageImplHelper<>(this.delegate.convertFromSolr(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
    }
}
