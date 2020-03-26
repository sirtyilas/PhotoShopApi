package com.matyila.photoshop.gateway.PhotoShopZuulAPIGateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	private Environment env;
	
	
	

	@Autowired
	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment env) {
		super(authenticationManager);
		this.env = env;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = request.getHeader(env.getProperty("authorization.token.header.name"));
		if(token ==null || !token.startsWith(env.getProperty("authorization.token.header.prefix"))){
			chain.doFilter(request, response);		
			return;
		}
		UsernamePasswordAuthenticationToken authenticationToken = getAuthToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthToken(HttpServletRequest request) {
		
		String token = request.getHeader(env.getProperty("authorization.token.header.name"));
		String userIDHeader = request.getHeader("userId");
		
		token = token.replace(env.getProperty("authorization.token.header.prefix"), "");
		
		if(token ==null)
			return null;
		
		String userId = Jwts.parser()
				.setSigningKey(env.getProperty("token.secret"))
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		if(userId == null && userIDHeader == userId) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(userId, null,new ArrayList<>());
	}
	

}
