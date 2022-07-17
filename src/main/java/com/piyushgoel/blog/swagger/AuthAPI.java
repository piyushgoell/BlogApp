package com.piyushgoel.blog.swagger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.piyushgoel.blog.dataTransferObject.Auth;
import com.piyushgoel.blog.dataTransferObject.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "The Auth API")
public interface AuthAPI {
	
	@Operation(summary = "Login User", description = "Login User")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Ok", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = Auth.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	@PostMapping(value = "/login", consumes = { "application/x-www-form-urlencoded" })
	default ResponseEntity<?> login(@Parameter(required=true) @RequestPart(value="username", required=true)  String username, @Parameter( required=true) @RequestPart(value="password", required=true)  String password ){
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	
	
	@Operation(summary = "Register User", description = "Create New User")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	@PostMapping(value = "/register", consumes = { "application/json", "application/xml" })
	default ResponseEntity<Void> create(@Valid @RequestBody UserDTO user,HttpServletRequest request) throws MalformedURLException, MessagingException,IOException {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	
	}
	
	@Operation(summary = "User Account Status", description = "Check the user account status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/register/activate")
	default ResponseEntity<Void> confirmRegistration(@RequestParam("token") UUID token) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
	}
	
	
	@Operation(summary = "Activate User Account", description = "Activates User Account & Set Password")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Activated", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/register/activate")
	default ResponseEntity<Void> confirmRegistration(@RequestParam("token") UUID token, @RequestParam("password") String password) throws MessagingException,IOException {
		
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	


}
