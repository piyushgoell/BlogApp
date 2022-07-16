package com.piyushgoel.blog.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piyushgoel.blog.security.BlogApplicationSecurityUtility;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class BlogApplicationAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BlogApplicationSecurityUtility jwtSecurityUtility;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/auth/login")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					this.jwtSecurityUtility.validateToken(token);
					UserDetails user = this.userDetailsService.loadUserByUsername(this.jwtSecurityUtility.getUsernameFromToken(token));
					if (user.getAuthorities().isEmpty()) throw new Exception("User has no roles");
					UsernamePasswordAuthenticationToken authenticationToken
					= new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);	
				}catch(Exception exception) {
					log.error("Error logging in : {}", exception.getMessage());
					response.setHeader("error", exception.getMessage());
					Map<String, String> errors = Map.of("error",exception.getMessage()		);
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					response.setStatus(FORBIDDEN.value());
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				}
			}
			else {
				filterChain.doFilter(request, response);	
			}

		}

	}

}
