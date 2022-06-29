package com.piyushgoel.blog.dataTransferObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	private UUID id;
	private String name;
	private String email;
	private String about;
}
