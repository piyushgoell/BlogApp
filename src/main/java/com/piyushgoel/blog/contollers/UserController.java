package com.piyushgoel.blog.contollers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.security.annotation.IsAdmin;
import com.piyushgoel.blog.security.annotation.IsAuthorised;
import com.piyushgoel.blog.services.UserService;
import com.piyushgoel.blog.swagger.UserAPI;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController implements UserAPI {

	@Autowired
	private UserService userService;
	
	
	@Override
	@IsAuthorised
	public ResponseEntity<?> getUserById(UUID Id) {
		final Object user = this.userService.getUserById(Id);
		return ResponseEntity.ok(user);
	}
	
	@Override
	@IsAuthorised
	public ResponseEntity<Void> update(UUID Id, UserDTO user){
		log.info("user {} body {}", Id, user);
		 this.userService.update(Id,user);
		 return ResponseEntity.noContent().build();
	}
	
	@Override
	@IsAuthorised
	public ResponseEntity<Void> delete(UUID Id) {
		 this.userService.delete(Id);
		 return ResponseEntity.accepted().build();
	}
	
	
	
	@Override
	@IsAdmin
	public ResponseEntity<?> getUsers(List<String> emails,Integer pageNumber, Integer pageSize,List<String> sort, Authentication authentication) 
	{
		
		PageRequest pageRequest = PageRequest.of(
				pageNumber, 
				pageSize,
				Sort.by(
					sort.stream()
						.map((s) -> new Order(
								(s.split(";").length > 1 && (s.split(";")[1].equalsIgnoreCase("desc")) 
										? Sort.Direction.DESC 
										: Sort.Direction.ASC),
								s.split(";")[0])
							)
						.collect(Collectors.toList()))
				);
		return ResponseEntity.ok(emails.isEmpty()					
				? this.userService.getAllUsers(pageRequest)
				: this.userService.searchUsers(emails, pageRequest));
	
	}
	
	@Override
	@IsAdmin
	public ResponseEntity<Void> updateUserPrivileges(String email, Collection<RoleType> claims) throws MessagingException,IOException{
		this.userService.update(email,claims);
		return ResponseEntity.noContent().build();
	}
	
	
}
