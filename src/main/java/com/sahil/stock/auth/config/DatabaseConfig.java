package com.sahil.stock.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {
    @Primary
    @Bean(name = "authDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.auth")
    public DataSource authDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("authDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sahil.stock.auth.model")
                .build();
    }

    @Bean(name = "portfolioDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.portfolio")
    public DataSource portfolioDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "portfolioEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean portfolioEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("portfolioDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sahil.stock.auth.model")
                .build();
    }
}
