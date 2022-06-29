package com.piyushgoel.blog.exceptions.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import com.piyushgoel.blog.exceptions.object.AppException;
import com.piyushgoel.blog.exceptions.object.InvalidParameter;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class AppExceptionBuilder {
	
	 /**
     * Builds an error response based on given parameters.
     *
     * @param code    the error code
     * @param message the error message
     * @param status  the http status attached to the error
     * @return the error response
     */
	public static AppException build(final String code, final String message, final HttpStatus status) {
        return builder(code, message, status);
    }
	
	/**
     * Builds an error response based on given parameters.
     *
     * @param code              the error code
     * @param message           the error message
     * @param status            the http status attached to the error
     * @param invalidParameters the list of invalid parameters
     * @return the error response
     */
	public static AppException build(
			final String code, 
			final String message, 
			final HttpStatus status,
            final List<InvalidParameter> invalidParameters
            ) 
	{
		return builder(code, message, status, invalidParameters);	
	}
	
	private static AppException builder(
			final String code, 
			final String message, 
			final HttpStatus status
			) 
	{
		return builder(code, message, status, null);
	}
	
	
	private static AppException builder(
			final String code, 
			final String message, 
			final HttpStatus status,
			final List<InvalidParameter> invalidParameters
			) 
	{
		final AppException appException = new AppException();
		appException.setCode(code);
		appException.setMessage(message);
		if (!Objects.isNull(status)) appException.setStatus(status.value());
		appException.setTimestamp(LocalDateTime.now());		
		if (!CollectionUtils.isEmpty(invalidParameters)) appException.setInvalidParameters(invalidParameters);
			
		return appException;
	}
}
