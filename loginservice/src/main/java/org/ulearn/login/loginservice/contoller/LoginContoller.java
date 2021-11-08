package org.ulearn.login.loginservice.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.ulearn.login.loginservice.services.MailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginContoller {
	
	@Autowired
	private MailService mailService;
	
	@GetMapping("/sendmail")
	public String sendMail() {
		mailService.sendEmail("soumendolui077@gmail.com", "shantanurong44@gmail.com");
		return "send successfully";
	}
	
	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		UUID uuid = UUID.randomUUID();
		String forgotPasswordLink = "192.168.0.129:8080/forgotpassword/"+uuid.toString();
		return forgotPasswordLink;
	}

}
