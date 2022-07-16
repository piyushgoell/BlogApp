package com.piyushgoel.blog.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(value = "(hasAuthority('ROLE_USER') and authentication.principal.id.equals(#Id))"
		+ " or "
		+ "hasAuthority('ROLE_ADMIN')"
		+ " or "
		+ "hasAuthority('ROLE_SUPER_ADMIN')")
public @interface IsAuthorised {

}
