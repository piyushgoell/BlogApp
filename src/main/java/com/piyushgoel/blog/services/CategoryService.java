package com.piyushgoel.blog.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import com.piyushgoel.blog.dataTransferObject.CategoryDTO;


public interface CategoryService {

	void create(CategoryDTO categoryDTO);
	void update( UUID Id, CategoryDTO categoryDTO);
	void delete(UUID Id);
	CategoryDTO getCategoryById(UUID Id);
	List<CategoryDTO> getAllCategories(PageRequest pageRequest);
}
