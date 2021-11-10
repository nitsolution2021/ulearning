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
	
	@PutMapping("/update/{licenseId}")
	public GlobalResponse updateLicense(@PathVariable() long licenseId, @RequestBody LicenseEntity licenseForUpdate ) {
		
		LOGGER.info("Inside the LicenseController Update License");
		
		return this.licenseService.updateLicense(licenseId,licenseForUpdate);
	}
	
	@GetMapping("/list")
	public List<LicenseEntity> getLicenseList() {
		
		return this.licenseService.getAllLicenseList();
	}
	
	@GetMapping("/view/{lcId}")
	public Optional<LicenseEntity> getLicense(@PathVariable long lcId){
		
		return this.licenseService.getLicense(lcId);
	}
}
