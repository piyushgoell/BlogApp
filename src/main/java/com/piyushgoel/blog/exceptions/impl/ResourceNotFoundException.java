package com.piyushgoel.blog.exceptions.impl;

import static java.lang.String.format;

import org.springframework.http.HttpStatus;

import com.piyushgoel.blog.exceptions.ResourceNotFoundPolicy;
import com.piyushgoel.blog.exceptions.enums.ResourceNotFoundExceptionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException implements ResourceNotFoundPolicy {

	private static final long serialVersionUID = 1L;

	private final String code;
	private final String message;
	protected final HttpStatus httpStatus;

	public ResourceNotFoundException(final ResourceNotFoundExceptionType reason) {
		this.code = reason.getCode();
		this.message = reason.getMessage();
		this.httpStatus = reason.getHttpStatus();
	}

	/**
	 * Constructor accepting an exception reason and an http status that will
	 * override the default one from the reason.
	 *
	 * @param reason               the reason of the exception
	 * @param overridingHttpStatus the http status which overrides the one from the
	 *                             reason
	 */
	public ResourceNotFoundException(final ResourceNotFoundExceptionType reason,
			final HttpStatus overridingHttpStatus) {
		this.code = reason.getCode();
		this.message = reason.getMessage();
		this.httpStatus = overridingHttpStatus;
	}

	/**
	 * Constructor accepting an excepting reason and optional parameters which are
	 * replaced in the message.
	 *
	 * @param reason     the reason of the exception
	 * @param parameters the optional parameters
	 */
	public ResourceNotFoundException(final ResourceNotFoundExceptionType reason, final Object... parameters) {
		if (parameters != null) {
			this.message = format(reason.getMessage(), parameters);
		} else {
			this.message = reason.getMessage();
		}

		this.code = reason.getCode();
		this.httpStatus = reason.getHttpStatus();
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

	public String toString() {
		return format("ResourceNotFoundException(code=%s, message=%s, httpStatus=%s)", this.getCode(),
				this.getMessage(), this.getHttpStatus().value());
	}

}
