package org.ulearn.login.loginservice;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootApplication
public class LoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}
	
	@Bean
    public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("soumendolui077@gmail.com");
		mailSender.setPassword("Soumen@1234c");
		 
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "false");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		 
		mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
	
}
