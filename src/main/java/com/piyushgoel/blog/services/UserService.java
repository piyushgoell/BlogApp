package com.piyushgoel.blog.services;

import java.util.List;
import java.util.UUID;

import com.piyushgoel.blog.dataTransferObject.UserDTO;
import com.piyushgoel.blog.exceptions.impl.BusinessException;

public interface UserService {
	
	UserDTO create(UserDTO user,String appURL);
	UserDTO update(UserDTO user);
	UserDTO update(UUID token , String password, boolean enabled) throws BusinessException;
	UserDTO getUserById(UUID Id);
	List<UserDTO> getAllUsers();
	void delete(UUID Id);
	boolean doesEmailExist(String email);
	UserDTO getUserByConfirmationToken(UUID confirmationToken);
}
