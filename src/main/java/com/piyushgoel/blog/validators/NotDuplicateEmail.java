package com.piyushgoel.blog.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotDuplicateEmailValidator.class)
@Documented
public @interface NotDuplicateEmail {
	
	String message() default "Email Address Already Registered With User";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};
}
