package com.db.sysgob.configuration;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManager",
    transactionManagerRef = "transactionManager",
    basePackages = {"com.db.sysgob.repository"})
public class PersistenceConfig {
  
    @Bean(name = "dataSource")
    public DataSource dataSource(){
    	PGSimpleDataSource datasource = new PGSimpleDataSource();
        datasource.setServerName("localhost");
        datasource.setPortNumber(5432);
    	datasource.setDatabaseName("postgres");
    	datasource.setSsl(false);
    	datasource.setSslfactory("org.postgresql.ssl.NonValidatingFactory");
    	datasource.setUser("postgres");
        datasource.setPassword("bovary18");
        return datasource;
    }

    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalJpaProperties());
        em.setPersistenceUnitName("persistenceUnit");
        em.setPackagesToScan("com.db.sysgob.entity");
        return em;
    }
    
    Properties additionalJpaProperties(){
      Properties properties = new Properties();
      properties.setProperty("hibernate.hbm2ddl.auto", "validate");
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
      properties.setProperty("hibernate.show_sql", "true");
      properties.setProperty("hibernate.cache.use_second_level_cache", "false");
      properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
      properties.setProperty("hibernate.database", "POSTGRESQL");
      return properties;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager);
        
        return transactionManager;
    }
}
