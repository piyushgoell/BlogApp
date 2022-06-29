package com.piyushgoel.blog.exceptions.enums;

import org.springframework.http.HttpStatus;

import com.piyushgoel.blog.exceptions.ResourceNotFoundPolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceNotFoundExceptionType implements ResourceNotFoundPolicy {
	
	RESOURCE_NOT_FOUND("%s not found with %s : %s", HttpStatus.NOT_FOUND);
	
	
	private final String code = ResourceNotFoundExceptionType.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

}
