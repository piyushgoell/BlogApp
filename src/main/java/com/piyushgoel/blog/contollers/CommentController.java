package com.piyushgoel.blog.contollers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.piyushgoel.blog.dataTransferObject.CommentDTO;
import com.piyushgoel.blog.model.User;
import com.piyushgoel.blog.security.annotation.IsAuthorised;
import com.piyushgoel.blog.services.CommentService;
import com.piyushgoel.blog.swagger.CommentAPI;

@RestController
public class CommentController implements CommentAPI{
	
	@Autowired
	private CommentService commentService;
	
	@Override
	@IsAuthorised
	public ResponseEntity<?> create(CommentDTO commentDTO ,UUID postId, Authentication authentication) {
		this.commentService.create(commentDTO, postId, (User) authentication.getPrincipal());
		return ResponseEntity.created(null).build(); 
	}
	
	@Override
	@IsAuthorised
	public ResponseEntity<?> delete(UUID postId,UUID commentId) {
		this.commentService.delete(commentId);
		return ResponseEntity.accepted().build();
	}
}
