package org.ulearn.licenseservice.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.InstituteAdminEntity;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.entity.LicenseGlobalEntity;
import org.ulearn.licenseservice.entity.LicenseLogEntity;
import org.ulearn.licenseservice.exception.CustomException;
import org.ulearn.licenseservice.servises.LicenseService;

@RestController
@RequestMapping("/license")
public class LicenseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicenseController.class);
	
	@Autowired
	private LicenseService licenseService;
	
	
	
	@PostMapping("/add")
	public GlobalResponse addLicense(@Valid @RequestBody LicenseEntity licenseEntity, @RequestHeader("Authorization") String token ) {
		
		LOGGER.info("Inside the LicenseController Add License");
		try {
			
			return this.licenseService.addLicense(licenseEntity,token);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@PutMapping("/update/{licenseId}")
	public GlobalResponse updateLicense(@PathVariable() int licenseId, @RequestBody LicenseEntity licenseForUpdate,@RequestHeader("Authorization") String token) {
		
		LOGGER.info("Inside the LicenseController Update License");
		try {
			
			return this.licenseService.updateLicense(licenseId,licenseForUpdate,token);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@GetMapping("/list")
	public Map<String, Object> getLicenseList(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		
		LOGGER.info("Inside the LicenseController view all License");
		
		try {
		
			return this.licenseService.getAllLicenseList(page,sortBy);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@RequestMapping(value = { "/list/{page}/{limit}/{sortName}/{sort}" }, method = RequestMethod.GET)
	public Map<String, Object> getLicensePagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@RequestParam(defaultValue = "") Optional<String>keyword, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - LicenseController.getLicensePagination()");
		
		try {
			return licenseService.forGetLicensePagination(page,limit,sortBy,sortName,sort,keyword);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/view/{lcId}")
	public Optional<LicenseEntity> getLicense(@PathVariable("lcId") int lcId){
		
		LOGGER.info("Inside the LicenseController view License by id");
		
		try {
			
			return this.licenseService.getLicense(lcId);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	
	}
	
	@PostMapping("/suspend/{lcId}")
	public GlobalResponse addSuspend(@PathVariable("lcId") int lcId, @RequestBody LicenseLogEntity licenseLogEntitySuspend,@RequestHeader("Authorization") String token) {
		
		LOGGER.info("Inside the suspend License");
		try {
			
			return this.licenseService.addSuspendLicense(lcId,licenseLogEntitySuspend,token);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}
//	@GetMapping("/test")
//	public GlobalResponse test(@RequestHeader("Authorization") String token) {
//		try {
//			long instId =  2;
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", token);
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			
//			HttpEntity request=new HttpEntity(headers);
//			ResponseEntity<LicenseGlobalEntity> responseEmailTempForInst=new RestTemplate().exchange("http://localhost:8087/dev/institute/view/"+instId,  HttpMethod.GET, request, LicenseGlobalEntity.class);
//			String emailId = responseEmailTempForInst.getBody().getInstituteAdmin().getAmdEmail();
//			String amdFname = responseEmailTempForInst.getBody().getInstituteAdmin().getAmdFname();
//			String amdLname = responseEmailTempForInst.getBody().getInstituteAdmin().getAmdLname();
//			
//			ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange("http://localhost:8090/dev/emailTemplate/getPrimaryETByAction/License_Create",
//					HttpMethod.GET, request, LicenseGlobalEntity.class);
//			String ETSubject = responseEmailTemp.getBody().getEtSubject();
//			String ETBody = responseEmailTemp.getBody().getEtBody();
//
//			LOGGER.info("Inside the suspend License mail body = "+ETBody+" subject = "+ETSubject);
//			
//			String ETTargetName = "<<_name_>>";
//			
//			String ETNameReplacement = amdFname +" "+ amdLname;
//
//			String processedName = ETBody.replace(ETTargetName, ETNameReplacement);
//
//			JSONObject requestJson = new JSONObject();
//			requestJson.put("senderMailId", emailId);
//			requestJson.put("subject", ETSubject);
//			requestJson.put("body", processedName);
//			requestJson.put("enableHtml", true);
//
//			HttpEntity<String> entity = new HttpEntity(requestJson, headers);
//			ResponseEntity<String> response = new RestTemplate().postForEntity("http://localhost:8086/dev/login/sendMail/", entity, String.class);
//			
//			LOGGER.info("Inside the suspend License  = "+emailId+" f name = "+amdFname + "L anme = "+amdLname);
//			return new GlobalResponse("Success","good",200);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new CustomException(e.getMessage());
//		}
//		
//	}
}
