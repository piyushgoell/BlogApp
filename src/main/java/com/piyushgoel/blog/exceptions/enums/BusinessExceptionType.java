package com.piyushgoel.blog.exceptions.enums;

import org.springframework.http.HttpStatus;

import com.piyushgoel.blog.exceptions.BusinessExceptionPolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BusinessExceptionType implements BusinessExceptionPolicy {
	
	NOT_FOUND_BY_EXT_REF("%s not found based on the external reference",HttpStatus.NOT_FOUND),
	EXCEPTION_MESSAGE("%s",HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);

	private final String code = BusinessExceptionType.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

}
