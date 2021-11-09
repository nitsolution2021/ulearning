package org.ulearn.login.loginservice.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.ulearn.login.loginservice.entity.GlobalEntity;
import org.ulearn.login.loginservice.entity.GlobalResponse;
import org.ulearn.login.loginservice.entity.LoginEntity;
import org.ulearn.login.loginservice.entity.LoginResetEntity;
import org.ulearn.login.loginservice.repository.LoginRepository;
import org.ulearn.login.loginservice.repository.LoginResetRepo;
import org.ulearn.login.loginservice.services.LoginService;
import org.ulearn.login.loginservice.services.MailService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginContoller {

	@Autowired
	private MailService mailService;
	


	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private LoginResetRepo loginResetRepo;

	@Autowired
	private LoginService loginService;

	@GetMapping("/sendmail")
	public String sendMail() {
		mailService.sendEmail("soumendolui077@gmail.com", "ahadul@nitsolution.in", "");
		return "send successfully"; 
	}

	@GetMapping("/mailForgotPasswordLink")
	public String mailForgotPasswordLink(@PathVariable("email") String email) {
		UUID uuid = UUID.randomUUID();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		formatter.format(date);
		Calendar calObj = Calendar.getInstance();
		calObj.setTime(date);
		String forgotPasswordLinkCreateTime = calObj.get(Calendar.YEAR) + "-" + (calObj.get(Calendar.MONTH) + 1) + "-"
				+ calObj.get(Calendar.DATE) + "-" + (calObj.get(Calendar.HOUR) + "-"
						+ (calObj.get(Calendar.MINUTE) + "-" + (calObj.get(Calendar.SECOND))));
		String encodedString = Base64.getEncoder().encodeToString(forgotPasswordLinkCreateTime.getBytes());
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		String forgotPasswordLink = "https://ulearn.nichetechnosolution.com/forgotpassword/" + uuid.toString() + "/"
				+ encodedString;
		Optional<LoginEntity> findByUserName = loginRepository.findByUserName(email);
		if (findByUserName.isPresent()) {
			LoginResetEntity loginResetEntity = new LoginResetEntity();
			loginResetEntity.setuId(findByUserName.get().getUid());
			loginResetEntity.setPrToken(forgotPasswordLink);
			loginResetEntity.setStatus("NEW");
			loginResetEntity.setCreatedOn(new Date());
			LoginResetEntity save = loginResetRepo.save(loginResetEntity);
			if (!save.equals(null)) {
				mailService.sendEmail("soumendolui077@gmail.com", findByUserName.get().getEmail(), forgotPasswordLink);
			}

		}
		return forgotPasswordLink;
	}

	public String resetPassword(@PathVariable("link") String link , @RequestBody GlobalEntity globalEntity) {
		Optional<LoginResetEntity> findByPrToken = loginResetRepo.findByPrToken(link);
		
		if(findByPrToken.isPresent()) {
			LoginResetEntity loginResetEntity = findByPrToken.get();
			Optional<LoginEntity> findById = loginRepository.findById(loginResetEntity.getuId());
			if(findById.isPresent()) {
				LoginEntity loginEntity = findById.get();
				loginEntity.setPassword(globalEntity.getNewPass());
				loginRepository.save(loginEntity);
			}
		}else {
			return "throw error";
		}
		
		return "";
	}

	@PostMapping("/changePassword")
	public GlobalResponse changePassword(@RequestBody GlobalEntity globalEntity,
			@RequestHeader(name = "Authorization") String token) {
		return this.loginService.changePass(globalEntity, token);

	}

}
