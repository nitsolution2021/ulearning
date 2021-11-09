package org.ulearn.licenseservice.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
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
		
		return this.licenseService.addLicense(licenseEntity);
	}
}
