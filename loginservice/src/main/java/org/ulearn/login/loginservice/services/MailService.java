package org.ulearn.login.loginservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;

	    public void sendEmail(String from,String to) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject("This is a plain text email");
		message.setText("Hello guys! This is a plain text email.");
		mailSender.send(message);

	}
}
