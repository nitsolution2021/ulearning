package org.ulearn.emailtemplateservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
				Optional<EmailTemplateEntity> findByEtName = emailTemplateRepo.findByEtName(emailTemplateEntity.getEtName());
				if(findByEtName.isPresent()) {
					throw new CustomException("The Template Name Already Exist");
				}
				List<EmailTemplateEntity> findByEtActionWithDefaultET = emailTemplateRepo.findByEtActionWithDefaultET(emailTemplateEntity.getEtAction(),"DEFAULT");
				String[] split = emailTemplateEntity.getEtBody().split(" ");
				String tags = "";
				for(int i = 0;i < split.length;i++) {
					if(split[i].startsWith("<<") && split[i].endsWith(">>")) {
						LOGGER.info("tags: " + split[i]);
						tags = tags + split[i] + " ";
					}
				}
				if(findByEtActionWithDefaultET.size() <1) {
					throw new CustomException("The Custome Template Action is Not Present in Default Action");
				}
				if(findByEtActionWithDefaultET.get(0).getEtTags().split(",").length < tags.split(" ").length) {
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
					newEmailTemplateEntity.setIsDeleted(0);
					newEmailTemplateEntity.setEtOrder(emailTemplateEntity.getEtOrder());
					newEmailTemplateEntity.setEtTags(emailTemplateEntity.getEtTags());
					newEmailTemplateEntity.setCreatedOn(new Date());
//					newEmailTemplateEntity.setUpdatedOn(emailTemplateEntity.getUpdatedOn());
//					LOGGER.info(emailTemplateEntity.toString());
					EmailTemplateEntity save = emailTemplateRepo.save(newEmailTemplateEntity);
					if(save.equals(null)) {
						throw new CustomException("Data Not Save Try Again");
					}else {
						return new GlobalResponse("Data Save Successfully","SUCCESS",200);
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
	public GlobalResponse emailTemplateUpdate(@PathVariable Long id, @RequestBody EmailTemplateEntity emailTemplateEntity) {
		LOGGER.info("Inside - EmailTemplateController.emailTemplateUpdate()");
		try {
			if(fieldValidation.isEmpty(emailTemplateEntity.getEtName()) && 
					fieldValidation.isEmpty(emailTemplateEntity.getEtSubject()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtBody()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getEtAction()) &&
					fieldValidation.isEmpty(emailTemplateEntity.getIsPrimary())) {
				Optional<EmailTemplateEntity> findByEtName = emailTemplateRepo.findByEtName(emailTemplateEntity.getEtName());
				if(findByEtName.isPresent() && !findByEtName.get().getEtName().equals(emailTemplateEntity.getEtName())) {
					throw new CustomException("The Template Name Already Exist");
				}
				Optional<EmailTemplateEntity> findById = emailTemplateRepo.findById(id);
				if(findById.isPresent()) {
					if(findById.get().getEtType().equals("DEFAULT")) {
						throw new CustomException("Default Template Can't Delete");
					}
					List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(0, id);
					if(findByIdAndDelete.size()>0) {
						List<EmailTemplateEntity> findByEtActionWithDefaultET = emailTemplateRepo.findByEtActionWithDefaultET(emailTemplateEntity.getEtAction(),"DEFAULT");
						String[] split = emailTemplateEntity.getEtBody().split(" ");
						String tags = "";
						for(int i = 0;i < split.length;i++) {
							if(split[i].startsWith("<<") && split[i].endsWith(">>")) {
								LOGGER.info("tags: " + split[i]);
								tags = tags + split[i] + " ";
							}
						}
						if(findByEtActionWithDefaultET.get(0).getEtTags().split(",").length < tags.split(" ").length) {
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
							newEmailTemplateEntity.setEtId(id);
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
								return new GlobalResponse("Data Save Successfully","SUCCESS",200);
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
	public GlobalResponse emailTemplateDelete(@PathVariable("id") Long id) {
		
		LOGGER.info("Inside - EmailTemplateController.emailTemplateDelete()");
		try {
			Optional<EmailTemplateEntity> findById = emailTemplateRepo.findById(id);
			if(findById.isPresent()) {
				List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(0, id);
				if(findByIdAndDelete.size()>0) {
					if(findByIdAndDelete.get(0).getEtType().equals("CUSTOM")) {
						EmailTemplateEntity emailTemplateEntity = findById.get();
						emailTemplateEntity.setIsDeleted(1);
						EmailTemplateEntity save = emailTemplateRepo.save(emailTemplateEntity);
						if(save.equals(null)) {
							throw new CustomException("Data Not Save Try Again");
						}else {
							return new GlobalResponse("Data Save Successfully","SUCCESS",200);
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
	
	@GetMapping("/getAll/template")
	public Map<String, Object> emailTemplateGetAll(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		
		LOGGER.info("Inside - EmailTemplateController.emailTemplateGetAll()");
		try {
			int Limit = 10;
				Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
						sortBy.orElse("createdOn"));
				Page<EmailTemplateEntity> findAll = emailTemplateRepo.findAllAndDelete(0,pagingSort);
				
				int totalPage=findAll.getTotalPages()-1;
				if(totalPage < 0) {
					totalPage=0;
				}
				Map<String, Object> response = new HashMap<>();
				response.put("data", findAll.getContent());
				response.put("currentPage", findAll.getNumber());
				response.put("total", findAll.getTotalElements());
				response.put("totalPage", totalPage);
				response.put("perPage", findAll.getSize());
				response.put("perPageElement", findAll.getNumberOfElements());

				if (findAll.getSize() < 1) {
					throw new CustomException("Template Not Found!");
				} else {
					return response;
				}

//				List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.findAllAndDelete(1);
//				if(findAllByIdAndDelete.size()<1) {
//					throw new CustomException("No Data Present");
//				}else {
//					return findAllByIdAndDelete;
//				}
				
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@GetMapping(value = { "/getAll/template/{page}/{limit}/{sortName}/{sort}" })
	public Map<String, Object> emailTemplateGetAllPagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@RequestParam(defaultValue = "") Optional<String>keyword, @RequestParam Optional<String> sortBy) {
		
		LOGGER.info("Inside - EmailTemplateController.emailTemplateGetAllPagination()");
		try {
			Pageable pagingSort = null;

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<EmailTemplateEntity> findAll = null;
			if (keyword.isPresent()) {
				findAll = emailTemplateRepo.Search(keyword.get(), pagingSort);
			} else {
				findAll = emailTemplateRepo.findAll(pagingSort);
			}
			int totalPage=findAll.getTotalPages()-1;
			if(totalPage < 0) {
				totalPage=0;
			}
			
			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() < 1) {
				throw new CustomException("Template Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getAll/template_for")
	public List<EmailTemplateEntity> defaultEmailTemplateGetAll() {
		LOGGER.info("Inside - EmailTemplateController.emailTemplateGetAll()");
		try {
			List<EmailTemplateEntity> findAllByIdAndDelete = emailTemplateRepo.findAllByDefaultTemplate("DEFAULT");
			if(findAllByIdAndDelete.size()<1) {
				throw new CustomException("No Data Present");
			}else {
				return findAllByIdAndDelete;
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
			List<EmailTemplateEntity> findByIdAndDelete = emailTemplateRepo.findByIdAndDelete(0, id);
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
