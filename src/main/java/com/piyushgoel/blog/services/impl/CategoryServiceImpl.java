package com.piyushgoel.blog.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.dataTransferObject.CategoryDTO;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;
import com.piyushgoel.blog.exceptions.impl.ResourceNotFoundException;
import com.piyushgoel.blog.model.Category;
import com.piyushgoel.blog.repositories.CategoryRepository;
import com.piyushgoel.blog.services.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void create(CategoryDTO categoryDTO) {
		this.categoryRepository.save(this.modelMapper.map(categoryDTO, Category.class));
	}

	@Override
	public void update(UUID Id, CategoryDTO categoryDTO) {
		Category category = this.categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",Id.toString()));
		category.setId(Id);
	}

	@Override
	public void delete(UUID Id) {
		
		this.categoryRepository.delete(this.categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",Id.toString())));
	}

	@Override
	public CategoryDTO getCategoryById(UUID Id) {
		return this.modelMapper.map(this.categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundExceptionType.RESOURCE_NOT_FOUND,"Category","Id",Id.toString())),CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories(PageRequest pageRequest) {
		return this.categoryRepository.findAll(pageRequest).stream().map((category)->this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	}

}
