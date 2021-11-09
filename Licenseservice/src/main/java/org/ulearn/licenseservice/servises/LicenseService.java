package org.ulearn.licenseservice.servises;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.repository.LicenseRepo;
import org.ulearn.licenseservice.validation.FieldValidation;

@Service
public class LicenseService {
	
	@Autowired 
	public LicenseRepo LicenseRepo;
	
	@Autowired
	public FieldValidation fieldValidation;
	
	public GlobalResponse addLicense( LicenseEntity license ) {
	
		try {
				
				LicenseEntity licenseAdd = new LicenseEntity();
				
				licenseAdd.setLcCreatDate(license.getLcCreatDate());
				licenseAdd.setLcType(license.getLcType());
				licenseAdd.setLcStype(license.getLcStype());
				licenseAdd.setLcValidityType(license.getLcValidityType());
				licenseAdd.setLcValidityNum(license.getLcValidityNum());
				licenseAdd.setLcEndDate(license.getLcEndDate());
				licenseAdd.setHsnId(license.getHsnId());
				licenseAdd.setLcComment(license.getLcComment());
				licenseAdd.setLcStatus(license.getLcStatus());
				licenseAdd.setIsActive(1);
				licenseAdd.setIsDeleted(0);
				licenseAdd.setCreatedOn(new Date());
				
				LicenseRepo.save(licenseAdd);
				
				return new GlobalResponse("Success","License Add successfully");
			
		}
		catch(Exception ex) {
			return new GlobalResponse("Error", "Catch error");
		}
		
	}

}
