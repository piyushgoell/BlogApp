package com.piyushgoel.blog.contollers;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.exceptions.enums.BusinessExceptionType;
import com.piyushgoel.blog.exceptions.impl.BusinessException;
import com.piyushgoel.blog.security.JWTUtility;
import com.piyushgoel.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody Map<String,String> request) throws Exception, BusinessException{
		
		// System.out.println(" *********** "+request.get("username")+" " + request.get("password"));
		
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.get("username"), 
							request.get("password")
							)
					);
			
			return new ResponseEntity<>(Map.of("jwt",this.jwtUtility
								.generateToken(
										this.userDetailsService.loadUserByUsername(request.get("username")))), HttpStatus.OK);

			
		} catch (BadCredentialsException ex) {
			throw new BusinessException(BusinessExceptionType.EXCEPTION_MESSAGE,HttpStatus.UNAUTHORIZED);
			
		}
		
	}
	

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/register")
	public UserDTO create(@Valid @RequestBody UserDTO user,HttpServletRequest request) {
		return this.userService.create(user, request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort());
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/confirm")
	public UserDTO confirmRegistration(@RequestParam("token") UUID token) {
		return this.userService.getUserByConfirmationToken(token);
		
	}
	
	@PostMapping("/confirm")
	public ResponseEntity<?> confirmRegistration(@RequestParam("token") UUID token, @RequestParam("password") String password) throws Exception {
		 this.userService.update(token, password, true);
		 return new ResponseEntity<>(Map.of("message", "User Activated Sucessfully"),HttpStatus.OK);
	}
}
