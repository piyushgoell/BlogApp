package com.piyushgoel.blog.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyushgoel.blog.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
