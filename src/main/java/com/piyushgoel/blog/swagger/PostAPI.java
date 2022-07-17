package com.piyushgoel.blog.swagger;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.piyushgoel.blog.config.BlogAppApplicationConstants;
import com.piyushgoel.blog.dataTransferObject.PostDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirements(value = @SecurityRequirement(name = "Bearer Authentication"))
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "The Posts API")
public interface PostAPI {
	
	
	@Operation(summary = "Search Posts", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PostDTO.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/")
	default ResponseEntity<?> getAllPost(
			@RequestParam(value = "search",defaultValue="", required = false) String search,
			@RequestParam(value = "pageNumber", defaultValue = BlogAppApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BlogAppApplicationConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sort", defaultValue=BlogAppApplicationConstants.DEFAULT_EMPTY_STRING, required = false) List<String> sort,
			Authentication authentication
			) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Operation(summary = "Get Post By Id", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/{postId}")
	default ResponseEntity<?> getPostById(@PathVariable(name = "postId", required = true) UUID Id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Operation(summary = "Update Post", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Updated", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/{postId}")
	default ResponseEntity<?> update(@PathVariable(name = "postId", required = true) UUID Id , @RequestBody PostDTO post) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	
	@Operation(summary = "Delete Post", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Deleted", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/{postId}")
	default ResponseEntity<?> delete(@PathVariable(name = "postId", required = true) UUID Id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Operation(summary = "Upload Post Image", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Uploaded", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping(value = "/{postId}/image/upload")
	default ResponseEntity<?> uploadImage(@PathVariable(name = "postId", required = true) UUID Id, @RequestParam("image") MultipartFile image)
			throws Exception {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
	}
	
	@Operation(summary = "Reterive Post Image", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Successful", content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping(value = "/{postId}/image/reterive", produces = MediaType.IMAGE_JPEG_VALUE)
	default ResponseEntity<?> imageReterive(@PathVariable UUID Id, HttpServletResponse response) throws IOException {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Operation(summary = "Create Post", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/")
	default ResponseEntity<?> create(@RequestBody PostDTO postDTO, Authentication authentication) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Operation(summary = "Get Post By categoryId", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/category/{categoryId}")
	default ResponseEntity<?> getPostsByCategory(@PathVariable UUID categoryId, Authentication authentication) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
}
