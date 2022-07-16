package com.piyushgoel.blog.logger;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityLogger {

	@EventListener
	  public void authenticated(final @NonNull AuthenticationSuccessEvent event) {
	    final Object principal = event.getAuthentication().getPrincipal();
	    log.info("Successful login - [username: \"{}\"]", principal);
	  }
	  @EventListener
	  public void authenticationFailure(final @NonNull AbstractAuthenticationFailureEvent event) {
	    final Object principal = event.getAuthentication().getPrincipal();
	    log.info("Unsuccessful login - [username: \"{}\"]", principal);
	  }
	  @EventListener
	  public void authorizationFailure(final @NonNull AuthorizationFailureEvent event) {
	    final Object principal = event.getAuthentication().getPrincipal();
	    final String message = event.getAccessDeniedException().getMessage();
	    log.error("Unauthorized access - [username: \"{}\", message: \"{}\"]", principal, Optional.ofNullable(message).map(Function.identity()).orElse("<null>"));
	  }
	  @EventListener
	  public void logoutSuccess(final @NonNull LogoutSuccessEvent event) {
	    final Object principal = event.getAuthentication().getPrincipal();
	    log.info("Successful logout - [username: \"{}\"]", principal);
	  }
}
