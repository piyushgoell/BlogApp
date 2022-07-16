package com.piyushgoel.blog.contollers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Sort.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.piyushgoel.blog.dataTransferObject.PostDTO;
import com.piyushgoel.blog.model.User;
import com.piyushgoel.blog.security.annotation.IsAuthorised;
import com.piyushgoel.blog.services.PostService;
import com.piyushgoel.blog.swagger.PostAPI;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PostController implements PostAPI {

	@Autowired
	private PostService postService;

	@Override
	@IsAuthorised
	public ResponseEntity<?> getAllPost(String search, Integer pageNumber, Integer pageSize, List<String> sort, Authentication authentication) {
		log.debug("GetAllPost {} {} {} {}", search, pageNumber, pageSize, sort);
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
				Sort.by(sort.stream()
						.map((s) -> new Order((s.split(";").length > 1 && (s.split(";")[1].equalsIgnoreCase("desc"))
								? Sort.Direction.DESC
								: Sort.Direction.ASC), s.split(";")[0]))
						.collect(Collectors.toList())));
		return ResponseEntity.ok(search == null ? this.postService.getAllPost(pageRequest)
				: this.postService.searchPosts(search, pageRequest));
	}

	@Override
	public ResponseEntity<?> getPostById(UUID Id) {
		return ResponseEntity.ok(this.postService.getPostById(Id));
	}

	@Override
	public ResponseEntity<?> update(UUID Id, PostDTO post) {
		this.postService.update(Id, post);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<?> delete(UUID Id) {
		this.postService.delete(Id);
		return ResponseEntity.accepted().build();
	}

	@Override
	public ResponseEntity<?> uploadImage(UUID Id, MultipartFile image)
			throws Exception {
		this.postService.uploadImage(Id, image);
		return ResponseEntity.noContent().build(); 

	}
	
	@Override
	public ResponseEntity<?> imageReterive(@PathVariable UUID Id, HttpServletResponse response) throws IOException {
		StreamUtils.copy(this.postService.reteriveImage(Id), response.getOutputStream());
		return ResponseEntity.accepted().build();
	}

	@Override
	public ResponseEntity<?> create(@RequestBody PostDTO postDTO, Authentication authentication) {
		this.postService.create(postDTO, (User) authentication.getPrincipal());
		return ResponseEntity.created(null).build();
	}


	@Override
	public ResponseEntity<?> getPostsByCategory(@PathVariable UUID categoryId, Authentication authentication) {
		return ResponseEntity.ok(this.postService.getPostsByCategory(categoryId));
	}

}
