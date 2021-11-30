package org.ulearn.licenseservice.servises;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.ulearn.licenseservice.controller.LicenseController;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.entity.LicenseGlobalEntity;
import org.ulearn.licenseservice.entity.LicenseLogEntity;
import org.ulearn.licenseservice.exception.CustomException;
import org.ulearn.licenseservice.repository.LicenseLogRepo;
import org.ulearn.licenseservice.repository.LicenseRepo;
import org.ulearn.licenseservice.validation.FieldValidation;

@Service
public class LicenseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicenseController.class);
	
	@Autowired 
	public LicenseRepo LicenseRepo;
	
	@Autowired
	public FieldValidation fieldValidation;
	
	@Autowired
	public LicenseLogRepo licenseLogRepo;
	
	public GlobalResponse addLicense(LicenseEntity license,String token) {
	
		try {
			
			
			if(
					fieldValidation.isEmpty(license.getInstId()) & 
					fieldValidation.isEmpty(license.getLcName()) & 
					fieldValidation.isEmpty(license.getLcCreatDate()) & fieldValidation.isEmpty(license.getLcType()) & 
					fieldValidation.isEmpty(license.getLcStype()) & fieldValidation.isEmpty(license.getLcValidityType()) & 
					fieldValidation.isEmpty(license.getLcValidityNum()) & 
					fieldValidation.isEmpty(license.getLcEndDate())) {
					
					Optional<LicenseEntity> findByInstId = LicenseRepo.findByInstId(license.getInstId());
					if(!findByInstId.isPresent()) {
						
					
						LicenseEntity licenseAdd = new LicenseEntity();
						
						//Set date for license table
						licenseAdd.setLcName(license.getLcName());
						licenseAdd.setInstId(license.getInstId());
						licenseAdd.setLcCreatDate(license.getLcCreatDate());
						licenseAdd.setLcType(license.getLcType());
						licenseAdd.setLcStype(license.getLcStype());
						licenseAdd.setLcValidityType(license.getLcValidityType());
						licenseAdd.setLcValidityNum(license.getLcValidityNum());
						licenseAdd.setLcEndDate(license.getLcEndDate());
						licenseAdd.setLcComment(license.getLcComment());
						licenseAdd.setLcStatus("Complete");
						licenseAdd.setIsActive(1);
						licenseAdd.setIsDeleted(0);
						licenseAdd.setCreatedOn(new Date());
						
						
						//Insert data into license table
						LicenseEntity save = LicenseRepo.save(licenseAdd);
						
						//Set data for license log table
						
						LicenseLogEntity licenseLogAdd = new LicenseLogEntity();
						
						licenseLogAdd.setLcIdFk(save.getLcId().intValue());
						licenseLogAdd.setLlAction("Add license");
						licenseLogAdd.setLlValidityType(save.getLcValidityType());
						licenseLogAdd.setLlValidityNum(Integer.parseInt(save.getLcValidityNum()+""));
						licenseLogAdd.setLlComment(save.getLcComment());
						licenseLogAdd.setLlStatus("Complete");
						licenseLogAdd.setCreatedOn(new Date());
						
						LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);
						
						HttpHeaders headers = new HttpHeaders();
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);
						
						HttpEntity request=new HttpEntity(headers);
						ResponseEntity<LicenseGlobalEntity> responseEmailTempForInstNameEmail=new RestTemplate().exchange("http://65.1.66.115:8087/dev/institute/view/"+save.getInstId(),  HttpMethod.GET, request, LicenseGlobalEntity.class);
						String emailId = responseEmailTempForInstNameEmail.getBody().getInstituteAdmin().getAmdEmail();
						String amdFname = responseEmailTempForInstNameEmail.getBody().getInstituteAdmin().getAmdFname();
						String amdLname = responseEmailTempForInstNameEmail.getBody().getInstituteAdmin().getAmdLname();
						
						
						//HttpEntity request = new HttpEntity(headers);
						ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange("http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Create",
								HttpMethod.GET, request, LicenseGlobalEntity.class);
						String ETSubject = responseEmailTemp.getBody().getEtSubject();
						String ETBody = responseEmailTemp.getBody().getEtBody();

						String ETTargetName = "<<_name_>>";
						
						String ETNameReplacement = amdFname +" "+ amdLname;

						String processedName = ETBody.replace(ETTargetName, ETNameReplacement);

						JSONObject requestJson = new JSONObject();
						requestJson.put("senderMailId", emailId);
						requestJson.put("subject", ETSubject);
						requestJson.put("body", processedName);
						requestJson.put("enableHtml", true);

						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response = new RestTemplate().postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
						
						

						if (!save.equals(null)) {
							return new GlobalResponse("SUCCESS","License Added successfully",200);
						} else {
							throw new CustomException("Data not store");
						}
					}
					else {
						throw new CustomException("A license have already assigned for ths institute..");
					}
				}
				else {
					throw new CustomException("Some required value are missing..");
				}
		}
		catch(Exception ex) {
			throw new CustomException(ex.getMessage());
		}
		
	}

	public GlobalResponse updateLicense(long licenseId, LicenseEntity licenseForUpdate, String token) {
		// TODO Auto-generated method stub
		Long instituteId;
		try {
			
			if(fieldValidation.isEmpty(licenseForUpdate.getInstId()) & fieldValidation.isEmpty(licenseForUpdate.getLcName()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcCreatDate()) & fieldValidation.isEmpty(licenseForUpdate.getLcType()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcStype()) & fieldValidation.isEmpty(licenseForUpdate.getLcValidityType()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcValidityNum()) & fieldValidation.isEmpty(licenseForUpdate.getLcEndDate())) {
			

					
					Optional<LicenseEntity> findById = this.LicenseRepo.findById(licenseId);
					if(findById.isPresent()) {
						if(findById.get().getLcId().equals(licenseId)) {
							
								
							LicenseEntity licenseUpdate = new LicenseEntity();
							
							if( findById.get().getInstId().equals(licenseForUpdate.getInstId())) {
								 instituteId = licenseForUpdate.getInstId();
							}
							else {
								Optional<LicenseEntity> findByInstId = LicenseRepo.findByInstId(licenseForUpdate.getInstId());
								if(!findByInstId.isPresent()) {
									instituteId = licenseForUpdate.getInstId();
								}
								else {
								
									throw new CustomException("A license have already assigned for this institute.please select another institute.");
								}
							}
							
							//LOGGER.info(findById.get().getCreatedOn()+"");
							licenseUpdate.setLcName(licenseForUpdate.getLcName());
							licenseUpdate.setLcId(findById.get().getLcId());
							licenseUpdate.setInstId(instituteId);
							licenseUpdate.setLcCreatDate(licenseForUpdate.getLcCreatDate());
							licenseUpdate.setLcType(licenseForUpdate.getLcType());
							licenseUpdate.setLcStype(licenseForUpdate.getLcStype());
							licenseUpdate.setLcValidityType(licenseForUpdate.getLcValidityType());
							licenseUpdate.setLcValidityNum(licenseForUpdate.getLcValidityNum());
							licenseUpdate.setLcEndDate(licenseForUpdate.getLcEndDate());
							licenseUpdate.setLcComment(licenseForUpdate.getLcComment());
							licenseUpdate.setLcStatus(findById.get().getLcStatus());
							licenseUpdate.setIsActive(findById.get().getIsActive());
							licenseUpdate.setIsDeleted(findById.get().getIsDeleted());
							licenseUpdate.setCreatedOn(findById.get().getCreatedOn());
							licenseUpdate.setUpdatedOn(new Date());
							
							LicenseEntity save = LicenseRepo.save(licenseUpdate);
							
							//Set data for license log table
							
							LicenseLogEntity licenseLogAdd = new LicenseLogEntity();
							
							licenseLogAdd.setLcIdFk(save.getLcId().intValue());
							licenseLogAdd.setLlAction("Update license");
							licenseLogAdd.setLlValidityType(save.getLcValidityType());
							licenseLogAdd.setLlValidityNum(Integer.parseInt(save.getLcValidityNum()+""));
							licenseLogAdd.setLlComment(save.getLcComment());
							licenseLogAdd.setLlStatus("Complete");
							licenseLogAdd.setCreatedOn(new Date());
							licenseLogAdd.setUpdatedOn(new Date());
							
							LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);
							
							HttpHeaders headers = new HttpHeaders();
							headers.set("Authorization", token);
							headers.setContentType(MediaType.APPLICATION_JSON);
							
							HttpEntity request=new HttpEntity(headers);
							ResponseEntity<LicenseGlobalEntity> responseEmailTempForInstNameEmail=new RestTemplate().exchange("http://65.1.66.115:8087/dev/institute/view/"+save.getInstId(),  HttpMethod.GET, request, LicenseGlobalEntity.class);
							String emailId = responseEmailTempForInstNameEmail.getBody().getInstituteAdmin().getAmdEmail();
							String amdFname = responseEmailTempForInstNameEmail.getBody().getInstituteAdmin().getAmdFname();
							String amdLname = responseEmailTempForInstNameEmail.getBody().getInstituteAdmin().getAmdLname();
							
							
							//HttpEntity request = new HttpEntity(headers);
							ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange("http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Update",
									HttpMethod.GET, request, LicenseGlobalEntity.class);
							String ETSubject = responseEmailTemp.getBody().getEtSubject();
							String ETBody = responseEmailTemp.getBody().getEtBody();

							String ETTargetName = "<<_name_>>";
							
							String ETNameReplacement = amdFname +" "+ amdLname;

							String processedName = ETBody.replace(ETTargetName, ETNameReplacement);

							JSONObject requestJson = new JSONObject();
							requestJson.put("senderMailId", emailId);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", processedName);
							requestJson.put("enableHtml", true);

							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
							ResponseEntity<String> response = new RestTemplate().postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
							
							
							
							if(!save.equals(null)) {
								return new GlobalResponse("SUCCESS","Update Successfull",200);
							}
							else {
								throw new CustomException("Update not successfull");
							}
							
						}
						else {
							throw new CustomException("License id not matched");
						}
					}
					else {
						throw new CustomException("License not found for this Id  "+licenseId);
					}
			}
			else {
			
				throw new CustomException("Some required value are missing, please check..");
			}
		}
		catch(Exception ex) {
			throw new CustomException(ex.getMessage());
		}
	}

	public Map<String, Object> getAllLicenseList(Optional<Integer> page,@RequestParam Optional<String> sortBy) {
		// TODO Auto-generated method stub
		
		int Limit = 10;
		try {
			
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
					sortBy.orElse("createdOn"));
			Page<LicenseEntity> findAll = LicenseRepo.findAll(pagingSort);
			
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
				throw new CustomException("License Not Found!");
			} else {
				return response;
			}
//			
//			List<LicenseEntity> findAll = LicenseRepo.findAllIsNotDeleted();
//			if(findAll.size()<1) {
//				throw new CustomException("No license found");
//			}
//			else {
//				return findAll;
//			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}
	
	public Map<String, Object> forGetLicensePagination(int page, int limit, Optional<String> sortBy, String sortName,
			String sort, Optional<String> keyword) {
		// TODO Auto-generated method stub
		try {
				Pageable pagingSort = null;
	
				if (sort.equals("ASC")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
				} else {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				}
	
				Page<LicenseEntity> findAll = null;
				if (keyword.isPresent()) {
					findAll = LicenseRepo.Search(keyword.get(), pagingSort);
				} else {
					findAll = LicenseRepo.findByAllLicense(pagingSort);
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
					throw new CustomException("License Not Found!");
				} else {
					return response;
				}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Optional<LicenseEntity> getLicense(long lcId) {
		// TODO Auto-generated method stub
		
		try {
			
			Optional<LicenseEntity> findById = LicenseRepo.findById(lcId);
			if(findById.isPresent()) {
				return findById;
			}
			else {
				throw new CustomException("No license found for this id");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
	}

	public GlobalResponse addSuspendLicense(int lcId, LicenseLogEntity licenseLogEntitySuspend) {
		// TODO Auto-generated method stub
		
		try {
			
			if(fieldValidation.isEmpty(licenseLogEntitySuspend.getLlEdate()) 
					& fieldValidation.isEmpty(licenseLogEntitySuspend.getLlComment())) {
				
				Optional<LicenseLogEntity> findByLcId = licenseLogRepo.findByLcId(lcId,"Add suspend");
				if (findByLcId.isPresent()) {
					throw new CustomException("This license have already suspended..");
				}
				else {
					
					LicenseLogEntity licenseLogEntityForSuspend = new LicenseLogEntity();
					licenseLogEntityForSuspend.setLcIdFk(lcId);
					licenseLogEntityForSuspend.setLlAction("Add suspend");
					licenseLogEntityForSuspend.setLlEdate(licenseLogEntitySuspend.getLlEdate());
					licenseLogEntityForSuspend.setLlComment(licenseLogEntitySuspend.getLlComment());
					licenseLogEntityForSuspend.setLlStatus("Complete");
					licenseLogEntityForSuspend.setCreatedOn(new Date());
					
					LicenseLogEntity save = licenseLogRepo.save(licenseLogEntityForSuspend);
					
					if (!save.equals(null)) {
						return new GlobalResponse("SUCCESS","This license has been suspended for this date.",200);
					}
					else {
						throw new CustomException("Suspend date not add.. ");
					}
				}
			}
			else {
				throw new CustomException("Some required value are missing, please check..");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}

	

}
