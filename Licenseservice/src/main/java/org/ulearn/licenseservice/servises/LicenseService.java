package org.ulearn.licenseservice.servises;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.exception.CustomeException;
import org.ulearn.licenseservice.repository.LicenseRepo;
import org.ulearn.licenseservice.validation.FieldValidation;

@Service
public class LicenseService {
	
	@Autowired 
	public LicenseRepo LicenseRepo;
	
	@Autowired
	public FieldValidation fieldValidation;
	
	public GlobalResponse addLicense(LicenseEntity license) {
	
		try {
				
				LicenseEntity licenseAdd = new LicenseEntity();
				
				licenseAdd.setLcName(license.getLcName());
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
				
				LicenseEntity save = LicenseRepo.save(licenseAdd);
				
				if (!save.equals(null)) {
					return new GlobalResponse("Success","License Add successfully");
				} else {
					throw new CustomeException("Data not store");
				}
		}
		catch(Exception ex) {
			throw new CustomeException(ex.getMessage());
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
					licenseUpdate.setLcCreatDate(licenseForUpdate.getLcCreatDate());
					licenseUpdate.setLcType(licenseForUpdate.getLcType());
					licenseUpdate.setLcStype(licenseForUpdate.getLcStype());
					licenseUpdate.setLcValidityType(licenseForUpdate.getLcValidityType());
					licenseUpdate.setLcValidityNum(licenseForUpdate.getLcValidityNum());
					licenseUpdate.setLcEndDate(licenseForUpdate.getLcEndDate());
					licenseUpdate.setLcComment(licenseForUpdate.getLcComment());
					licenseUpdate.setLcStatus(licenseForUpdate.getLcStatus());
					licenseUpdate.setUpdatedOn(new Date());
					
					LicenseEntity save = LicenseRepo.save(licenseUpdate);
					
					if(!save.equals(null)) {
						return new GlobalResponse("Success","Update Successfull");
					}
					else {
						throw new CustomeException("Upade not successfull");
					}
				}
				else {
					throw new CustomeException("License id not matched");
				}
			}
			else {
				throw new CustomeException("License not found for this Id"+licenseId);
			}
		}
		catch(Exception ex) {
			throw new CustomeException(ex.getMessage());
		}
	}

	public List<LicenseEntity> getAllLicenseList() {
		// TODO Auto-generated method stub
		
		try {
			
			List<LicenseEntity> findAll = LicenseRepo.findAllIsNotDeleted();
			if(findAll.size()<1) {
				throw new CustomeException("No license found");
			}
			else {
				return findAll;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomeException(e.getMessage());
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
				throw new CustomeException("No license found for this id");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomeException(e.getMessage());
		}
		
	}

}
