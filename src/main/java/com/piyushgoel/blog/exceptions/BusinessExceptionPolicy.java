package com.piyushgoel.blog.exceptions;

import org.springframework.http.HttpStatus;

public interface BusinessExceptionPolicy extends AppExceptionPolicy{
	 HttpStatus getHttpStatus();
}
