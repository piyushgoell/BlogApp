package com.piyushgoel.blog.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.piyushgoel.blog.services.EmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

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

	public void sendMimeEmail(String to, String subject, List<MimeBodyPart> imagePart)
			throws MessagingException, IOException {

		MimeMessage message = this.javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		messageHelper.setTo(to);
		messageHelper.setSubject(subject);

		MimeMultipart multipart = new MimeMultipart("related");

		imagePart.forEach((image) -> {
			try {
				multipart.addBodyPart(image);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
		message.setContent(multipart);
		this.javaMailSender.send(message);

	}
	
	@Override
	public void sendForgotPasswordEmail(String to, String accountActivationURL) throws MessagingException, IOException {
		log.info("Account Activation URL {}", accountActivationURL);
		String subject = "Activate Your Blog Application Account";

		MimeBodyPart textPart = new MimeBodyPart();

		String textMessage = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream("static/accountActivationEmail/template.html"))).lines()
				.collect(Collectors.joining("\n"));
		textMessage = textMessage.replace("http://www.activatemyaccount.com", accountActivationURL);
		textPart.setText(textMessage, "US-ASCII", "html");

		MimeBodyPart imagePart = new MimeBodyPart();

		imagePart.attachFile(
				new ClassPathResource("static/accountActivationEmail/images/animated_header.gif").getFile());
		imagePart.setContentID("<03a2c01e-005a-11ed-b939-0242ac120002>");
		imagePart.setDisposition(MimeBodyPart.INLINE);

		MimeBodyPart imagePart1 = new MimeBodyPart();

		imagePart1.attachFile(
				new ClassPathResource("static/accountActivationEmail/images/body_background_2.png").getFile());
		imagePart1.setContentID("<4d070ada-005a-11ed-b939-0242ac120002>");
		imagePart1.setDisposition(MimeBodyPart.INLINE);

		MimeBodyPart imagePart2 = new MimeBodyPart();

		imagePart2.attachFile(new ClassPathResource("static/accountActivationEmail/images/bottom_img.png").getFile());
		imagePart2.setContentID("<5b8503e6-005a-11ed-b939-0242ac120002>");
		imagePart2.setDisposition(MimeBodyPart.INLINE);

		this.sendMimeEmail(to, subject, Arrays.asList(textPart, imagePart, imagePart1, imagePart2));

	}
	
	@Override
	public void sendAccountActivationEmail(String to, String accountActivationURL)
			throws MessagingException, IOException {
		
		String subject = "Activate Your Blog Account";

		MimeBodyPart textPart = new MimeBodyPart();

		String textMessage = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream("templates/accountActivationTemplate.html"))).lines()
				.collect(Collectors.joining("\n"));
		textMessage = textMessage.replace("http://www.activatemyaccount.com", accountActivationURL);
		textPart.setText(textMessage, "US-ASCII", "html");
		this.sendMimeEmail(to, subject, Arrays.asList(textPart));
	}
 
	@Override
	public void sendRegistrationConfirmationEmail(String to) throws MessagingException, IOException {
		
		String subject = "Congratulations!! Your Blog Account has been activated Successfully";

		MimeBodyPart textPart = new MimeBodyPart(); 

		String textMessage = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream("templates/accountActivationConfiramationTemplate.html"))).lines()
				.collect(Collectors.joining("\n"));
		textPart.setText(textMessage, "US-ASCII", "html");
		this.sendMimeEmail(to, subject, Arrays.asList(textPart));

	}

}
