package com.piyushgoel.blog.dataTransferObject;

import lombok.NoArgsConstructor;

import java.util.UUID;

import com.piyushgoel.blog.enums.ReactionType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {
	
	private UUID id;
	private String comment;
	private ReactionType reaction;
	private UserDTO user;
}
