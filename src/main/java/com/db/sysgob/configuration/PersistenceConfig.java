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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
/*      @Value("${jdbc.driver}")
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
      private String hibernateJDBCMetaData; */
  
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://srv54.hosting24.com:3306/sepherot_DSC7_BD_Landy?autoReconnect=true");
        hikariConfig.setUsername("sepherot_DSC11");
        hikariConfig.setPassword("vr{#Xh&lvhfl");
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
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
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
      properties.setProperty("hibernate.show_sql", "true");
      properties.setProperty("hibernate.cache.use_second_level_cache", "false");
      properties.setProperty("org.hibernate.envers.audit_table_suffix", "_log");
      properties.setProperty("org.hibernate.envers.revision_field_name", "revision");
      properties.setProperty("org.hibernate.envers.revision_type_field_name", "revision_type");
      properties.setProperty("org.hibernate.envers.store_data_at_delete", "true");
      properties.setProperty("hibernate.ejb.naming_strategy","org.hibernate.cfg.ImprovedNamingStrategy");
      properties.setProperty("hibernate.database", "MYSQL");
      return properties;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager);
        
        return transactionManager;
    }
}
