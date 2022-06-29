package com.piyushgoel.blog.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.piyushgoel.blog.model.Category;
import com.piyushgoel.blog.model.Post;
import com.piyushgoel.blog.model.User;

public interface PostRepository extends JpaRepository<Post, UUID>{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title, PageRequest pagerequest);
}
