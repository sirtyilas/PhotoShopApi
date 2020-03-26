package com.matyila.photoshop.users.PhotoShopUsers.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matyila.photoshop.users.PhotoShopUsers.model.LoginRequestModel;
import com.matyila.photoshop.users.PhotoShopUsers.service.UsersService;
import com.matyila.photoshop.users.PhotoShopUsers.shared.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UsersService userService;
	private Environment env;
	
	

	@Autowired
	public AuthenticationFilter(UsersService userService, Environment env, AuthenticationManager authenticationManager) {
		
		super.setAuthenticationManager(authenticationManager);
		this.userService = userService;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
			String username  = ((User)authResult.getPrincipal()).getUsername();
			UserDto byUserName = userService.getByUserName(username);
			
			String token = Jwts.builder()
					.setSubject(byUserName.getUserId())
					.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration.time"))))
					.signWith(SignatureAlgorithm.HS512,  env.getProperty("token.secret"))
					.compact();
					
			response.addHeader("token", token);
			response.addHeader("userId", byUserName.getUserId());
			
		
		
	}
	
	
	
	

}
