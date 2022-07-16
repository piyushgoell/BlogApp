package com.piyushgoel.blog.swagger;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.piyushgoel.blog.dataTransferObject.CommentDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirements(value = @SecurityRequirement(name = "Bearer Authentication"))
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "The Comments API")
public interface CommentAPI {
	
	@Operation(summary = "Create Comment", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/posts/{postId}")
	default ResponseEntity<?>create(@RequestBody CommentDTO commentDTO, @PathVariable UUID postId,Authentication authentication) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Operation(summary = "Delete Comment", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Deleted", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/post/{postId}/comment/{commentId}")
	default ResponseEntity<?> delete(@PathVariable UUID postId, @PathVariable UUID commentId) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}
