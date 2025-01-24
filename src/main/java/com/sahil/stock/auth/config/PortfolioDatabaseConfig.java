package com.sahil.stock.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.sahil.stock.auth.repository.portfolio", entityManagerFactoryRef = "portfolioEntityManagerFactory", transactionManagerRef = "portfolioTransactionManager")
public class PortfolioDatabaseConfig {
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

    @Bean(name = "portfolioTransactionManager")
    public PlatformTransactionManager portfolioTransactionManager(
            @Qualifier("portfolioEntityManagerFactory") LocalContainerEntityManagerFactoryBean portfolioEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(portfolioEntityManagerFactory.getObject());
        return transactionManager;
    }
}
