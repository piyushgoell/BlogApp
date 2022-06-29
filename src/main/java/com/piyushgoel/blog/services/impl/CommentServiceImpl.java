package com.piyushgoel.blog.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.dataTransferObject.CommentDTO;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.Comment;
import com.piyushgoel.blog.repositories.CommentRepository;
import com.piyushgoel.blog.repositories.PostRepository;
import com.piyushgoel.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO create(CommentDTO comment, UUID postId) {
		System.out.println(comment);
		return this.modelMapper.map(
				this.commentRepository.save(
						this.modelMapper.map(comment,Comment.class)
							.setPost(this.postRepository.findById(postId)
										.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Post","Id",postId.toString())))
				),CommentDTO.class);
		
	}

	@Override
	public void delete(UUID commentId) {
		this.commentRepository.delete(
				this.commentRepository.findById(commentId)
						.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Comment","Id",commentId.toString())));
		
	}

}
