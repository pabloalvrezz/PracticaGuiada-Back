package com.izertis.baseapp.back;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.izertis.baseapp.back.solr.SolrBackConfig;
import com.izertis.baseapp.service.ServiceConfig;


@SpringBootApplication
@EnableAutoConfiguration
@Import({ ServiceConfig.class, SolrBackConfig.class })
@ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "*..solr..*") })
public class Application {
    /**
     * Main method for embedded deployment.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
        
        
    }
}
