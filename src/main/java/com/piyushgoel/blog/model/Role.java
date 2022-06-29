package com.piyushgoel.blog.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import com.piyushgoel.blog.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="id", columnDefinition = "BINARY(16)", updatable=false, nullable=false)
	private UUID id;
	
	@Column(name="type",unique = true, updatable = false, nullable = false)
	private RoleType type;
	
	@ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet();
	
	public Role(RoleType type) {
		this.type = type;
	}

	@Override
	public String getAuthority() {
		return this.type.toString();
	}
}
