package org.ulearn.packageservice.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.repo.PackageRepo;

@RestController
@RequestMapping("/package") 
public class PackageContoller {
	
	
	@Autowired
	private PackageRepo packageRepo;
	
	@GetMapping("/getPackageAll")
	public List<PackageEntity> getPackageAll(){
		
		List<PackageEntity> findAll = packageRepo.findAll();
		
		if(findAll.size()<1) {
			
		}
		
		return findAll;
	}

}
