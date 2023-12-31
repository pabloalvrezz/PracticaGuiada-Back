package com.izertis.baseapp.service.config.properties;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * Datasource related configuration propreties.
 */
@Getter
@Setter
@Validated
public class DatasourceProperties {
    /**
     * JDBC driver class name.
     */
    private String driverClassName;
    /**
     * Username for the database.
     */
    private String username;
    /**
     * Password for the database.
     */
    private String password;

    /**
     * URL of the database
     */
    private String url;

    /**
     * JNDI name of the datasource.
     */
    private String jndiName;
}
