package com.piyushgoel.blog.security.filter;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

import com.piyushgoel.blog.dataTransferObject.Auth;
import com.piyushgoel.blog.model.User;
import com.piyushgoel.blog.security.BlogApplicationSecurityUtility;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BlogApplicationAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	private final BlogApplicationSecurityUtility jwtSecurityUtility;

	public BlogApplicationAuthenticationFilter(AuthenticationManager authenticationManager, BlogApplicationSecurityUtility jwtSecurityUtility) {
		this.authenticationManager = authenticationManager;
		this.jwtSecurityUtility = jwtSecurityUtility;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordAuthenticationToken 
			authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return this.authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {	
		Auth tokens = this.jwtSecurityUtility.generateToken((User) authentication.getPrincipal(), request.getRequestURL().toString());
		log.info("Access Granted {}", tokens);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	}
	
	
	
	
}
