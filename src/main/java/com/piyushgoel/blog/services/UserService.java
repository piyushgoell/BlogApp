package com.piyushgoel.blog.services;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;

import org.springframework.data.domain.PageRequest;

import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.enums.RoleType;

public interface UserService {
	
	UserDTO create(UserDTO user, String confirmationURL) throws MessagingException, IOException;
	void update(UUID confirmationToken, String password) throws MessagingException, IOException;
	void update(String username, Collection<RoleType> claims) throws MessagingException, IOException;
	void update(UUID userId, UserDTO user);
	void delete(UUID Id);
	UserDTO getUserById(UUID Id);
	UserDTO getUserByEmail(String email);
	List<UserDTO> getAllUsers(PageRequest pageRequest);
	List<UserDTO> searchUsers(String keyword, PageRequest pageRequest);
	
	
	
	UserDTO getUserByConfirmationToken(UUID confirmationToken);
	boolean doesEmailExist(String email);
	
}
