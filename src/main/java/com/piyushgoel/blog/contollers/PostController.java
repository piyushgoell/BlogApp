package com.piyushgoel.blog.contollers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Sort.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.piyushgoel.blog.config.BlogAppApplicationConstants;
import com.piyushgoel.blog.dataTransferObject.PostDTO;
import com.piyushgoel.blog.model.Post;
import com.piyushgoel.blog.services.FileService;
import com.piyushgoel.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public PostDTO createPost(
			@RequestBody Post post,
			@PathVariable UUID userId,
			@PathVariable UUID categoryId) 
	{
		return this.postService.createPost(post,userId,categoryId);
	}
	
	@GetMapping("/user/{userId}/posts")
	public List<PostDTO> getPostsByUser(@PathVariable UUID userId){
		return this.postService.getPostByUser(userId);
	}
	
	
	@GetMapping("/category/{categoryId}/posts")
	public List<PostDTO> getPostsByCategory(@PathVariable UUID categoryId){
		return this.postService.getPostsByCategory(categoryId);
	}
	
	@GetMapping("/posts")
	public List<PostDTO> getAllPost(
			@RequestParam(value = "search" , required = false) String search,
			@RequestParam(value = "pageNumber", defaultValue = BlogAppApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BlogAppApplicationConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sort", defaultValue=BlogAppApplicationConstants.DEFAULT_EMPTY_STRING, required = false) List<String> sort
			) { 
		System.out.println(search +" "+ sort);
		PageRequest pageRequest = PageRequest.of(
				pageNumber, 
				pageSize,
				Sort.by(
					sort.stream()
						.map((s) -> new Order(
								(s.split(";").length > 1 && (s.split(";")[1].equalsIgnoreCase("desc")) 
										? Sort.Direction.DESC 
										: Sort.Direction.ASC),
								s.split(";")[0])
							)
						.collect(Collectors.toList()))
				);
		return search == null 						
				? this.postService.getAllPost(pageRequest)
				: this.postService.searchPosts(search, pageRequest);
	}
	
	@GetMapping("/posts/{postId}")
	public PostDTO getPostById(@PathVariable UUID postId) {
		return this.postService.getPostById(postId);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable UUID postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<>(Map.of("message", "Post Deleted Sucessfully"),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(
			@RequestBody Post post, 
			@PathVariable UUID postId) 
	{
		this.postService.updatePost(post, postId);
		return new ResponseEntity<>(Map.of("message", "Post Updated Sucessfully"),HttpStatus.OK);
	}
	
	@PutMapping(value = "/posts/{postId}/image/upload")
	public PostDTO imageUpload(
			@PathVariable UUID postId,
			@RequestParam("image") MultipartFile image) throws IOException {
		
		this.postService.getPostById(postId);
		
		return this.postService.updatePost(
				new Post(this.fileService.uploadImage(path, image)),
				postId
			);
		
	}
	
	@GetMapping(value = "/posts/{postId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public void imageReterive(
			@PathVariable UUID postId,
			HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getImage(path, this.postService.getPostById(postId).getImagepath());
		// response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
		
	
}
