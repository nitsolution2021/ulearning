package org.ulearn.licenseservice.servises;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.entity.LicenseLogEntity;
import org.ulearn.licenseservice.exception.CustomException;
import org.ulearn.licenseservice.repository.LicenseLogRepo;
import org.ulearn.licenseservice.repository.LicenseRepo;
import org.ulearn.licenseservice.validation.FieldValidation;

@Service
public class LicenseService {
	
	@Autowired 
	public LicenseRepo LicenseRepo;
	
	@Autowired
	public FieldValidation fieldValidation;
	
	@Autowired
	public LicenseLogRepo licenseLogRepo;
	
	public GlobalResponse addLicense(LicenseEntity license) {
	
		try {
			
			
			if(fieldValidation.isEmpty(license.getInstId()) & fieldValidation.isEmpty(license.getLcName()) & fieldValidation.isEmpty(license.getLcCreatDate()) &
					fieldValidation.isEmpty(license.getLcType()) & fieldValidation.isEmpty(license.getLcStype()) &
					fieldValidation.isEmpty(license.getLcValidityType()) & fieldValidation.isEmpty(license.getLcValidityNum()) &
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
						licenseLogAdd.setLlValidityNum(save.getLcValidityNum());
						licenseLogAdd.setLlComment(save.getLcComment());
						licenseLogAdd.setLlStatus("Complete");
						licenseLogAdd.setCreatedOn(new Date());
						
						LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);
						
						
						if (!save.equals(null)) {
							return new GlobalResponse("Success","License Add successfully");
						} else {
							throw new CustomException("Data not store");
						}
					}
					else {
						throw new CustomException("A license have already assigned for ths institute..");
					}
				}
				else {
					throw new CustomException("Some required field are missing..");
				}
		}
		catch(Exception ex) {
			throw new CustomException(ex.getMessage());
		}
		
	}

	public GlobalResponse updateLicense(long licenseId, LicenseEntity licenseForUpdate) {
		// TODO Auto-generated method stub
		
		try {
			
			Optional<LicenseEntity> findById = this.LicenseRepo.findById(licenseId);
			if(findById.isPresent()) {
				if(findById.get().getLcId().equals(licenseId)) {
					LicenseEntity licenseUpdate = new LicenseEntity();
					
					licenseUpdate.setLcName(licenseForUpdate.getLcName());
					licenseUpdate.setLcId(licenseForUpdate.getLcId());
					licenseUpdate.setInstId(licenseForUpdate.getInstId());
					licenseUpdate.setLcCreatDate(licenseForUpdate.getLcCreatDate());
					licenseUpdate.setLcType(licenseForUpdate.getLcType());
					licenseUpdate.setLcStype(licenseForUpdate.getLcStype());
					licenseUpdate.setLcValidityType(licenseForUpdate.getLcValidityType());
					licenseUpdate.setLcValidityNum(licenseForUpdate.getLcValidityNum());
					licenseUpdate.setLcEndDate(licenseForUpdate.getLcEndDate());
					licenseUpdate.setLcComment(licenseForUpdate.getLcComment());
					licenseUpdate.setLcStatus(licenseForUpdate.getLcStatus());
//					licenseUpdate.setIsDeleted(1);
					licenseUpdate.setUpdatedOn(new Date());
					
					LicenseEntity save = LicenseRepo.save(licenseUpdate);
					
					if(!save.equals(null)) {
						return new GlobalResponse("Success","Update Successfull");
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

}
