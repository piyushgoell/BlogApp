package com.piyushgoel.blog.services;

import java.io.IOException;

import javax.mail.MessagingException;

public interface EmailService {
	public void sendEmail(String to, String subject, String message);
	public void sendAccountActivationEmail(String to, String accountActivationURL) throws MessagingException, IOException;
	public void sendRegistrationConfirmationEmail(String to) throws MessagingException, IOException;
	public void sendForgotPasswordEmail(String to, String accountActivationURL) throws MessagingException, IOException;
}
