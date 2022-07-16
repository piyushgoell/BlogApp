package com.piyushgoel.blog.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.model.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>  {
	public Role findByType(RoleType type);
}
