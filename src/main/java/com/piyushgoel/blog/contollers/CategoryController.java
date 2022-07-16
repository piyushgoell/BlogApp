package com.piyushgoel.blog.contollers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.piyushgoel.blog.dataTransferObject.CategoryDTO;
import com.piyushgoel.blog.services.CategoryService;
import com.piyushgoel.blog.swagger.CategoryAPI;

@RestController
public class CategoryController implements CategoryAPI {
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public ResponseEntity<?> create(CategoryDTO categoryDTO) {
		this.categoryService.create(categoryDTO);
		return ResponseEntity.created(null).build(); 
	}
	
	@Override
	public ResponseEntity<?> update(UUID Id,CategoryDTO categoryDTO) {
		 this.categoryService.update(Id, categoryDTO);
		 return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<?> delete(UUID Id) {
		 this.categoryService.delete(Id);
		 return ResponseEntity.accepted().build();
	}
	
	@Override
	public ResponseEntity<?> getCategoryById(UUID Id) {
		return ResponseEntity.ok(this.categoryService.getCategoryById(Id));
	}
	
	@Override
	public ResponseEntity<?> getCategories(String search,Integer pageNumber, Integer pageSize, List<String> sort) {
		
		List<Order> sort1 = sort.stream()
				.map((s) -> new Order(
						(s.split(";").length > 1 && (s.split(";")[1].equalsIgnoreCase(Sort.Direction.DESC.name())) 
								? Sort.Direction.DESC 
								: Sort.Direction.ASC),
						s.split(";")[0])
					)
				.collect(Collectors.toList());
		
		PageRequest pageRequest = PageRequest.of(
				pageNumber, 
				pageSize,
				Sort.by(sort1));
		
		return ResponseEntity.ok(this.categoryService.getAllCategories(pageRequest));
	}
}
