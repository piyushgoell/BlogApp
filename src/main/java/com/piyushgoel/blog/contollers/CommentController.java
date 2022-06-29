package com.piyushgoel.blog.contollers;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piyushgoel.blog.dataTransferObject.CommentDTO;
import com.piyushgoel.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postId}/comment")
	public CommentDTO create(@RequestBody CommentDTO comment, @PathVariable UUID postId) {
		return this.commentService.create(comment, postId);
	}
	
	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public ResponseEntity<?> delete(@PathVariable UUID postId, @PathVariable UUID commentId) {
		this.commentService.delete(commentId);
		return new ResponseEntity<>(Map.of("message", "Comment Deleted Sucessfully"),HttpStatus.OK);
	}
}
