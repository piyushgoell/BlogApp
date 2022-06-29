package com.piyushgoel.blog.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piyushgoel.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
