package com.piyushgoel.blog.dataTransferObject;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
	
	private UUID id;
	private String title;
	private String content;
	private String imagepath = "default.png";
	private Timestamp timestamp;
	private CategoryDTO Category;
	private UserDTO User;
	private List<CommentDTO> comments;
}
