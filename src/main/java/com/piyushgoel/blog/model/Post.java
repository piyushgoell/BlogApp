package com.piyushgoel.blog.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="posts")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="id", columnDefinition = "BINARY(16)", updatable=false, nullable=false)
	private UUID id;
	
	@NotBlank
	@Size(min = 4)
	private String title;
	
	@NotBlank
	@Size(min = 10)
	private String content;
	
	private String imagepath;
	
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="post", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comments= new ArrayList<>();

	public Post(String imagepath) {
		super();
		this.imagepath = imagepath;
	}
	
	
		


}
