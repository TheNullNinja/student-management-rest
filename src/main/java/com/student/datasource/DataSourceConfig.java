package com.student.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "studentDataSourceProperties")
    @ConfigurationProperties("spring.datasource.student")
    DataSourceProperties studentDataSourceProperties() {
		return new DataSourceProperties();
	}
    
    @Primary
    @Bean(name = "studentDataSource")
    DataSource studentDataSource(@Qualifier("studentDataSourceProperties") DataSourceProperties dataSourceProperties) {
    	return dataSourceProperties.initializeDataSourceBuilder().build();
    }
    
    @Primary
    @Bean(name = "studentJdbcTemplate")
    JdbcTemplate studentJdbcTemplate(@Qualifier("studentDataSource") DataSource dataSource) {
    	return new JdbcTemplate(dataSource);
    }

	/*
	 * Configure for multiple data source
	 */

}
