package com.piyushgoel.blog.dataTransferObject;

import com.piyushgoel.blog.enums.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
	private RoleType type;
}
