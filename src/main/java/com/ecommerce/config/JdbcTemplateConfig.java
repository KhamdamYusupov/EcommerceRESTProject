package com.ecommerce.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.ecommerce")
public class JdbcTemplateConfig {
    private final DataSource dataSource;

    @Autowired
    public JdbcTemplateConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }
}
