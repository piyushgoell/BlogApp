package com.piyushgoel.blog.exceptions.impl;

import org.springframework.http.HttpStatus;

import com.piyushgoel.blog.exceptions.BusinessExceptionPolicy;
import com.piyushgoel.blog.exceptions.enums.BusinessExceptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.String.format;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException implements BusinessExceptionPolicy {

		private static final long serialVersionUID = 1L;
		
		protected final String code;
	    protected final String message;
	    protected final HttpStatus httpStatus;

	    /**
	     * Constructor accepting an exception reason.
	     *
	     * @param reason the reason of the exception
	     */
	    public BusinessException(final BusinessExceptionType reason) {
	        this.code = reason.getCode();
	        this.message = reason.getMessage();
	        this.httpStatus = reason.getHttpStatus();
	    }

	    /**
	     * Constructor accepting an exception reason and an http status that will override the default one from the reason.
	     *
	     * @param reason               the reason of the exception
	     * @param overridingHttpStatus the http status which overrides the one from the reason
	     */
	    public BusinessException(final BusinessExceptionType reason, final HttpStatus overridingHttpStatus) {
	        this.code = reason.getCode();
	        this.message = reason.getMessage();
	        this.httpStatus = overridingHttpStatus;
	    }

	    /**
	     * Constructor accepting an excepting reason and optional parameters which are replaced in the
	     * message.
	     *
	     * @param reason     the reason of the exception
	     * @param parameters the optional parameters
	     */
	    public BusinessException(final BusinessExceptionType reason, final Object... parameters) {
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
	        return format("BusinessException(code=%s, message=%s, httpStatus=%s)", this.getCode(), this.getMessage(),
	                this.getHttpStatus().value());
	    }

}
