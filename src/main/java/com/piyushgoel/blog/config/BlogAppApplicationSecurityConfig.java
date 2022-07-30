package com.piyushgoel.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.piyushgoel.blog.security.BlogApplicationSecurityUtility;
import com.piyushgoel.blog.security.filter.BlogApplicationAuthenticationFilter;
import com.piyushgoel.blog.security.filter.BlogApplicationAuthorizationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@SecurityScheme(name = "Bearer Authentication",type = SecuritySchemeType.HTTP,bearerFormat = "JWT",scheme = "bearer")
public class BlogAppApplicationSecurityConfig{
	
	@Autowired
	private final UserDetailsService userDetailsService;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private BlogApplicationAuthorizationFilter blogApplicationAutheAuthorizationFilter;
	
	@Autowired
	private BlogApplicationSecurityUtility jwtSecurityUtility;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	
    	http
    		.requiresChannel()
    		.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
    		.requiresSecure();
    	
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/**").permitAll()
				.antMatchers("/api/auth/register/**").permitAll()
				.antMatchers("/api/auth/updatePrivileges").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.exceptionHandling()				 
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        AuthenticationManager authenticationManager = getAuthenticationManager(http.getSharedObject(AuthenticationManagerBuilder.class));

        http.addFilter(getBlogApplicationAuthenticationFilter(authenticationManager))
                .authenticationManager(authenticationManager);

        http.addFilterBefore(this.blogApplicationAutheAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
	
	protected BlogApplicationAuthenticationFilter getBlogApplicationAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
		
		BlogApplicationAuthenticationFilter authenticationFilter = new BlogApplicationAuthenticationFilter(authenticationManager,this.jwtSecurityUtility);
		authenticationFilter.setFilterProcessesUrl("/api/auth/login");
        return authenticationFilter;
    }
	
	
	

}
