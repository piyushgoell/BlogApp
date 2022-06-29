package com.piyushgoel.blog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(String to, String subject, String message) {
		SimpleMailMessage registrationMessage = new SimpleMailMessage();
		
		registrationMessage.setTo(to);
		registrationMessage.setSubject(subject);
		registrationMessage.setText(message);
		this.javaMailSender.send(registrationMessage);
		
	}

}
