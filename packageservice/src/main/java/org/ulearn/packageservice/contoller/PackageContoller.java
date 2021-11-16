package org.ulearn.packageservice.contoller;

import java.util.List;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.ulearn.packageservice.entity.GlobalResponse;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.entity.PackageLogEntity;
import org.ulearn.packageservice.exception.CustomException;
import org.ulearn.packageservice.repo.LoginRepository;
import org.ulearn.packageservice.repo.PackageLogRepo;
import org.ulearn.packageservice.repo.PackageRepo;
import org.ulearn.packageservice.validation.FieldValidation;

@RestController
@RequestMapping("/package")
public class PackageContoller {

	@Autowired
	private PackageRepo packageRepo;

	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private PackageLogRepo packageLogRepo;
	
	
	private static final Logger log = LoggerFactory.getLogger(PackageContoller.class);
	
	@GetMapping("/list")
	public List<PackageEntity> listAlldata() {
		try {
			log.info("Inside-PackageController.list");
			List<PackageEntity> allPackagedata = packageRepo.findAll();
			if (allPackagedata != null) {
				return allPackagedata;
				// return (Optional<PackageEntity>) allPackagedata;
			} else {
				throw new CustomException("No data found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/view/{pkId}")
	public Optional<PackageEntity> viewPackagedetails(@PathVariable() long pkId ) {
		
		try {
			log.info("Inside-PackageController.view");
			PackageEntity packageData=packageRepo.getById(pkId);
			PackageLogEntity loggingData=new PackageLogEntity();
			loggingData.setPkId(pkId);
			loggingData.setPlAction("view");
			loggingData.setPlAdate(new Date());
			loggingData.setPlCreat(packageData.getCreatedOn());
			loggingData.setPlUpdate(packageData.getUpdatedOn());
			loggingData.setPlStatus("Log_running");
			if (pkId != 0) {
				
				if (packageRepo.existsById(pkId)) {
					Optional<PackageEntity> details = this.packageRepo.findById(pkId);
					loggingData.setPlComment("Success");
					packageLogRepo.save(loggingData);
					return details;
				} else {
					loggingData.setPlComment("Not_Found");
					packageLogRepo.save(loggingData);
					throw new CustomException("Record not found");
				}
			} else {
				loggingData.setPlComment("Invalid_ID");
				packageLogRepo.save(loggingData);
				throw new CustomException("Please enter the valid Id");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/add")
	public GlobalResponse addPackagedata(@RequestBody PackageEntity newData,@RequestHeader("Authorization") String token)
	{
		try
		{
			
			log.info("Inside-PackageController.addPackagedata");
			if ((fieldValidation.isEmpty(newData.getInstId()))
					& (fieldValidation.isEmpty(newData.getParentId()))
					& (fieldValidation.isEmpty(newData.getPkComment()))
					& (fieldValidation.isEmpty(newData.getPkFname()))
					& (fieldValidation.isEmpty(newData.getPkName()))
					& (fieldValidation.isEmpty(newData.getPkNusers()))
					& (fieldValidation.isEmpty(newData.getPkStatus()))
					& (fieldValidation.isEmpty(newData.getPkType()))
					& (fieldValidation.isEmpty(newData.getPkValidityNum()))
					& (fieldValidation.isEmpty(newData.getPkValidityType())))
			{
				org.springframework.http.HttpHeaders header=new org.springframework.http.HttpHeaders();
				header.set("Authorization", token);
				HttpEntity request=new HttpEntity(header);
				ResponseEntity<String> response=new RestTemplate().exchange("http://localhost:8088/dev/institute/insIdvalidation/"+newData.getInstId(), HttpMethod.GET, request, String.class);
				System.out.println(response.getBody());
				//if(response.)
				
				if(response.getBody().equals("notOk"))
				{
					throw new CustomException("Institute Id does not exist");
				}
				else if(response.getBody().equals("Exception"))
				{
					throw new CustomException("Exception");
				}
				else
					{
				newData.setCreatedOn(new Date());
				newData.setIsActive((long) 1);
				newData.setIsDeleted((long) 0);
				newData.setPkCdate(new Date());
				packageRepo.save(newData);
				PackageEntity packageData=packageRepo.getById(newData.getInstId());
				PackageLogEntity loggingData=new PackageLogEntity();
				loggingData.setPkId(newData.getPkId());
				loggingData.setPlAction("add");
				loggingData.setPlAdate(new Date());
				loggingData.setPlCreat(packageData.getCreatedOn());
				loggingData.setPlUpdate(packageData.getUpdatedOn());
				loggingData.setPlStatus("Log_running");
				loggingData.setPlComment("Success");
				packageLogRepo.save(loggingData);
				return new GlobalResponse("SUCCESSFULL", "Data is strored in database successfull");
					}
			}
			else
			{
				throw new CustomException("Please enter valid data");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/update/{pkId}")
	public GlobalResponse updatePackage(@RequestBody PackageEntity updatePackagedata,@PathVariable long pkId)
	{
		try
		{
			PackageEntity packageData=packageRepo.getById(pkId);
			PackageLogEntity loggingData=new PackageLogEntity();
			loggingData.setPkId(packageData.getPkId());
			loggingData.setPlAction("update");
			loggingData.setPlAdate(new Date());
			loggingData.setPlCreat(packageData.getCreatedOn());
			loggingData.setPlUpdate(packageData.getUpdatedOn());
			loggingData.setPlStatus("Log_running");
			log.info("Inside-PackageController.update");
			if(pkId!=0)
			{
				if(packageRepo.existsById(pkId))
				{
					if ((fieldValidation.isEmpty(updatePackagedata.getInstId()))
							& (fieldValidation.isEmpty(updatePackagedata.getParentId()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkComment()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkFname()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkName()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkNusers()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkStatus()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkType()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkValidityNum()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkValidityType())))
					{
						PackageEntity dbData=packageRepo.getById(pkId);
						if(dbData.getInstId()!=updatePackagedata.getInstId())
						{
							loggingData.setPlComment("Invalid institute Id");
							packageLogRepo.save(loggingData);
							throw new CustomException("Please enter valid institute id");
						}
						updatePackagedata.setCreatedOn(dbData.getCreatedOn());
						updatePackagedata.setPkId(dbData.getPkId());
						updatePackagedata.setPkCdate(dbData.getPkCdate());
						updatePackagedata.setIsActive(dbData.getIsActive());
						updatePackagedata.setIsDeleted(dbData.getIsDeleted());
						updatePackagedata.setUpdatedOn(new Date());
						packageRepo.save(updatePackagedata);
						loggingData.setPlComment("DB_Updated");
						packageLogRepo.save(loggingData);
						return new GlobalResponse("Successfull", "DB is updated");
					}
					else
					{
						loggingData.setPlComment("Invalid field information");
						packageLogRepo.save(loggingData);
						throw new CustomException("Please enter the valid information");
					}
				}
				else
				{
					loggingData.setPlComment("Invalid package id");
					packageLogRepo.save(loggingData);
					throw new CustomException("Record not found");
				}
			}
			else
			{
				loggingData.setPlComment("Invalid input data");
				packageLogRepo.save(loggingData);

				throw new CustomException("Invalid input data");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
