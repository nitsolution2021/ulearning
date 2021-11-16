package org.ulearn.login.loginservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;

	    public void sendEmail(String to,String subject,String body) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("ulearn@co.in");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);

	}
}
