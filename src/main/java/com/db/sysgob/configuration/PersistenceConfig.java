package com.db.sysgob.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
      @Value("${jdbc.driver}")
      private String driver;
      
      @Value("${jdbc.url}")
      private String url;
      
      @Value("${jdbc.user}")
      private String user;
      
      @Value("${jdbc.password}")
      private String password;

      @Value("${hibernate.test.query}")
      private String hibernateTestQuery;
      
      @Value("${hibernate.hbm2ddl.auto}")
      private String hibernateDDL;
      
      @Value("${hibernate.dialect}")
      private String hibernateDialect;
      
      @Value("${hibernate.show_sql}")
      private String hibernateShowSqlProperty;
      
      @Value("${hibernate.cache.use_second_level_cache}")
      private String hibernateCacheProperty;
      
      @Value("${hibernate.temp.use_jdbc_metadata_defaults}")
      private String hibernateJDBCMetaData;
  
    @Bean(name = "dataSource")
    public DataSource dataSource(){
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
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

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager);
        return transactionManager;
    }
    
    Properties additionalJpaProperties(){
      Properties properties = new Properties();
      properties.setProperty("hibernate.hbm2ddl.auto", hibernateDDL);
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
      properties.setProperty("hibernate.show_sql", hibernateShowSqlProperty);
      properties.setProperty("hibernate.cache.use_second_level_cache", hibernateCacheProperty);
      properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", hibernateJDBCMetaData);
      return properties;
    }
}
