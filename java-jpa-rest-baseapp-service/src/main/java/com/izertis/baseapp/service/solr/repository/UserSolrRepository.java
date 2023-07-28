package com.izertis.baseapp.service.solr.repository;

import java.time.Instant;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.izertis.baseapp.service.solr.model.UserSolr;

/**
 * Spring Data Solr repository for {@link UserSolr}
 */
public interface UserSolrRepository extends SolrCrudRepository<UserSolr, String>, UserSolrRepositoryCustom {
    /**
     * Delete all documents older than an specific date.
     *
     * @param indexDate
     *            Date.
     */
    void deleteByIndexDateLessThan(Instant indexDate);
}
