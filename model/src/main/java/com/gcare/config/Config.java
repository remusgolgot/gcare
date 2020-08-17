package com.gcare.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.gcare.dao")
@ComponentScan(basePackages = "com.gcare.model")
@PropertySource(value={"classpath:application.properties"})
@EnableTransactionManagement
public class Config {

    @Value("${gcare.datasource.password}")
    private String datasourcePassword;
    @Value("${gcare.datasource.url}")
    private String datasourceUrl;
    @Value("${gcare.datasource.username}")
    private String datasourceUsername;


    @Bean
    public DataSource dataSource() {

        try {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url(datasourceUrl)
                    .username(datasourceUsername)
                    .password(datasourcePassword)
                    .build();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        // will set the provider to 'org.hibernate.ejb.HibernatePersistence'
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        // will set hibernate.show_sql to 'true'
        vendorAdapter.setShowSql(true);
        // if set to true, will set hibernate.hbm2ddl.auto to 'update'
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.gcare");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(hibernateProperties());

        // This will trigger the creation of the entity manager factory
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "false");// set to false so the real SQL can be seen better

        return properties;
    }
}