package com.izertis.baseapp.service.solr.repository;

import com.izertis.baseapp.service.filter.UserFilter;
import com.izertis.baseapp.service.solr.model.UserSolr;
import com.izertis.libraries.solr.query.FilteredQueryRepository;

/**
 * Custom Spring Data Solr repository for {@link UserSolr}
 */
public interface UserSolrRepositoryCustom extends FilteredQueryRepository<UserFilter, UserSolr> {

}
