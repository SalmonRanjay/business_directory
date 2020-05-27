package com.ranjay.directory.config;

import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * DataConfig
 */
@Configuration
public class DataConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataConfig.class);

    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.implicit_naming_strategy}")
    private String hibernateNamingStrategy;
    @Value("${hibernate.format_sql}")
    private String hibernateFomatSQL;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSQL;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateDDL;

    @Value("${stripe.domain.package}")
    private String domainPackages;

    // @Bean(name = "datasource")
    // public DataSource datasSource() {
    // BasicDataSource ds = new BasicDataSource();
    // ds.setDriverClassName(driverClassName);
    // ds.setUrl(url);
    // ds.setUsername(username);
    // ds.setPassword(password);
    // System.out.println(ds.getPassword());
    // return ds;
    // }
    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.implicit_naming_strategy", hibernateNamingStrategy);
        properties.put("hibernate.format_sql", hibernateFomatSQL);
        properties.put("hibernate.show_sql", hibernateShowSQL);
        properties.put("hibernate.hbm2ddl.auto", hibernateDDL);
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws URISyntaxException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.ranjay.directory.model");
        // factory.setDataSource(dataSource());
        factory.setDataSource(dataSource());
        factory.setJpaProperties(getHibernateProperties());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}