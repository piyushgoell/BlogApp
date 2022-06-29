package com.piyushgoel.blog.services.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.exceptions.enums.BusinessExceptionType;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.BusinessException;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.User;
import com.piyushgoel.blog.repositories.RoleRepository;
import com.piyushgoel.blog.repositories.UserRepository;
import com.piyushgoel.blog.services.EmailService;
import com.piyushgoel.blog.services.UserService;

@Service
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
	public UserDTO create(UserDTO userDTO, String appURL) {
		User user = this.modelMapper.map(userDTO, User.class);
		user.setRoles(Set.of(this.roleRepository.findByType(RoleType.ROLE_USER)));
		user.setConfirmationToken(UUID.randomUUID());
		user.setEnabled(false);
		user = this.userRepository.save(user);
		
		String subject = "Set your password";
		String message = "To set your password, please click on the link below: \n"
				+ appURL+"/api/auth/confirm?token=" + user.getConfirmationToken();
		this.emailService.sendEmail(user.getEmail(), subject, message);
		
		
		return this.modelMapper.map(user,UserDTO.class);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		User user = this.userRepository.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",userDTO.getId().toString()));
		return this.modelMapper.map(this.userRepository.save(this.modelMapper.map(user, User.class)),UserDTO.class);
	}
	
	public UserDTO update(UUID token , String password, boolean enabled) throws BusinessException {
		System.out.println(password);
		User user  = this.userRepository.findByConfirmationToken(token)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","cofirmation token",token.toString()));
		
		if(user.isEnabled() && user.getConfirmationToken().equals(token)) throw new BusinessException(BusinessExceptionType.EXCEPTION_MESSAGE,"Token is already Used",HttpStatus.IM_USED);
		user.setEnabled(enabled);
		user.setPassword(this.passwordEncoder.encode(password));
		this.userRepository.save(user);
		return null;
	}

	@Override
	public UserDTO getUserById(UUID Id) {
		return this.modelMapper.map(this.userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",Id.toString())),UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return this.userRepository.findAll().stream().map((user)-> this.modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
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
	

}
