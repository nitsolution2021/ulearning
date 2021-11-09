package org.ulearn.login.loginservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;

	    public void sendEmail(String from,String to,String body) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject("Forgot Password Link");
		message.setText("Please click on link and reset your password:-  "+body);
		mailSender.send(message);

	}
}
