package com.piyushgoel.blog.config;

import java.util.List;

import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.model.Role;
import com.piyushgoel.blog.repositories.RoleRepository;

@Configuration
public class BlogAppApplicationConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public Validator defaultValidator() {
        return new LocalValidatorFactoryBean();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}
	
	@Autowired
	private RoleRepository roleRepository;
	

	@EventListener
	public void applicationReady(ApplicationReadyEvent event) {
		if (this.roleRepository.findAll().isEmpty()) this.roleRepository.saveAllAndFlush(List.of(new Role(RoleType.ROLE_ADMIN),new Role(RoleType.ROLE_USER)));
	}
	
}
