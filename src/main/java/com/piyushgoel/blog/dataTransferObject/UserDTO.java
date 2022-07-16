package com.piyushgoel.blog.dataTransferObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	
	private String name;
	private String email;
	private String about;
}
