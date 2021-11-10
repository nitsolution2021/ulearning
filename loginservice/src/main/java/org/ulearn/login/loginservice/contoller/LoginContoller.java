package org.ulearn.login.loginservice.contoller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.ulearn.login.loginservice.LoginServiceApplication;
import org.ulearn.login.loginservice.entity.GlobalEntity;
import org.ulearn.login.loginservice.entity.GlobalResponse;
import org.ulearn.login.loginservice.entity.LoginEntity;
import org.ulearn.login.loginservice.entity.LoginResetEntity;
import org.ulearn.login.loginservice.entity.LoginUserDetails;
import org.ulearn.login.loginservice.helper.JwtUtil;
import org.ulearn.login.loginservice.exception.CustomException;
import org.ulearn.login.loginservice.repository.LoginRepository;
import org.ulearn.login.loginservice.repository.LoginResetRepo;
import org.ulearn.login.loginservice.services.LoginService;
import org.ulearn.login.loginservice.services.MailService;
import org.ulearn.login.loginservice.services.VendorDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	private JwtUtil jwtUtil;

	@Autowired
	private LoginResetRepo loginResetRepo;
	
	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoginService loginService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginContoller.class);

	@GetMapping("/mailForgotPasswordLink/{email}")
	@Description("Using This API You Can Send The Recovery Link to Email, and Using That Link He/She Can Recover The Password")
	public GlobalResponse mailForgotPasswordLink(@PathVariable("email") String email) {

		LOGGER.info("Inside - LoginContoller.getInstute()");

		try {

			UUID uuid = UUID.randomUUID();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(date);
			String forgotPasswordLinkCreateTime = calObj.get(Calendar.YEAR) + "-" + (calObj.get(Calendar.MONTH) + 1)+ "-" + calObj.get(Calendar.DATE) + "-" + (calObj.get(Calendar.HOUR) + "-"+ (calObj.get(Calendar.MINUTE) + "-" + (calObj.get(Calendar.SECOND))));
			String encodedString = Base64.getEncoder().encodeToString(forgotPasswordLinkCreateTime.getBytes());
			byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
			String decodedString = new String(decodedBytes);
			String forgotPasswordLink = "https://ulearn.nichetechnosolution.com/forgotpassword/" + uuid.toString() + "/"+ encodedString;
			
			//** CHECKING THE EMAIL IS PREASENT IN DATABASE **//
			Optional<LoginEntity> findByUserName = loginRepository.findByUserName(email);
			
			if (findByUserName.isPresent()) {
				LoginResetEntity loginResetEntity = new LoginResetEntity();
				loginResetEntity.setuId(findByUserName.get().getUid());
				loginResetEntity.setPrToken(forgotPasswordLink);
				loginResetEntity.setStatus("NEW");
				loginResetEntity.setCreatedOn(new Date());
				//** SAVE THE DETAILS IN DATABASE **//
				LoginResetEntity save = loginResetRepo.save(loginResetEntity);
				if (!save.equals(null)) {
					//** SEND MAIL IF DETAILS SAVE IN DATABASE **//
					mailService.sendEmail("soumendolui077@gmail.com", findByUserName.get().getEmail(),forgotPasswordLink);
				}

			}
			return new GlobalResponse("Mail Send Successfully","SUCCESS");

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public String resetPassword(@PathVariable("link") String link, @RequestBody GlobalEntity globalEntity) {
		Optional<LoginResetEntity> findByPrToken = loginResetRepo.findByPrToken(link);

		if (findByPrToken.isPresent()) {
			LoginResetEntity loginResetEntity = findByPrToken.get();
			Optional<LoginEntity> findById = loginRepository.findById(loginResetEntity.getuId());
			if (findById.isPresent()) {
				LoginEntity loginEntity = findById.get();
				loginEntity.setPassword(globalEntity.getNewPass());
				loginRepository.save(loginEntity);
			}
		} else {
			return "throw error";
		}

		return "";
	}

	@PostMapping("/changePassword")
	public GlobalResponse changePassword(@RequestBody GlobalEntity globalEntity,
			@RequestHeader(name = "Authorization") String token) {
		return this.loginService.changePass(globalEntity, token);

	}
	
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> venderLogin(@RequestBody LoginEntity loginEntity) {
		LOGGER.info("Inside - VendorLoginController.venderLogin()");
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginEntity.getUserName(), loginEntity.getPassword()));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

		UserDetails vendor = this.vendorDetailsService.loadUserByUsername(loginEntity.getUserName());

		String token = jwtUtil.generateToken(vendor);

		Optional<LoginEntity> findByUserName = loginRepository.findByUserName(vendor.getUsername());
		
		LoginEntity loginEntityAfterCheck = findByUserName.get();
		loginEntityAfterCheck.setAccessToken(token);
		LoginEntity save = loginRepository.save(loginEntityAfterCheck);
		
		if(save.equals(null)) {
			throw new CustomException("Data Not Save Try Again");
		}

		return ResponseEntity.ok(new LoginUserDetails(token,findByUserName.get().getUid(),findByUserName.get().getUserName() , findByUserName.get().getPassword()));

	}

}
