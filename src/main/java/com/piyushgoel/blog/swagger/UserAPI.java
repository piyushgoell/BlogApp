package com.piyushgoel.blog.swagger;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.piyushgoel.blog.config.BlogAppApplicationConstants;
import com.piyushgoel.blog.dataTransferObject.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirements(value = @SecurityRequirement(name = "Bearer Authentication"))
@RequestMapping("/api/users")
@Tag(name = "Users", description = "The Users API")
public interface UserAPI {

	@Operation(summary = "Get User By Id", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/{id}")
	default ResponseEntity<?> getUserById(@PathVariable(name = "id", required = true) UUID Id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Operation(summary = "Update Users", description = "")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "204", description = "Updated", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/{id}")
	default ResponseEntity<Void> update(@PathVariable(name = "id", required = true) UUID Id,
			@Valid @RequestBody UserDTO user) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Operation(summary = "Delete User", description = "")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Deleted", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/{id}")
	default ResponseEntity<Void> delete(@PathVariable(name = "id", required = true) UUID Id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Operation(summary = "Get Users", description = "")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/")
	default ResponseEntity<?> getUsers(
			@Parameter(example = "piyushgoel.in@gmail.com") @RequestParam(value = "search" , required = false) String search,
			@RequestParam(value = "pageNumber", defaultValue = BlogAppApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BlogAppApplicationConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sort", defaultValue=BlogAppApplicationConstants.DEFAULT_EMPTY_STRING, required = false ) List<String> sort
			) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
}
