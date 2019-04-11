package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("dao")
@ComponentScan("dto")
@ComponentScan("service")
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
}
