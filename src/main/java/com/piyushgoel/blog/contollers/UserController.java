package com.piyushgoel.blog.contollers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody UserDTO user, @PathVariable(name = "id") UUID Id) {
			user.setId(Id);
		 this.userService.update(user);
		 return new ResponseEntity<>(Map.of("message", "User Updated Sucessfully"),HttpStatus.OK);
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID Id) {
		 this.userService.delete(Id);
		 return new ResponseEntity<>(Map.of("message", "User Deleted Sucessfully"),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable(name = "id") UUID Id) {
		return this.userService.getUserById(Id);
	}
	
	@GetMapping("/")
	public List<UserDTO> getUsers() {
		return this.userService.getAllUsers();
	}
	
}
