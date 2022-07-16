package com.piyushgoel.blog.services;

import java.util.UUID;

import com.piyushgoel.blog.dataTransferObject.CommentDTO;
import com.piyushgoel.blog.model.User;

public interface CommentService {
	
	void create(CommentDTO comment, UUID postId, User user);	
	void delete(UUID commentId);
}
