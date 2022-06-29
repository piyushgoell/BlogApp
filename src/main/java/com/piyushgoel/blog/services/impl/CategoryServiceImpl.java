package com.piyushgoel.blog.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.dataTransferObject.CategoryDTO;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.Category;
import com.piyushgoel.blog.repositories.CategoryRepository;
import com.piyushgoel.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(Category category) {
		return this.modelMapper.map(this.categoryRepository.save(category),CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(Category category, UUID Id) {
		this.categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",Id.toString()));
		category.setId(Id);
		return this.modelMapper.map(this.categoryRepository.save(category),CategoryDTO.class);
	}

	@Override
	public void deleteCategory(UUID Id) {
		
		this.categoryRepository.delete(this.categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",Id.toString())));
	}

	@Override
	public CategoryDTO getCategoryById(UUID Id) {
		return this.modelMapper.map(this.categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",Id.toString())),CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return this.categoryRepository.findAll().stream().map((category)->this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	}

}
