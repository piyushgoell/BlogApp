package com.piyushgoel.blog.contollers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piyushgoel.blog.dataTransferObject.CategoryDTO;
import com.piyushgoel.blog.model.Category;
import com.piyushgoel.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/")
	public CategoryDTO createCategory(@Valid @RequestBody Category category) {
		return this.categoryService.createCategory(category);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category, @PathVariable(name = "id") UUID Id) {
		 this.categoryService.updateCategory(category,Id);
		 return new ResponseEntity<>(Map.of("message", "Category Updated Sucessfully"),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id") UUID Id) {
		 this.categoryService.deleteCategory(Id);
		 return new ResponseEntity<>(Map.of("message", "Category Deleted Sucessfully"),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public CategoryDTO getUserById(@PathVariable(name = "id") UUID Id) {
		return this.categoryService.getCategoryById(Id);
	}
	
	@GetMapping("/")
	public List<CategoryDTO> getUsers() {
		return this.categoryService.getAllCategories();
	}
}
