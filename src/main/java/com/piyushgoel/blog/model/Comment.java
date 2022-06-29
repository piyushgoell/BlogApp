package com.piyushgoel.blog.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.piyushgoel.blog.enums.ReactionType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="comments")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="id", columnDefinition = "BINARY(16)", updatable=false, nullable=false)
	private UUID id;
	
	private String comment;
	
	@ManyToOne
	private Post post;
	
	@OneToOne
	private User user;
	
	@Enumerated(EnumType.STRING)
	private ReactionType reaction;
	
	public Comment setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	public Comment setPost(Post post) {
		this.post = post;
		return this;
	}
}
