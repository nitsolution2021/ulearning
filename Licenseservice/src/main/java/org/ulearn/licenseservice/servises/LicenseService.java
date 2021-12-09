package org.ulearn.licenseservice.servises;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class LicenseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicenseController.class);
	
	@Autowired 
	public LicenseRepo licenseRepo;
	
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
					
					Optional<LicenseEntity> findByInstId = licenseRepo.findByInstId(license.getInstId());
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
						LicenseEntity save = licenseRepo.save(licenseAdd);
						
						//Set data for license log table
						
						LicenseLogEntity licenseLogAdd = new LicenseLogEntity();
						
						licenseLogAdd.setLcIdFk(save.getLcId());
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
						
						String emailId="";
						String amdFname="";
						String amdLname="";
						try {
							
							Unirest.setTimeouts(0, 0);
							 HttpResponse<JsonNode> asJson = Unirest.get("http://65.1.66.115:8087/dev/institute/view/"+ save.getInstId())
							  .header("Authorization", token)
							  .asJson();
							 org.json.JSONObject object = asJson.getBody().getObject();
							 emailId =object.getString("instEmail");
							 JSONArray jsonArray = object.getJSONArray("instituteAdmin");
							 org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
							 amdFname=jsonObject.getString("amdFname");
							 amdLname = jsonObject.getString("amdLname");
//							 LOGGER.info("Inside the LicenseController Update License"+object.getString("instEmail"));
						}
						catch(Exception e) {
							throw new CustomException(e.getMessage());
							
						}
						
						
						String ETSubject;
						String ETBody;
						String ETTargetName;
						String ETNameReplacement;
						String processedName;
						//HttpEntity request = new HttpEntity(headers);
						
						try {
							
							ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange("http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Create",HttpMethod.GET, request, LicenseGlobalEntity.class);
							 ETSubject = responseEmailTemp.getBody().getEtSubject();
							 ETBody = responseEmailTemp.getBody().getEtBody();

							 ETTargetName = "__$name$__";
							
							 ETNameReplacement = amdFname +" "+ amdLname;

							 processedName = ETBody.replace(ETTargetName, ETNameReplacement);
						}
						catch(Exception e) {
							if(!save.equals(null)) {
								throw new CustomException("License have added but there is a problem in emailTemplate view.please check.");
							}
							else {
								throw new CustomException("Error from mail template.");
							}
							
						}
						

						try {
							
							JSONObject requestJson = new JSONObject();
							requestJson.put("senderMailId", emailId);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", processedName);
							requestJson.put("enableHtml", true);

							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
							ResponseEntity<String> response = new RestTemplate().postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
						}
						catch(Exception e) {
							if(!save.equals(null)) {
								throw new CustomException("License have added but there is a problem in sendMail.please check.");
							}
							else {
								throw new CustomException("Error from sendMail.");
							}
							
						}
						

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

	public GlobalResponse updateLicense(int licenseId, LicenseEntity licenseForUpdate, String token) {
		// TODO Auto-generated method stub
		
		try {
			
			if(fieldValidation.isEmpty(licenseForUpdate.getInstId()) & fieldValidation.isEmpty(licenseForUpdate.getLcName()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcCreatDate()) & fieldValidation.isEmpty(licenseForUpdate.getLcType()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcStype()) & fieldValidation.isEmpty(licenseForUpdate.getLcValidityType()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcValidityNum()) & fieldValidation.isEmpty(licenseForUpdate.getLcEndDate())) {
			

					
					Optional<LicenseEntity> findById = this.licenseRepo.findById(licenseId);
					
					if(findById.isPresent()) {
							
							Long instituteId= null;
							if( findById.get().getInstId().equals(licenseForUpdate.getInstId())) {
								 instituteId = licenseForUpdate.getInstId();
							}
							else {
								Optional<LicenseEntity> findByInstId = licenseRepo.findByInstId(licenseForUpdate.getInstId());
								if(!findByInstId.isPresent()) {
									instituteId = licenseForUpdate.getInstId();
								}
								else {
								
									throw new CustomException("A license have already assigned for this institute.please select another institute.");
								}
							}
							
							
							LicenseEntity licenseUpdate = new LicenseEntity();
							
							licenseUpdate.setLcName(licenseForUpdate.getLcName());
							licenseUpdate.setLcId(licenseId);
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
							
							LicenseEntity save = licenseRepo.save(licenseUpdate);					
							
							//Set data for license log table
							
							LicenseLogEntity licenseLogAdd = new LicenseLogEntity();
							
							licenseLogAdd.setLcIdFk(save.getLcId());
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
							String emailId;
							String amdFname;
							String amdLname;
							try {
								Unirest.setTimeouts(0, 0);
								 HttpResponse<JsonNode> asJson = Unirest.get("http://65.1.66.115:8087/dev/institute/view/"+ save.getInstId())
								  .header("Authorization", token)
								  .asJson();
								 org.json.JSONObject object = asJson.getBody().getObject();
								 emailId =object.getString("instEmail");
								 JSONArray jsonArray = object.getJSONArray("instituteAdmin");
								 org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
								 amdFname=jsonObject.getString("amdFname");
								 amdLname = jsonObject.getString("amdLname");	
							}
							catch(Exception e) {
								if(!save.equals(null)) {
									throw new CustomException("License have updated but there is a problem in institute view.please check.");
								}
								else {
									throw new CustomException("Error from institute view.");
								}
								
							}
							
							
							String ETSubject;
							String ETBody;
							String ETTargetName;
							String ETNameReplacement;
							String processedName;
							//HttpEntity request = new HttpEntity(headers);
							
							try {
								
								ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange("http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Create",HttpMethod.GET, request, LicenseGlobalEntity.class);
								 ETSubject = responseEmailTemp.getBody().getEtSubject();
								 ETBody = responseEmailTemp.getBody().getEtBody();

								 ETTargetName = "__$name$__";
								
								 ETNameReplacement = amdFname +" "+ amdLname;

								 processedName = ETBody.replace(ETTargetName, ETNameReplacement);
							}
							catch(Exception e) {
								if(!save.equals(null)) {
									throw new CustomException("License have updated but there is a problem in emailTemplate view.please check.");
								}
								else {
									throw new CustomException("Error from mail template.");
								}
								
							}
							

							try {
								
								JSONObject requestJson = new JSONObject();
								requestJson.put("senderMailId", emailId);
								requestJson.put("subject", ETSubject);
								requestJson.put("body", processedName);
								requestJson.put("enableHtml", true);

								HttpEntity<String> entity = new HttpEntity(requestJson, headers);
								ResponseEntity<String> response = new RestTemplate().postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
							}
							catch(Exception e) {
								if(!save.equals(null)) {
									throw new CustomException("License have updated but there is a problem in sendMail.please check.");
								}
								else {
									throw new CustomException("Error from sendMail.");
								}
								
							}
							
							
							
							
							if(!save.equals(null)) {
								return new GlobalResponse("SUCCESS","License Update Successfull",200);
							}
							else {
								throw new CustomException("Update not successfull");
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
			Page<LicenseEntity> findAll = licenseRepo.findAll(pagingSort);
			
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
			String sort,int isDeleted, Optional<String> keyword) {
		
		try {
				Pageable pagingSort = null;
				int CountData=(int) licenseRepo.count();							
				if(limit==0) {
					limit=CountData;
				}
				if (sort.equals("ASC")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
				} else {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				}
	
				Page<LicenseEntity> findAll = null;
				
				if (keyword.get().isEmpty()) {
				
					findAll = licenseRepo.findByAllLicense(isDeleted,pagingSort);
					
				} else {
					
					findAll = licenseRepo.Search(keyword.get(), isDeleted,pagingSort);
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

	public Optional<LicenseEntity> getLicense(int lcId) {
		// TODO Auto-generated method stub
		
		try {
			
			Optional<LicenseEntity> findById = licenseRepo.findById(lcId);
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

	public GlobalResponse addSuspendLicense(int lcId, LicenseLogEntity licenseLogEntitySuspend,String token) {
		// TODO Auto-generated method stub
		
		try {
			
			if(fieldValidation.isEmpty(licenseLogEntitySuspend.getLlEdate()) 
					& fieldValidation.isEmpty(licenseLogEntitySuspend.getLlComment())) {
				
				LicenseEntity findById = this.licenseRepo.getById(lcId);			
				if(findById.getIsActive() != 0) {
				
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
					
						String lStatus = "Suspending";					
						findById.setLcStatus(lStatus);
						licenseRepo.save(findById);
						
//					For sending mail	
						HttpHeaders headers = new HttpHeaders();
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);
						
						HttpEntity request=new HttpEntity(headers);
						
						String emailId="";
						String amdFname="";
						String amdLname="";
						try {
							
							Unirest.setTimeouts(0, 0);
							 HttpResponse<JsonNode> asJson = Unirest.get("http://65.1.66.115:8087/dev/institute/view/"+ findById.getInstId())
							  .header("Authorization", token)
							  .asJson();
							 org.json.JSONObject object = asJson.getBody().getObject();
							 emailId =object.getString("instEmail");
							 JSONArray jsonArray = object.getJSONArray("instituteAdmin");
							 org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
							 amdFname=jsonObject.getString("amdFname");
							 amdLname = jsonObject.getString("amdLname");
//							 LOGGER.info("Inside the LicenseController Update License"+object.getString("instEmail"));
						}
						catch(Exception e) {
							throw new CustomException(e.getMessage());
							
						}
						
						
						String ETSubject;
						String ETBody;
						String ETTargetName;
						String licenseSuspendDate;
						String ETNameReplacement;
						String ETDateReplacement;
						String processedDate;
						String processedName;
						//HttpEntity request = new HttpEntity(headers);
						
						try {
							
							ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange("http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Add_Suspend_License",HttpMethod.GET, request, LicenseGlobalEntity.class);
							 ETSubject = responseEmailTemp.getBody().getEtSubject();
							 ETBody = responseEmailTemp.getBody().getEtBody();

							 ETTargetName = "__$name$__";
							 licenseSuspendDate = "__$date$__";
							
							 ETNameReplacement = amdFname +" "+ amdLname;
							 ETDateReplacement = licenseLogEntitySuspend.getLlEdate().toString();
							 processedDate = ETBody.replace(licenseSuspendDate, ETDateReplacement);
							 processedName = processedDate.replace(ETTargetName, ETNameReplacement);
							 
						}
						catch(Exception e) {

								throw new CustomException("Error from mail template.");
						}
						

						try {
							
							JSONObject requestJson = new JSONObject();
							requestJson.put("senderMailId", emailId);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", processedName);
							requestJson.put("enableHtml", true);

							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
							ResponseEntity<String> response = new RestTemplate().postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
						}
						catch(Exception e) {
							
								throw new CustomException("Error from sendMail.");
							
						}
						
//						End send mail
					
					if (!save.equals(null)) {
						return new GlobalResponse("SUCCESS","This license will be suspend for the selected date.",200);
					}
					else {
						throw new CustomException("Suspend date not add.. ");
					}
				}
				}
				else {
					throw new CustomException("This license has not active.");
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
