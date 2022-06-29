package com.piyushgoel.blog.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.dataTransferObject.PostDTO;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.Post;
import com.piyushgoel.blog.repositories.CategoryRepository;
import com.piyushgoel.blog.repositories.PostRepository;
import com.piyushgoel.blog.repositories.UserRepository;
import com.piyushgoel.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDTO createPost(Post post,UUID userId, UUID categoryId) {
		post.setUser(this.userRepository
					.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",userId.toString())));
		
		post.setCategory(this.categoryRepository
								.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",categoryId.toString())));
		if(post.getImagepath() == null) post.setImagepath("default.png");
		
		return this.modelMapper.map(this.postRepository.save(post),PostDTO.class);
	}

	@Override
	public PostDTO updatePost(Post post, UUID Id) {
		Post oldPost = this.postRepository
				.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString()));
		if(post.getTitle() == null) post.setTitle(oldPost.getTitle());
		if(post.getContent() == null) post.setContent(oldPost.getContent());
		if(post.getId() == null) post.setId(oldPost.getId());
		if(post.getCategory() == null) post.setCategory(oldPost.getCategory());
		if(post.getUser() == null) post.setUser(oldPost.getUser());
		if(post.getImagepath() == null) post.setImagepath("default.png");
		
		return this.modelMapper.map(this.postRepository.save(post),PostDTO.class);
	}

	@Override
	public void deletePost(UUID Id) {
		this.postRepository
				.delete(this.postRepository
								.findById(Id)
								.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString()))
						);
	}

	@Override
	public List<PostDTO> getAllPost(PageRequest pageRequest) {
		
		return this.postRepository
				.findAll(pageRequest)
				.stream()
				.map((post)-> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDTO getPostById(UUID Id) {
		return this.modelMapper.map(this.postRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString())),PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostsByCategory(UUID categoryId) {
		return this.postRepository
				.findByCategory(this.categoryRepository
						.findById(categoryId)
						.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",categoryId.toString())))
				.stream()
				.map((post)-> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getPostByUser(UUID userId) {
		
		return this.postRepository
				.findByUser(this.userRepository
						.findById(userId)
						.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"User","Id",userId.toString())))
				.stream()
				.map((post)-> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> searchPosts(String keyword, PageRequest pageRequest) {

		return this.postRepository.findByTitleContaining(keyword, pageRequest)
				.stream()
				.map((post)-> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
	}

}
