package main.java.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan("main.java.dao")
@ComponentScan("main.java.dto")
@ComponentScan("main.java.service")
@ComponentScan("main.java.controller")
@PropertySource("classpath:dao.properties")
public class SpringConfig {

	@Value("${url}")
	String url;
	@Value("${driver}")
	String driver;
	@Value("${userDb}")
	String userDb;
	@Value("${password}")
	String password;
	
	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
								.url(url)
								.driverClassName(driver)
								.username(userDb)
								.password(password)
								.build();
		
	}
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		
		return bean;
	}
}
