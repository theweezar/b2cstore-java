package com.ecom.b2cstore.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DatabaseConfiguration {

    @Autowired
    Environment env;

    @Bean
    DataSource dataSource() {
        String dbMode = env.getProperty("datasource.mode", "h2");
        String sourceType = env.getProperty("datasource." + dbMode + ".source-type-class-name");
        String driver = env.getProperty("datasource." + dbMode + ".driver-class-name");
        String url = env.getProperty("datasource." + dbMode + ".url");
        String username = env.getProperty("datasource." + dbMode + ".username");
        String password = env.getProperty("datasource." + dbMode + ".password");

        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();

        if (sourceType != null) {
            try {
                dataSource.type((Class<? extends DataSource>) Class.forName(sourceType));
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Invalid datasource type: " + sourceType, e);
            }
        }

        if (driver != null) {
            dataSource.driverClassName(driver);
        }

        dataSource.url(url);
        dataSource.username(username);
        dataSource.password(password);

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
