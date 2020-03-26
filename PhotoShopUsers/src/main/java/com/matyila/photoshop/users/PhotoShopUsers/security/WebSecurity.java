package com.matyila.photoshop.users.PhotoShopUsers.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.matyila.photoshop.users.PhotoShopUsers.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private UsersService usersService;
	private BCryptPasswordEncoder bcryptEncoder;
	private Environment env;
	
	
	

	@Autowired
	public WebSecurity(UsersService usersService, BCryptPasswordEncoder bcryptEncoder, Environment env) {
		super();
		this.usersService = usersService;
		this.bcryptEncoder = bcryptEncoder;
		this.env = env;
	}

	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/users/**").permitAll()
		.and().addFilter(getAuthFilter());
		http.headers().frameOptions().disable();
		
		
	}

	private Filter getAuthFilter() throws Exception {
		
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService,env,authenticationManager());
		authenticationFilter.setFilterProcessesUrl(env.getProperty("default.login.url"));

		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(usersService).passwordEncoder(bcryptEncoder);
		
	}
	
	


	
	
}
