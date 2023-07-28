package com.izertis.baseapp.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.izertis.baseapp.service.repository.RepositoryConfig;
import com.izertis.baseapp.service.solr.SolrConfig;

/**
 * Service Spring configuration.
 */
@Configuration
@ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "*..solr..*") })
@Import({RepositoryConfig.class, SolrConfig.class})
public class ServiceConfig {

}
