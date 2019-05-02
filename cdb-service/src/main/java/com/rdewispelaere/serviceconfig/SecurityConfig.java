package com.rdewispelaere.serviceconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String SQL_USER_BY_NAME = "SELECT name, password FROM user WHERE name = :name";
	private static final String SQL_ROLE_BY_NAME = "SELECT username, role FROM role WHERE username = :username";

	@Autowired
	DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(SQL_USER_BY_NAME)
			.authoritiesByUsernameQuery(SQL_ROLE_BY_NAME);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.and()
		  .formLogin().loginPage("/login").failureUrl("/login?error")
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  .logout().logoutSuccessUrl("/login?logout")
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf();
	}
}
