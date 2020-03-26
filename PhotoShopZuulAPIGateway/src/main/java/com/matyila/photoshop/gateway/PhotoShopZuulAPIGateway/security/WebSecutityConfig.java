package com.matyila.photoshop.gateway.PhotoShopZuulAPIGateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecutityConfig extends WebSecurityConfigurerAdapter {

	private Environment env;
	
	@Autowired
	public WebSecutityConfig(Environment env) {
		
		this.env = env;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("THE URRRRLLLL"+env.getProperty("login.url"));
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST,env.getProperty("login.url")).permitAll()
			.antMatchers(HttpMethod.POST,env.getProperty("zuul.url.actuator.path")).permitAll()
			.antMatchers(HttpMethod.POST,env.getProperty("registration.url")).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new AuthorizationFilter(authenticationManager(),env));
		
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	}

	
	
}
