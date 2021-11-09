package org.ulearn.licenseservice.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.repository.LicenseRepo;

@Service
public class LicenseService {
	
	@Autowired 
	public LicenseRepo LicenseRepo;
	
	public void addLicense( LicenseEntity license ) {
	
		try {
			
			LicenseEntity licenseAdd = new LicenseEntity();
			
			
			
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

}
