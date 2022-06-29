package com.piyushgoel.blog.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import com.piyushgoel.blog.dataTransferObject.PostDTO;
import com.piyushgoel.blog.model.Post;

public interface PostService {
	PostDTO createPost(Post post, UUID userId, UUID categoryId);
	PostDTO updatePost(Post post, UUID Id);
	void deletePost(UUID Id);
	List<PostDTO> getAllPost(PageRequest pageRequest);
	PostDTO getPostById(UUID Id);
	
	List<PostDTO> getPostsByCategory(UUID categoryId);
	List<PostDTO> getPostByUser(UUID userId);
	
	List<PostDTO> searchPosts(String Keyword, PageRequest pageRequest);
	
	
}
