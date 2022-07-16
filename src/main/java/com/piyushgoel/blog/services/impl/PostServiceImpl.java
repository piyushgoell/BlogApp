package com.piyushgoel.blog.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.piyushgoel.blog.dataTransferObject.PostDTO;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.Category;
import com.piyushgoel.blog.model.Post;
import com.piyushgoel.blog.model.User;
import com.piyushgoel.blog.repositories.CategoryRepository;
import com.piyushgoel.blog.repositories.PostRepository;
import com.piyushgoel.blog.repositories.UserRepository;
import com.piyushgoel.blog.services.FileService;
import com.piyushgoel.blog.services.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{
	
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDTO create(PostDTO postDTO,User user) {
		
		Post  post = this.modelMapper.map(postDTO, Post.class);
		post.setUser(user);
		
		//post.setCategory(this.categoryRepository
		//						.findById(categoryId)
		//						.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",categoryId.toString())));
		
		return this.modelMapper.map(this.postRepository.save(post),PostDTO.class);
	}

	@Override
	public PostDTO update(UUID Id, PostDTO post) {
		Post oldPost = this.postRepository
				.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString()));
		
		if(post.getTitle() != null) oldPost.setTitle(post.getTitle());
		if(post.getContent() == null) oldPost.setContent(post.getContent());
		if(post.getCategory() == null) oldPost.setCategory(this.modelMapper.map(post.getCategory(),Category.class));
		if(post.getImagepath() == null) oldPost.setImagepath(post.getImagepath());

		return this.modelMapper.map(oldPost,PostDTO.class);
	}
	
	@Override
	public void uploadImage(UUID Id, MultipartFile image) throws IOException {
		Post post = this.postRepository
				.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString()));
		String ImagePath = this.fileService.uploadImage(path, image);
		post.setImagepath(ImagePath);	
	}
	
	@Override
	public InputStream reteriveImage(UUID Id) throws IOException {
		Post post = this.postRepository
				.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString()));
		return this.fileService.reteriveImage(path, post.getImagepath());
	}
	
	
	@Override
	public void delete(UUID Id) {
		this.postRepository
				.delete(this.postRepository
								.findById(Id)
								.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",Id.toString()))
						);
	}

	@Override
	public List<PostDTO> getAllPost(PageRequest pageRequest) {
		this.postRepository.findAll(null, pageRequest);
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
