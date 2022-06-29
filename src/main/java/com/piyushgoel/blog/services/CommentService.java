package com.piyushgoel.blog.services;

import java.util.UUID;

import com.piyushgoel.blog.dataTransferObject.CommentDTO;

public interface CommentService {
	
	public CommentDTO create(CommentDTO comment, UUID postId);	
	void delete(UUID commentId);
}
