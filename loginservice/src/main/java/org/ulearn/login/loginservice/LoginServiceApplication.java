package org.ulearn.login.loginservice;

import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.ulearn.login.loginservice.config.YMLConfig;


import springfox.documentation.swagger2.annotations.EnableSwagger2;




@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages= {"org.ulearn.login.loginservice.*"})
@EnableSwagger2
public class LoginServiceApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceApplication.class);
	
	@Autowired
	private YMLConfig myConfig;
	
	@Bean
	public BCryptPasswordEncoder bcryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
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
	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : "+myConfig.getName());
		LOGGER.info("environment : "+myConfig.getEnvironment());
		LOGGER.info("contextpath : "+myConfig.getContextpath());
		LOGGER.info("servers: "+myConfig.getServers());
		LOGGER.info("-------------------------------------");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
		LOGGER.info("In LoginServiceApplication Main Method");
		
	}
	
	
	
	
	
}
