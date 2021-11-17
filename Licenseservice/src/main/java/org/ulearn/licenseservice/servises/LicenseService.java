package org.ulearn.licenseservice.servises;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.licenseservice.controller.LicenseController;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
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
	
	public GlobalResponse addLicense(LicenseEntity license) {
	
		try {
			
			
			if(fieldValidation.isEmpty(license.getInstId()) & fieldValidation.isEmpty(license.getLcName()) & 
					fieldValidation.isEmpty(license.getLcCreatDate()) & fieldValidation.isEmpty(license.getLcType()) & 
					fieldValidation.isEmpty(license.getLcStype()) & fieldValidation.isEmpty(license.getLcValidityType()) & 
					fieldValidation.isEmpty(license.getLcValidityNum()) & fieldValidation.isEmpty(license.getLcEndDate())) {
					
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
						licenseLogAdd.setLlValidityNum(save.getLcValidityNum());
						licenseLogAdd.setLlComment(save.getLcComment());
						licenseLogAdd.setLlStatus("Complete");
						licenseLogAdd.setCreatedOn(new Date());
						
						LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);
						
						
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

	public GlobalResponse updateLicense(long licenseId, LicenseEntity licenseForUpdate) {
		// TODO Auto-generated method stub
		
		try {
			
			if(fieldValidation.isEmpty(licenseForUpdate.getInstId()) & fieldValidation.isEmpty(licenseForUpdate.getLcName()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcCreatDate()) & fieldValidation.isEmpty(licenseForUpdate.getLcType()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcStype()) & fieldValidation.isEmpty(licenseForUpdate.getLcValidityType()) & 
					fieldValidation.isEmpty(licenseForUpdate.getLcValidityNum()) & fieldValidation.isEmpty(licenseForUpdate.getLcEndDate())) {
			
//					Optional<LicenseEntity> findByInstId = LicenseRepo.findByInstId(licenseForUpdate.getInstId());
//					if(!findByInstId.isPresent()) {
					
					Optional<LicenseEntity> findById = this.LicenseRepo.findById(licenseId);
					if(findById.isPresent()) {
						if(findById.get().getLcId().equals(licenseId)) {
							LicenseEntity licenseUpdate = new LicenseEntity();
							
							//LOGGER.info(findById.get().getCreatedOn()+"");
							licenseUpdate.setLcName(licenseForUpdate.getLcName());
							licenseUpdate.setLcId(findById.get().getLcId());
							licenseUpdate.setInstId(licenseForUpdate.getInstId());
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
							licenseLogAdd.setLlValidityNum(save.getLcValidityNum());
							licenseLogAdd.setLlComment(save.getLcComment());
							licenseLogAdd.setLlStatus("Complete");
							licenseLogAdd.setCreatedOn(new Date());
							licenseLogAdd.setUpdatedOn(new Date());
							
							LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);
							
							
							if(!save.equals(null)) {
								return new GlobalResponse("SUCCESS","Update Successfull",200);
							}
							else {
								throw new CustomException("Upade not successfull");
							}
						}
						else {
							throw new CustomException("License id not matched");
						}
					}
					else {
						throw new CustomException("License not found for this Id  "+licenseId);
					}
//				}
//				else {
//					
//					throw new CustomException("A license have already assigned for ths institute..");
//				}
			}
			else {
			
				throw new CustomException("Some required value are missing, please check..");
			}
		}
		catch(Exception ex) {
			throw new CustomException(ex.getMessage());
		}
	}

	public List<LicenseEntity> getAllLicenseList() {
		// TODO Auto-generated method stub
		
		try {
			
			List<LicenseEntity> findAll = LicenseRepo.findAllIsNotDeleted();
			if(findAll.size()<1) {
				throw new CustomException("No license found");
			}
			else {
				return findAll;
			}
		} catch (Exception e) {
			// TODO: handle exception
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
						return new GlobalResponse("SUCCESS","Suspend date added for this license.",200);
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
