package com.piyushgoel.blog.exceptions.impl;

import com.piyushgoel.blog.exceptions.AppExceptionPolicy;
import com.piyushgoel.blog.exceptions.ApplicationExceptionPolicy;
import com.piyushgoel.blog.exceptions.enums.ApplicationExceptionType;

import lombok.Getter;
import lombok.Setter;

import static java.lang.String.format;

@Getter
@Setter
public class ApplicationException extends RuntimeException implements AppExceptionPolicy{
	

	private static final long serialVersionUID = 1L;
	
	private final String code;
    private final String message;
	
    public ApplicationException(final ApplicationExceptionPolicy reason) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
    }


    /**
     * Constructor accepting an excepting reason and optional parameters which are replaced in the
     * message.
     *
     * @param reason     the reason of the exception
     * @param parameters the optional parameters
     */
    public ApplicationException(final ApplicationExceptionType reason, final Object... parameters) {
        if (parameters != null) {
            this.message = format(reason.getMessage(), parameters);
        } else {
            this.message = reason.getMessage();
        }

        this.code = reason.getCode();
    }


    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    public String toString() {
        return format("ApplicationException(code=%s, message=%s)", this.getCode(), this.getMessage());
    }

}
