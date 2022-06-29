package com.piyushgoel.blog.services;

public interface EmailService {
	public void sendEmail(String to, String subject, String message);
}
