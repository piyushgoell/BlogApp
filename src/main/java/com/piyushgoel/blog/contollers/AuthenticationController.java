package com.piyushgoel.blog.contollers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.services.UserService;
import com.piyushgoel.blog.swagger.AuthAPI;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthAPI{
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<Void> create(UserDTO user,HttpServletRequest request) throws MalformedURLException, MessagingException,IOException {
		user = this.userService.create(user, request.getRequestURL().append("/activation").toString());
		return ResponseEntity.created(null).build();
	}
	
	@Override
	public ResponseEntity<Void> confirmRegistration(UUID token) {
		this.userService.getUserByConfirmationToken(token);
		return ResponseEntity.ok().build();
		
	}
	
	@Override
	public ResponseEntity<Void> confirmRegistration(UUID token, String password) throws MessagingException,IOException {
			
		this.userService.update(token,password);		
		 return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<Void> updateUserPrivileges(String email, Collection<RoleType> claims) throws MessagingException,IOException{
		this.userService.update(email,claims);
		return ResponseEntity.noContent().build();
	}
}
