package com.izertis.baseapp.service.solr.indexer;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.izertis.baseapp.service.model.User;
import com.izertis.baseapp.service.repository.UserRepository;
import com.izertis.baseapp.service.solr.mapper.UserSolrMapper;
import com.izertis.baseapp.service.solr.model.UserSolr;
import com.izertis.baseapp.service.solr.repository.UserSolrRepository;
import com.izertis.libraries.solr.autoconfigure.properties.SolrProperties;
import com.izertis.libraries.solr.indexer.impl.AbstractIndexerImpl;

/**
 * Indexer for {@link User}.
 */
@Component
public class UserIndexerImpl extends AbstractIndexerImpl<User, String> {

    /**
     * The user's database repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Solr User repository.
     */
    @Autowired
    private UserSolrRepository solrUserRepository;

    /**
     * MapStruct Mapper for {@link UserSolr}.
     */
    @Autowired
    private UserSolrMapper userSolrMapper;

    /**
     * Solr properties.
     */
    @Autowired
    private SolrProperties solrProperties;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void indexInternal(final Iterable<User> entities) {
        // Index in solr.
        this.solrUserRepository.saveAll(this.userSolrMapper.convertToSolr(entities));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reIndex() {
        int pageNumber = 0;
        Page<User> page;
        int totalPages;

        final Instant startDate = Instant.now();

        // Do the new index
        do {
            page = this.userRepository.findAll(PageRequest.of(pageNumber++, this.solrProperties.getReindexPageSize()));
            totalPages = page.getTotalPages();
            this.index(page.getContent());
        } while (pageNumber < totalPages);

        // Clean deleted documents
        this.solrUserRepository.deleteByIndexDateLessThan(startDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<User> getValidClass() {
        return User.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CrudRepository<User, String> getCrudRepository() {
        return this.userRepository;
    }
}
