package com.ecom.b2cstore.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DatabaseConfiguration {

    @Bean
    DataSource dataSource() {
        // Using H2 Database
        // Document: https://docs.spring.io/spring-boot/how-to/data-access.html
        DataSourceBuilder<?> dataSource = DataSourceBuilder.create().type(org.h2.jdbcx.JdbcDataSource.class);
        dataSource.driverClassName(org.h2.Driver.class.getName());
        dataSource.url("jdbc:h2:~/b2cstore");
        dataSource.username("sa");
        dataSource.password("123");
        return dataSource.build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.ecom.b2cstore.entity");
        factory.setDataSource(dataSource());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create");
        factory.setJpaPropertyMap(jpaProperties);

        return factory;
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

}
