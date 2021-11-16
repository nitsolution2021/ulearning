package org.ulearn.licenseservice.controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
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
	public GlobalResponse addLicense(@RequestBody LicenseEntity licenseEntity ) {
		
		LOGGER.info("Inside the LicenseController Add License");
		try {
			
			return this.licenseService.addLicense(licenseEntity);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@PutMapping("/update/{licenseId}")
	public GlobalResponse updateLicense(@PathVariable() long licenseId, @RequestBody LicenseEntity licenseForUpdate ) {
		
		LOGGER.info("Inside the LicenseController Update License");
		try {
			
			return this.licenseService.updateLicense(licenseId,licenseForUpdate);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@GetMapping("/list")
	public List<LicenseEntity> getLicenseList() {
		
		LOGGER.info("Inside the LicenseController view all License");
		
		try {
		
			return this.licenseService.getAllLicenseList();
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@GetMapping("/view/{lcId}")
	public Optional<LicenseEntity> getLicense(@PathVariable long lcId){
		
		LOGGER.info("Inside the LicenseController view License by id");
		
		try {
			
			return this.licenseService.getLicense(lcId);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	
	}
	
	@PostMapping("/suspend/{lcId}")
	public GlobalResponse addSuspend(@PathVariable long lcId, @RequestBody LicenseLogEntity licenseLogEntitySuspend) {
		
		LOGGER.info("Inside the suspend License");
		try {
			
			return this.licenseService.addSuspendLicense(lcId,licenseLogEntitySuspend);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}
}
