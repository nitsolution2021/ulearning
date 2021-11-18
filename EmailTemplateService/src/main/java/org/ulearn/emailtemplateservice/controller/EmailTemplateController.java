package org.ulearn.emailtemplateservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
				List<EmailTemplateEntity> findByEtActionWithDefaultET = emailTemplateRepo.findByEtActionWithDefaultET(emailTemplateEntity.getEtAction(),emailTemplateEntity.getEtType());
				String[] split = emailTemplateEntity.getEtBody().split(" ");
				String tags = "";
				for(int i = 0;i < split.length;i++) {
					if(split[i].startsWith("<<") && split[i].endsWith(">>")) {
						LOGGER.info("tags: " + split[i]);
						tags = tags + split[i] + " ";
					}
				}
				if(findByEtActionWithDefaultET.get(0).getEtTags().split(",").length != tags.split(" ").length) {
					throw new CustomException("Tags lengths Are not Match");
				}
				String[] splitTags = tags.split(" ");
				String splitTagsFromDB = findByEtActionWithDefaultET.get(0).getEtTags();
				for(int i = 0;i < splitTags.length;i++) {
					if(splitTagsFromDB.indexOf(splitTags[i]) == -1){
						throw new CustomException("Tags Are Not Present in Default Template");
					}
				}
				List<EmailTemplateEntity> findByEtAction = emailTemplateRepo.findByEtAction(emailTemplateEntity.getEtAction());
				if(findByEtAction.size()>0) {
					if(emailTemplateEntity.getIsPrimary() == 1) {
						List<EmailTemplateEntity> findByEtActionNew = new ArrayList<EmailTemplateEntity>();
						findByEtAction.forEach(obj->{
							obj.setIsPrimary(0);
							findByEtActionNew.add(obj);
						});
						emailTemplateRepo.saveAll(findByEtActionNew);
					}
					
					EmailTemplateEntity newEmailTemplateEntity = new EmailTemplateEntity();
					newEmailTemplateEntity.setEtName(emailTemplateEntity.getEtName());
					newEmailTemplateEntity.setEtSubject(emailTemplateEntity.getEtSubject());
					newEmailTemplateEntity.setEtBody(emailTemplateEntity.getEtBody());
					newEmailTemplateEntity.setEtType("CUSTOM");
					newEmailTemplateEntity.setIsPrimary(emailTemplateEntity.getIsPrimary());
					newEmailTemplateEntity.setEtAction(emailTemplateEntity.getEtAction());
					newEmailTemplateEntity.setIsActive(1);
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
					) {
				
				Optional<EmailTemplateEntity> findById = emailTemplateRepo.findById(id);
				if(findById.isPresent()) {
					List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(1, id);
					if(findByIdAndDelete.size()<1) {
						List<EmailTemplateEntity> findByEtActionWithDefaultET = emailTemplateRepo.findByEtActionWithDefaultET(emailTemplateEntity.getEtAction(),emailTemplateEntity.getEtType());
						String[] split = emailTemplateEntity.getEtBody().split(" ");
						String tags = "";
						for(int i = 0;i < split.length;i++) {
							if(split[i].startsWith("<<") && split[i].endsWith(">>")) {
								LOGGER.info("tags: " + split[i]);
								tags = tags + split[i] + " ";
							}
						}
						if(findByEtActionWithDefaultET.get(0).getEtTags().split(",").length != tags.split(" ").length) {
							throw new CustomException("Tags lengths Are not Match");
						}
						String[] splitTags = tags.split(" ");
						String splitTagsFromDB = findByEtActionWithDefaultET.get(0).getEtTags();
						for(int i = 0;i < splitTags.length;i++) {
							if(splitTagsFromDB.indexOf(splitTags[i]) == -1){
								throw new CustomException("Tags Are Not Present in Default Template");
							}
						}
						List<EmailTemplateEntity> findByEtAction = emailTemplateRepo.findByEtAction(emailTemplateEntity.getEtAction());
						if(findByEtAction.size()>0) {
							if(emailTemplateEntity.getIsPrimary() == 1) {
								findByEtAction.forEach(obj->{
									obj.setIsPrimary(0);
								});
								emailTemplateRepo.saveAll(findByEtAction);
							}
							EmailTemplateEntity newEmailTemplateEntity = findByIdAndDelete.get(0);
							newEmailTemplateEntity.setEtId(emailTemplateEntity.getEtId());
							newEmailTemplateEntity.setEtName(emailTemplateEntity.getEtName());
							newEmailTemplateEntity.setEtSubject(emailTemplateEntity.getEtSubject());
							newEmailTemplateEntity.setEtBody(emailTemplateEntity.getEtBody());
							newEmailTemplateEntity.setIsPrimary(emailTemplateEntity.getIsPrimary());
							newEmailTemplateEntity.setEtAction(emailTemplateEntity.getEtAction());
							newEmailTemplateEntity.setEtOrder(emailTemplateEntity.getEtOrder());
							newEmailTemplateEntity.setEtTags(emailTemplateEntity.getEtTags());
							newEmailTemplateEntity.setUpdatedOn(emailTemplateEntity.getUpdatedOn());
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
					if(findByIdAndDelete.get(1).getEtType().equals("DEFAULT")) {
						EmailTemplateEntity emailTemplateEntity = findById.get();
						emailTemplateEntity.setIsDeleted(0);
						EmailTemplateEntity save = emailTemplateRepo.save(emailTemplateEntity);
						if(save.equals(null)) {
							throw new CustomException("Data Not Save Try Again");
						}else {
							return new GlobalResponse("Data Save Successfully","SUCCESS");
						}	
					}else {
						throw new CustomException("Default Template Can't be Deleted");
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
				List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.findAllByDefaultTemplate("DEFAULT");
				if(findAllByIdAndDelete.size()<1) {
					throw new CustomException("No Data Present");
				}else {
					return findAllByIdAndDelete;
				}
			}else if(type.equals("template_for")){
				List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.findAllByDefaultET("DEFAULT");
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
	
	@GetMapping("/getPrimaryETByAction/{action}")
	public EmailTemplateEntity getPrimaryETByAction(@PathVariable("action") String action) {
		
		LOGGER.info("Inside - EmailTemplateController.emailTemplateGetAll()");
		try {

				List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.getPrimaryETByAction(action,1);
				if(findAllByIdAndDelete.size()<1) {
					throw new CustomException("No Data Present");
				}else {
					return findAllByIdAndDelete.get(0);
				}
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	@GetMapping("/viewTemplate/{id}")
	public EmailTemplateEntity viewTemplate(@PathVariable("id") Long id) {
		
		LOGGER.info("Inside - EmailTemplateController.viewTemplate()");
		try {
			List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(1, id);
			if(findByIdAndDelete.size()>0) {
				return findByIdAndDelete.get(0);
			}else {
				throw new CustomException("No Data Found");
			}
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	

}
