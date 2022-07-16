package com.piyushgoel.blog.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.piyushgoel.blog.dataTransferObject.PostDTO;
import com.piyushgoel.blog.model.User;

public interface PostService {
	PostDTO create(PostDTO postDTO, User user);
	PostDTO update(UUID Id, PostDTO post);
	void delete(UUID Id);
	
	void uploadImage(UUID Id, MultipartFile image) throws IOException;
	InputStream reteriveImage(UUID Id) throws IOException;
	List<PostDTO> getAllPost(PageRequest pageRequest);
	PostDTO getPostById(UUID Id);
	
	List<PostDTO> getPostsByCategory(UUID categoryId);
	List<PostDTO> getPostByUser(UUID userId);
	
	List<PostDTO> searchPosts(String Keyword, PageRequest pageRequest);
	
	
}
