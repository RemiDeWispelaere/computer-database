package com.rdewispelaere.config;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.rdewispelaere.model.Computer;
import com.rdewispelaere.model.Company;

@PropertySource("classpath:dao.properties")
@ComponentScan(
		basePackages = {"com.rdewispelaere.dao",
						"com.rdewispelaere.dto",
						"com.rdewispelaere.service",
						"com.rdewispelaere.controller"}, 
		excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
public class DaoConfig {

	@Value("${url}")
	String url;
	@Value("${driver}")
	String driver;
	@Value("${userDb}")
	String userDb;
	@Value("${password}")
	String password;
	
	@Bean
	public SessionFactory getSessionFactory() {
		
		Map<String, Object> settings = new HashMap<>();
		settings.put(Environment.DRIVER, driver);
		settings.put(Environment.URL, url);
		settings.put(Environment.USER, userDb);
		settings.put(Environment.PASS, password);
		
		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		registryBuilder.applySettings(settings);
		
		StandardServiceRegistry registry = registryBuilder.build();
		
		MetadataSources sources = new MetadataSources(registry)
				.addAnnotatedClass(Computer.class)
				.addAnnotatedClass(Company.class);
		
		Metadata metadata = sources.getMetadataBuilder().build();
		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		
		return sessionFactory;
	}
	
	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
