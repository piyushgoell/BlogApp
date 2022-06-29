package com.piyushgoel.blog.exceptions.enums;

import com.piyushgoel.blog.exceptions.ApplicationExceptionPolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationExceptionType implements ApplicationExceptionPolicy {
	
	PROPERTY_NOT_EXISTS("Property '%s' for object '%s' doesn't exists");

    private final String code = ApplicationExceptionType.class.getSimpleName();
    private final String message;
}
