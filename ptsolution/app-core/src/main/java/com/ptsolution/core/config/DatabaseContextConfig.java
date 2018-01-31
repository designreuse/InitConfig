package com.ptsolution.core.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = "com.ptsolution.core.orm")
@EnableTransactionManagement
@PropertySource(value = "classpath:db.properties")
public class DatabaseContextConfig {
	@Autowired private Environment env;
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName(env.getProperty("hibernate.connection.driver.class"));
		driver.setUrl(env.getProperty("hibernate.connection.url"));
		driver.setUsername(env.getProperty("hibernate.connection.username"));
		driver.setPassword(env.getProperty("hibernate.connection.password"));
		
		return driver;
	}
	
	@Bean
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.c3p0.min_size", 10);
		properties.put("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		properties.put("hibernate.c3p0.acquire_increment", env.getProperty("hibernate.c3p0.acquire_increment"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.connection.show_sql"));
		properties.put("hibernate.connection.autocommit", env.getProperty("hibernate.connection.autocommit"));
		properties.put("hibernate.connection.release_mode", env.getProperty("hibernate.connection.release_mode"));
		properties.put("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));
		properties.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		properties.put("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
		
		return properties;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPersistenceUnitName("db_persistence");
		factory.setPackagesToScan("com.ptsolution.core.orm.entity");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaProperties(hibernateProperties());
		return factory;
	}
	
	@Bean
	public JpaTransactionManager jpaTransactionManager(){
		JpaTransactionManager transaction = new JpaTransactionManager();
		transaction.setEntityManagerFactory(entityManagerFactory().getObject());
		return transaction;
	}
}
