package org.ulearn.instituteservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.ulearn.instituteservice.entity.GlobalResponse;
import org.ulearn.instituteservice.entity.InstituteEntrity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.repository.InstituteRepo;
import org.ulearn.instituteservice.validation.FieldValidation;


@RestController
@RequestMapping("/institute")
public class InstuteController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);
	
@Autowired
 private InstituteRepo instituteRepo;

@Autowired
private FieldValidation fieldValidation;
	
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
	
	@GetMapping("/view/{instId}")
	public Optional<InstituteEntrity> viewInstituteDetails(@PathVariable() long instId)
	{
		LOGGER.info("Inside - InstituteController.viewInstituteDetails()");
		try
		{
			Optional<InstituteEntrity> findById = this.instituteRepo.findById(instId);
			if (!(findById.isPresent())) {							
				throw new CustomException("Institute Not Found!");
			}else {
				return findById;
			}
			
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
		//return ResponseEntity.ok(instId);
	}
	@PutMapping("/update/{instId}")
	public GlobalResponse putInstituteDetails(@RequestBody InstituteEntrity instituteEntrity,@PathVariable() long instId)
	{
		LOGGER.info("Inside - InstituteController.putInstituteDetails()");
		try
		{
			
			if ((fieldValidation.isEmpty(instituteEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstWebsite()))					
					& (fieldValidation.isEmpty(instituteEntrity.getInstPanNum()))) {
			if(instituteRepo.existsById(instId))
			{				
				String dat_name=instituteEntrity.getInstName();
				String dat_website=instituteEntrity.getInstWebsite();
				String dat_email=instituteEntrity.getInstEmail();
				String dat_Cnum=instituteEntrity.getInstCnum();
				String dat_Mnum=instituteEntrity.getInstMnum();
				String dat_logo=instituteEntrity.getInstLogo();
				String dat_PANno=instituteEntrity.getInstPanNum();
				String dat_GSTno=instituteEntrity.getInstGstNum();
				String dat_stat=instituteEntrity.getInstStatus();
				long dat_id=instId; 
				//String sId=String.valueOf(dat_id);
				Date dat_regDate=instituteEntrity.getIsntRegDate();
				Date dat_creatDate=instituteEntrity.getCreatedOn();
				Date dat_endDate=instituteEntrity.getInstEndDate();
				//Date dat_updtDate=update_dat.getUpdatedOn();
//				if(dat_id!=0 && dat_regDate!=null && dat_creatDate!=null && dat_endDate!=null)
//				{
//					return new GlobalResponse("success","You don't have a permission to change!");
//				}
					InstituteEntrity dat=instituteRepo.getById(instId);
					long dat_id_long=instId;
//					System.out.println(dat_id_long);
					dat_regDate=dat.getIsntRegDate();
					dat_creatDate=dat.getCreatedOn();
					dat_endDate=dat.getInstEndDate();
					int dat_active=dat.getIsActive();
					int dat_delete=dat.getIsDeleted();
					InstituteEntrity updt_dat=new InstituteEntrity();
					updt_dat.setCreatedOn(dat_creatDate);
					updt_dat.setInstCnum(dat_Cnum);
					updt_dat.setInstEmail(dat_email);
					updt_dat.setInstEndDate(dat_endDate);
					updt_dat.setInstGstNum(dat_GSTno);
					updt_dat.setInstId(dat_id_long);
					updt_dat.setInstLogo(dat_logo);
					updt_dat.setInstMnum(dat_Mnum);
					updt_dat.setInstName(dat_name);
					updt_dat.setInstPanNum(dat_PANno);
					updt_dat.setInstStatus(dat_stat);
					updt_dat.setInstWebsite(dat_website);
					updt_dat.setIsActive(dat_active);
					updt_dat.setIsDeleted(dat_delete);
					updt_dat.setIsntRegDate(dat_regDate);
					updt_dat.setUpdatedOn(new Date());
					instituteRepo.save(updt_dat);					
					return new GlobalResponse("success","Institute Updated Successfully");
				
			}else {
					return new GlobalResponse("error","Institute Not Exist!");
				}				
			}
			else
			{				
				throw new CustomException("Institute Not Found!");
			}
		}
		catch(Exception e)
		{ 
			throw new CustomException(e.getMessage());
		}
	}
}
