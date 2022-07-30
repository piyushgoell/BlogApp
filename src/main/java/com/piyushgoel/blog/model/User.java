package com.piyushgoel.blog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.piyushgoel.blog.validators.NotDuplicateEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ID")
	@GenericGenerator(name="ID", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="id", updatable=false, nullable=false, unique = true)
	private UUID id;
	
	@NotEmpty(message="Name must be min of 6 characters")
	@Size(min=4, message="Name must be min of 6 characters")
	@Column(name="user_name", nullable = false)
	private String name;
	
	@NotEmpty(message="Email address is not valid!!")
	@NotNull(message="Email address is not valid!!")
	@Email(message="Email address is not valid!!")
	@NotDuplicateEmail
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name="password")
	private String password;
	
	@NotEmpty(message="Please Provide short description about yourself")
	@Column(name="about")
	private String about;
	   
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
	@Column(name="confirmation_token", updatable=false, nullable=false, unique = true)
	private UUID confirmationToken = UUID.randomUUID();
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about=" + about
				+ ", roles=" + roles + ", confirmationToken=" + confirmationToken + ", enabled="
				+ enabled + "]";
	}
	
	
	
	
}
