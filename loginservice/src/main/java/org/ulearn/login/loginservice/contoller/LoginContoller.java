package org.ulearn.login.loginservice.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.ulearn.login.loginservice.entity.Login;
import org.ulearn.login.loginservice.repository.LoginRepository;
import org.ulearn.login.loginservice.services.MailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginContoller {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@GetMapping("/sendmail")
	public String sendMail() {
		mailService.sendEmail("soumendolui077@gmail.com", "shantanurong44@gmail.com","");
		return "send successfully";
	}
	
	@GetMapping("/mailForgotPasswordLink")
	public String mailForgotPasswordLink() {
		UUID uuid = UUID.randomUUID();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    formatter.format(date);
		Calendar calObj = Calendar.getInstance();
		calObj.setTime(date);
//		Date time2 = calObj.getTime();
//		LOGGER.info("invDate&&&&&&&&&"+invDate);
		String forgotPasswordLinkCreateTime = calObj.get(Calendar.YEAR) + "-" + (calObj.get(Calendar.MONTH) + 1) + "-" + calObj.get(Calendar.DATE) + "-" +(calObj.get(Calendar.HOUR)+"-" +(calObj.get(Calendar.MINUTE)+"-" +(calObj.get(Calendar.SECOND))));
		String encodedString = Base64.getEncoder().encodeToString(forgotPasswordLinkCreateTime.getBytes());
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		String forgotPasswordLink = "https://ulearn.nichetechnosolution.com/forgotpassword/"+uuid.toString()+"/"+encodedString;
		mailService.sendEmail("soumendolui077@gmail.com", "shantanurong44@gmail.com",forgotPasswordLink);
		return forgotPasswordLink;
	}
	
	public String resetPassword() {
		Login login = new Login();
		
		return "";
	}
	
	@PostMapping("/changePassword")
	public void changePassword(@RequestBody String name, @RequestHeader(name = "Authorization") String token) {
		
	}

}
