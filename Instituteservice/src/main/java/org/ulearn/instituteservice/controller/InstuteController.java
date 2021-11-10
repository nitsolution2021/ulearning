package org.ulearn.instituteservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.instituteservice.entity.GlobalResponse;
import org.ulearn.instituteservice.entity.InstituteEntrity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.repository.InstituteRepo;


@RestController
@RequestMapping("/institute")
public class InstuteController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);
	
@Autowired
 private InstituteRepo instituteRepo;
	
	@GetMapping("/list")
	public List<InstituteEntrity> getInstute() {
		LOGGER.info("Inside - InstituteController.getInstute()");
		
		try {
			List<InstituteEntrity> findAll = instituteRepo.findAll();
			if (findAll.size() < 1) {
				throw new CustomException("Institute Not Found!");
			}else {				
				return findAll;
			}			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@PostMapping("/add")
	public GlobalResponse postInstituteDetails(@RequestBody InstituteEntrity instituteEntrity) {
		LOGGER.info("Inside - InstituteController.postInstituteDetails()");
		
	try {
		List<InstituteEntrity> findByInstituteName = instituteRepo.findByInstName(instituteEntrity.getInstName());
		
		if (findByInstituteName.size() < 1) {
			InstituteEntrity filterInsDetails= new InstituteEntrity();
			
			filterInsDetails.setInstName(instituteEntrity.getInstName());
			filterInsDetails.setInstEndDate(instituteEntrity.getInstEndDate());
			filterInsDetails.setInstWebsite(instituteEntrity.getInstWebsite());
			filterInsDetails.setInstEmail(instituteEntrity.getInstEmail());
			filterInsDetails.setInstCnum(instituteEntrity.getInstCnum());
			filterInsDetails.setInstMnum(instituteEntrity.getInstMnum());
			filterInsDetails.setIsntRegDate(new Date());
			filterInsDetails.setInstLogo(instituteEntrity.getInstLogo());
			filterInsDetails.setInstPanNum(instituteEntrity.getInstPanNum());
			filterInsDetails.setInstGstNum(instituteEntrity.getInstGstNum());
			filterInsDetails.setInstStatus(instituteEntrity.getInstStatus());
			filterInsDetails.setIsActive(0);
			filterInsDetails.setIsDeleted(0);
			filterInsDetails.setCreatedOn(new Date());
			filterInsDetails.setUpdatedOn(new Date());	
			
			InstituteEntrity save=instituteRepo.save(filterInsDetails);
			return new GlobalResponse("success","Institute Added Successfully");	
		}else {
			throw new CustomException("Institute Name already exist!");
		}
	} catch (Exception e) {
		 throw new CustomException(e.getMessage());
	}
		
	}
}
