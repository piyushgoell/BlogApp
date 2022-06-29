package com.piyushgoel.blog.services;

import java.util.List;
import java.util.UUID;

import com.piyushgoel.blog.dataTransferObject.CategoryDTO;
import com.piyushgoel.blog.model.Category;


public interface CategoryService {

	CategoryDTO createCategory(Category category);
	CategoryDTO updateCategory(Category category, UUID Id);
	void deleteCategory(UUID Id);
	CategoryDTO getCategoryById(UUID Id);
	List<CategoryDTO> getAllCategories();
}
