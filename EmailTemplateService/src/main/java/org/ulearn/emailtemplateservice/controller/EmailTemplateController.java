package org.ulearn.emailtemplateservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.emailtemplateservice.entity.EmailTemplateEntity;
import org.ulearn.emailtemplateservice.entity.GlobalResponse;
import org.ulearn.emailtemplateservice.exception.CustomException;
import org.ulearn.emailtemplateservice.repository.EmailTemplateRepo;
import org.ulearn.emailtemplateservice.validation.FieldValidation;


@RestController
@RequestMapping("/emailTemplate")
public class EmailTemplateController {
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private EmailTemplateRepo emailTemplateRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateController.class);
	
	
	
	@PostMapping("/add")
	public GlobalResponse emailTemplateAdd(@RequestBody EmailTemplateEntity emailTemplateEntity) {
		LOGGER.info("Inside - EmailTemplateController.emailTemplateAdd()");
		try {
			
			if(fieldValidation.isEmpty(emailTemplateEntity.getEtName()) && 
					fieldValidation.isEmpty(emailTemplateEntity.getEtSubject()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtBody()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtAction()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getIsPrimary())
					){
				if(emailTemplateRepo.findByEtAction(emailTemplateEntity.getEtAction()).size()>0) {
					EmailTemplateEntity newEmailTemplateEntity = new EmailTemplateEntity();
					newEmailTemplateEntity.setEtName(emailTemplateEntity.getEtName());
					newEmailTemplateEntity.setEtSubject(emailTemplateEntity.getEtSubject());
					newEmailTemplateEntity.setEtBody(emailTemplateEntity.getEtBody());
					newEmailTemplateEntity.setEtType("CUSTOM");
					newEmailTemplateEntity.setIsPrimary(emailTemplateEntity.getIsPrimary());
					newEmailTemplateEntity.setEtAction(emailTemplateEntity.getEtAction());
					newEmailTemplateEntity.setIsActive(emailTemplateEntity.getIsActive());
					newEmailTemplateEntity.setIsDeleted(1);
					newEmailTemplateEntity.setEtOrder(emailTemplateEntity.getEtOrder());
					newEmailTemplateEntity.setEtTags(emailTemplateEntity.getEtTags());
					newEmailTemplateEntity.setCreatedOn(new Date());
					EmailTemplateEntity save = emailTemplateRepo.save(newEmailTemplateEntity);
					if(save.equals(null)) {
						throw new CustomException("Data Not Save Try Again");
					}else {
						return new GlobalResponse("Data Save Successfully","SUCCESS");
					}	
				}else {
					throw new CustomException("The Custome Template Action is Not Present in Default Action");
				}
				
			}else {
				throw new CustomException("Validation Error");
			}
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@PutMapping("/update/{id}")
	public GlobalResponse emailTemplateUpdate(@RequestParam("id") Long id, @RequestBody EmailTemplateEntity emailTemplateEntity) {
		LOGGER.info("Inside - EmailTemplateController.emailTemplateUpdate()");
		try {
			
			if(fieldValidation.isEmpty(emailTemplateEntity.getEtName()) && 
					fieldValidation.isEmpty(emailTemplateEntity.getEtSubject()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtBody()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtAction()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getIsPrimary()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtId())
					){
				Optional<EmailTemplateEntity> findById = emailTemplateRepo.findById(id);
				if(findById.isPresent()) {
					List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(1, id);
					if(findByIdAndDelete.size()<1) {
						EmailTemplateEntity newEmailTemplateEntity = new EmailTemplateEntity();
						newEmailTemplateEntity.setEtId(emailTemplateEntity.getEtId());
						newEmailTemplateEntity.setEtName(emailTemplateEntity.getEtName());
						newEmailTemplateEntity.setEtSubject(emailTemplateEntity.getEtSubject());
						newEmailTemplateEntity.setEtBody(emailTemplateEntity.getEtBody());
						newEmailTemplateEntity.setEtType(emailTemplateEntity.getEtType());
						newEmailTemplateEntity.setIsPrimary(emailTemplateEntity.getIsPrimary());
						newEmailTemplateEntity.setEtAction(emailTemplateEntity.getEtAction());
						newEmailTemplateEntity.setIsActive(emailTemplateEntity.getIsActive());
						newEmailTemplateEntity.setIsDeleted(emailTemplateEntity.getIsDeleted());
						newEmailTemplateEntity.setEtOrder(emailTemplateEntity.getEtOrder());
						newEmailTemplateEntity.setEtTags(emailTemplateEntity.getEtTags());
						newEmailTemplateEntity.setCreatedOn(emailTemplateEntity.getCreatedOn());
						newEmailTemplateEntity.setUpdatedOn(emailTemplateEntity.getUpdatedOn());
						EmailTemplateEntity save = emailTemplateRepo.save(newEmailTemplateEntity);
						if(save.equals(null)) {
							throw new CustomException("Data Not Save Try Again");
						}else {
							return new GlobalResponse("Data Save Successfully","SUCCESS");
						}	
					}else {
						throw new CustomException("Id is Deleted");
					}
				}else {
					throw new CustomException("Id is Not Present");
				}
			}else {
				throw new CustomException("Validation Error");
			}
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	@DeleteMapping("/delete/{id}")
	public GlobalResponse emailTemplateDelete(@RequestParam("id") Long id) {
		
		LOGGER.info("Inside - EmailTemplateController.emailTemplateDelete()");
		try {
			Optional<EmailTemplateEntity> findById = emailTemplateRepo.findById(id);
			if(findById.isPresent()) {
				List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(1, id);
				if(findByIdAndDelete.size()<1) {
					EmailTemplateEntity emailTemplateEntity = findById.get();
					emailTemplateEntity.setIsDeleted(0);
					EmailTemplateEntity save = emailTemplateRepo.save(emailTemplateEntity);
					if(save.equals(null)) {
						throw new CustomException("Data Not Save Try Again");
					}else {
						return new GlobalResponse("Data Save Successfully","SUCCESS");
					}	
				}else {
					throw new CustomException("Id is Deleted");
				}
			}else {
				throw new CustomException("Id is Not Present");
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/getAll/{type}")
	public List<EmailTemplateEntity> emailTemplateGetAll(@PathVariable("type") String type) {
		
		LOGGER.info("Inside - EmailTemplateController.emailTemplateGetAll()");
		try {
			if(type.equals("template")) {
				List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.findAllByIdAndDelete(1);
				if(findAllByIdAndDelete.size()<1) {
					throw new CustomException("No Data Present");
				}else {
					return findAllByIdAndDelete;
				}
			}else if(type.equals("template_for")){
				List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.findEtTypeByIdAndDeleteWithDefaultET(1,"DEFAULT");
				if(findAllByIdAndDelete.size()<1) {
					throw new CustomException("No Data Present");
				}else {
					return findAllByIdAndDelete;
				}
			}else {
				throw new CustomException("Type Not Matched");
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
//	@GetMapping("/viewTemplate/{id}")
//	public List<EmailTemplateEntity> viewTemplate(@PathVariable("type") String type) {
//		
//		LOGGER.info("Inside - EmailTemplateController.viewTemplate()");
//		try {
//			
//		}catch(Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//		
//	}
	

}
