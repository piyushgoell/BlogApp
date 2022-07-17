package com.piyushgoel.blog.services.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.User;
import com.piyushgoel.blog.repositories.RoleRepository;
import com.piyushgoel.blog.repositories.UserRepository;
import com.piyushgoel.blog.services.EmailService;
import com.piyushgoel.blog.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService  {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDTO create(UserDTO userDTO, String confirmationURL) throws MessagingException, IOException {
		User user = this.create(userDTO);
		String accountActivationURL = confirmationURL+"?token=" + user.getConfirmationToken();
		this.emailService.sendAccountActivationEmail(user.getEmail(), accountActivationURL);
		return this.modelMapper.map(user, UserDTO.class);
	}
	
	@Override
	public void update(UUID confirmationToken, String password) throws MessagingException, IOException {
		User user = this.userRepository.findByConfirmationToken(confirmationToken)
		.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","confiramtion token",confirmationToken.toString()));
		
		user.setPassword(this.passwordEncoder.encode(password)); 
		user.setEnabled(true);
		
		this.emailService.sendRegistrationConfirmationEmail(user.getEmail());	
	}
	
	@Override
	public void update(String username, Collection<RoleType> claims) throws MessagingException, IOException {
		User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","email",username));
		claims.forEach((role)-> user.getRoles().add(this.roleRepository.findByType(role)));
//		this.emailService.sendupdatedPrivilegesEmail(user.getEmail());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(UUID userId, UserDTO user) {
		ObjectMapper obj = new ObjectMapper();
		this.update(userId, obj.convertValue(obj, Map.class));
	}
	
	@Override
	public UserDTO getUserById(UUID Id) {
		return this.modelMapper.map(this.userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",Id.toString())),UserDTO.class);
	}
	
	@Override
	public UserDTO getUserByEmail(String email) {
		final User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Email",email));
		return this.modelMapper.map(user, UserDTO.class);
	}
	
	@Override
	public List<UserDTO> getAllUsers(PageRequest pageRequest) {
		return this.userRepository.findAll(pageRequest).stream().map((user)-> this.modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void delete(UUID Id) {	
		this.userRepository.delete(this.userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",Id.toString())));	
	}

	@Override
	public boolean doesEmailExist(String email) {
		return !this.userRepository.findByEmail(email).isEmpty();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","email",username));
	}

	@Override
	public UserDTO getUserByConfirmationToken(UUID confirmationToken) {
		return this.modelMapper.map(
				this.userRepository.findByConfirmationToken(confirmationToken)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","confiramtion token",confirmationToken.toString())),
				UserDTO.class);
	}
	
	
	private User create(UserDTO userDTO){
		log.info("Saving new user to the database {}",userDTO);
		User user = this.modelMapper.map(userDTO, User.class);
		user.setRoles(Set.of(this.roleRepository.findByType(RoleType.ROLE_USER)));
		return this.userRepository.save(user);
	}
	
	private User update(UUID userId, Map<String, Object> object) {
		log.info("User {} records {} updated", userId,object);
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",userId.toString()));
		
		object.forEach((k,v)->{
			if(k == "name") user.setName((String) v);
			if(k == "about") user.setAbout((String) v);
			if(k == "email") user.setEmail((String) v);
		});
		
		return user;
	}
	
	@Override
	public List<UserDTO> searchUsers(List<String> emails, PageRequest pageRequest) {

		return this.userRepository.findAll()
				.stream()
				.map((post)-> this.modelMapper.map(post, UserDTO.class))
				.collect(Collectors.toList());
	}


	

	
}
