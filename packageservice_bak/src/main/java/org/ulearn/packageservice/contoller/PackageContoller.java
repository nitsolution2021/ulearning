package org.ulearn.packageservice.contoller;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import org.ulearn.packageservice.entity.InstituteAdminEntity;
import org.ulearn.packageservice.entity.DataResponseEntity;
import org.ulearn.packageservice.entity.GlobalResponse;
import org.ulearn.packageservice.entity.InstituteAddressEntity;
import org.ulearn.packageservice.entity.InstituteEntity;
import org.ulearn.packageservice.entity.LicenseEntity;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.entity.PackageLogEntity;
import org.ulearn.packageservice.exception.CustomException;
import org.ulearn.packageservice.repo.InstituteRepo;
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

	@Autowired
	private InstituteRepo instituteRepo;
	private static final Logger log = LoggerFactory.getLogger(PackageContoller.class);

	@GetMapping("/list")
	public List<?> listAlldata(@RequestHeader("Authorization") String token ) {
		try {
			log.info("Inside-PackageController.list");
			List<InstituteEntity> allPackagedata =instituteRepo.findAll();
			if (allPackagedata != null) {
				return allPackagedata;
			} else {
				throw new CustomException("No data found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/view/{pkId}")
	public Optional<?> viewPackagedata(@PathVariable long pkId,
			@RequestHeader("Authorization") String token) {
//		log.info("Hi");
//		log.info("Token is <><><><>  "+token);
		try {
			log.info("Inside-PackageController.view");
			if (pkId != 0) {
				if (packageRepo.existsById(pkId)) {
					PackageEntity packageData = packageRepo.getById(pkId);
					//log.info(packageData.toString());
					HttpHeaders header = new HttpHeaders();
					header.set("Authorization", token);
					HttpEntity request = new HttpEntity(header);
					ResponseEntity<InstituteEntity> instituteData = new RestTemplate().exchange(
							"http://localhost:8087/dev/institute/view/" + packageData.getInstId(), HttpMethod.GET,
							request, InstituteEntity.class);
					
					//log.info("DATAAAAAAAAAAA   "+Response.getBody().getInstEmail());
					DataResponseEntity dataResponseEntity=new DataResponseEntity();
					dataResponseEntity.setPackageData(packageData);
					dataResponseEntity.setInstituteData(instituteData.getBody());
					return Optional.of(dataResponseEntity);

				} else {
					throw new CustomException("NO RECORD FOUND");
				}
			} else {
				throw new CustomException("Invalid data input");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/add")
	public GlobalResponse addPackagedata(@Valid @RequestBody PackageEntity newData,
			@RequestHeader("Authorization") String token) {
		try {

			log.info("Inside-PackageController.addPackagedata");
			if ((fieldValidation.isEmpty(newData.getInstId())) & (fieldValidation.isEmpty(newData.getParentId()))
					& (fieldValidation.isEmpty(newData.getPkComment()))
					& (fieldValidation.isEmpty(newData.getPkFname())) & (fieldValidation.isEmpty(newData.getPkName()))
					& (fieldValidation.isEmpty(newData.getPkNusers()))
					& (fieldValidation.isEmpty(newData.getPkStatus())) & (fieldValidation.isEmpty(newData.getPkType()))
					& (fieldValidation.isEmpty(newData.getPkValidityNum()))
					& (fieldValidation.isEmpty(newData.getPkValidityType()))) {
				org.springframework.http.HttpHeaders header = new org.springframework.http.HttpHeaders();
				header.set("Authorization", token);
				HttpEntity request = new HttpEntity(header);

				ResponseEntity<String> response = new RestTemplate().exchange(
						"http://localhost:8087/dev/institute/insIdvalidation/" + newData.getInstId(), HttpMethod.GET,
						request, String.class);
				System.out.println(response.getBody());
				// if(response.)

				if (response.getBody().equals("notOk")) {
					throw new CustomException("Institute Id does not exist");
				} else if (response.getBody().equals("Exception")) {
					throw new CustomException("Exception");
				} else {
					newData.setCreatedOn(new Date());
					newData.setIsActive((long) 1);
					newData.setIsDeleted((long) 0);
					newData.setPkCdate(new Date());
					packageRepo.save(newData);
					return new GlobalResponse("SUCCESSFULL", "Data is strored in database successfull");
				}
			} else {
				throw new CustomException("Please enter valid data");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/update/{pkId}")
	public GlobalResponse updatePackage(@Valid @RequestBody PackageEntity updatePackagedata, @PathVariable long pkId) {
		try {
			log.info("Inside-PackageController.update");
			if (pkId != 0) {
				if (packageRepo.existsById(pkId)) {
					if ((fieldValidation.isEmpty(updatePackagedata.getInstId()))
							& (fieldValidation.isEmpty(updatePackagedata.getParentId()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkComment()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkFname()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkName()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkNusers()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkStatus()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkType()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkValidityNum()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkValidityType()))) {
						PackageEntity dbData = packageRepo.getById(pkId);
						if (dbData.getInstId() != updatePackagedata.getInstId()) {
							throw new CustomException("Please enter valid institute id");
						}
						updatePackagedata.setCreatedOn(dbData.getCreatedOn());
						updatePackagedata.setPkId(dbData.getPkId());
						updatePackagedata.setPkCdate(dbData.getPkCdate());
						updatePackagedata.setIsActive(dbData.getIsActive());
						updatePackagedata.setIsDeleted(dbData.getIsDeleted());
						updatePackagedata.setUpdatedOn(new Date());
						packageRepo.save(updatePackagedata);
						return new GlobalResponse("Successfull", "DB is updated");
					} else {
						throw new CustomException("Please enter the valid information");
					}
				} else {
					throw new CustomException("Record not found");
				}
			} else {
				throw new CustomException("Invalid input data");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/suspended/{pkId}")
	public GlobalResponse suspend(@PathVariable long pkId,@RequestBody PackageLogEntity packageLogEntity)
	{
		try
		{
			log.info("Inside-packageController.suspend()");
				if(packageRepo.existsById(pkId))
				{
					PackageEntity suspendedPackageData=packageRepo.getById(pkId);
					long isActive=0; 
					if(suspendedPackageData.getIsActive()!=isActive)
					{
						if((fieldValidation.isEmpty(packageLogEntity.getPlAdate()))
								& (fieldValidation.isEmpty(packageLogEntity.getPlComment())))
						{
							
							PackageLogEntity logData=new PackageLogEntity();
							logData.setPkId(pkId);
							logData.setPlAction("Suspended");
							logData.setPlAdate(packageLogEntity.getPlAdate());
							logData.setPlComment(packageLogEntity.getPlComment());
							logData.setPlCreat(new Date());
							logData.setPlStatus("Running");
							logData.setPlUpdate(null);
							packageLogRepo.save(logData);
							suspendedPackageData.setIsActive(isActive);
							packageRepo.save(suspendedPackageData);
							return new GlobalResponse("Success", "PackageId is Suspended");
						}
						else
						{
							throw new CustomException("Invalid field data");
						}
					}
					else
					{
						throw new CustomException("Id allready suspended");
					}
				}
				else
				{
					throw new CustomException("No record found");
				}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
