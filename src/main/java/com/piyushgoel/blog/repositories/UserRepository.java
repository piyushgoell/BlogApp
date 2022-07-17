package com.piyushgoel.blog.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyushgoel.blog.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, UUID>  {
	public Optional<User> findByEmail(String email);
	public Optional<User> findByConfirmationToken(UUID confirmationToken);
}
