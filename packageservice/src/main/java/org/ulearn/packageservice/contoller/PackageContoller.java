package org.ulearn.packageservice.contoller;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.tomcat.util.json.JSONParser;
import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;



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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.ulearn.packageservice.entity.DataResponseEntity;
import org.ulearn.packageservice.entity.GlobalResponse;
import org.ulearn.packageservice.entity.InstituteAddressEntity;
import org.ulearn.packageservice.entity.InstituteEntity;
import org.ulearn.packageservice.entity.LicenseEntity;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.entity.PackageGlobalTemplate;
import org.ulearn.packageservice.entity.PackageLogEntity;
import org.ulearn.packageservice.exception.CustomException;
import org.ulearn.packageservice.repo.InstituteRepo;
import org.ulearn.packageservice.repo.LicenseRepo;
import org.ulearn.packageservice.repo.LoginRepository;
import org.ulearn.packageservice.repo.PackageLogRepo;
import org.ulearn.packageservice.repo.PackageRepo;
import org.ulearn.packageservice.validation.FieldValidation;

import com.fasterxml.jackson.databind.util.JSONPObject;

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
	
	@Autowired
	private LicenseRepo licenseRepo;
	private static final Logger log = LoggerFactory.getLogger(PackageContoller.class);

	@GetMapping("/list")
	public Map<String, Object> getInstute(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		log.info("Inside - InstituteController.getpackage()");

		int Limit = 10;

		try {
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
					sortBy.orElse("createdOn"));
			Page<PackageEntity> findAll = packageRepo.findByListpackage(pagingSort);			
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
				throw new CustomException("Package Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	@RequestMapping(value = { "/list/{page}/{limit}/{sortName}/{sort}" }, method = RequestMethod.GET)
	public Map<String, Object> getInstutePagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@RequestParam(defaultValue = "") Optional<String>keyword, @RequestParam Optional<String> sortBy)
	{
		try
		{
			Pageable pageSort=null;
			if(sort.equals("ASC"))
					{
						pageSort= PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
					}
					else
					{
						pageSort= PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
					}
			Page<PackageEntity> findAll =null;
			if(keyword.isPresent())
			{
				findAll= packageRepo.Search(keyword.get(),pageSort);
			}
			else
			{
				findAll=packageRepo.findByListpackage(pageSort);
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
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/view/{pkId}")
	public Optional<?> viewPackagedata(@PathVariable long pkId) {
		try {
			log.info("Inside-PackageController.view");
			if (pkId != 0) {
				if (packageRepo.existsById(pkId)) {
					PackageEntity packageData = packageRepo.getById(pkId);
					InstituteEntity instituteEntity= instituteRepo.getById(packageData.getInstId());
					//insttituteEntity.setPackageEntity((List<PackageEntity>) packageData);
					DataResponseEntity dataResponseEntity=new DataResponseEntity();
					dataResponseEntity.setInstituteData(instituteEntity);
					dataResponseEntity.setPackageData(packageData);
					return Optional.of(dataResponseEntity);

				} else {
					throw new CustomException("No Id Found");
				}
			} else {
				throw new CustomException("Invalid Data Input");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/add")
	public GlobalResponse addPackagedata(@Valid @RequestBody PackageGlobalTemplate packageGlobalData,
			@RequestHeader("Authorization") String token) {
		try {

			log.info("Inside-PackageController.addPackagedata");
			if ((fieldValidation.isEmpty(packageGlobalData.getInstId()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkComment()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkFname())) 
					& (fieldValidation.isEmpty(packageGlobalData.getPkName()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkNusers()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkValidityNum()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkCdate()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkValidityType()))) {
				long defaultId=0;
				PackageEntity newData=new PackageEntity();
				newData.setPkFname(packageGlobalData.getPkFname());
				newData.setPkName(packageGlobalData.getPkName());
				newData.setPkNusers(packageGlobalData.getPkNusers());
				newData.setPkValidityNum(packageGlobalData.getPkValidityNum());
				newData.setPkValidityType(packageGlobalData.getPkValidityType());
				newData.setInstId(packageGlobalData.getInstId());
				newData.setPkComment(packageGlobalData.getPkComment());
				newData.setPkCdate(packageGlobalData.getPkCdate());
				if(packageRepo.existsById(packageGlobalData.getInstId()))
				{
					Optional<PackageEntity> oldPackageData=packageRepo.findById(packageGlobalData.getInstId());
					PackageEntity packageData=oldPackageData.get();
					long parentId=packageData.getInstId();
					//System.out.println(parentId);
					newData.setPkType("AddOn");
					newData.setParentId(parentId);
				}
				else
				{
					newData.setPkType("New");
					newData.setParentId(defaultId);
				}
				
					//newData.setPkCdate(new Date());
				if(licenseRepo.existsById(newData.getInstId()))
				{
					//System.out.println(licenseRepo.existsById(newData.getInstId()));
					throw new CustomException("License Not Found");
				}
				else
				{
					
					newData.setPkStatus("Active");
					newData.setCreatedOn(new Date());
					newData.setIsActive((long) 2);
					newData.setIsDeleted((long) 0);
					packageRepo.save(newData);
					List<PackageEntity> recentPackageEntity=packageRepo.recentData();
					//System.out.println(recentPackageEntity);
					PackageEntity newPackageEntity=recentPackageEntity.get(0);
					PackageLogEntity packageLogData=new PackageLogEntity();
					packageLogData.setPkId(newPackageEntity.getPkId());
					packageLogData.setPlAdate(newPackageEntity.getPkCdate());
					packageLogData.setPlAction("Subscribtion Added");
					packageLogData.setPlComment("Looking normal");
					packageLogData.setPlCreat(new Date());
					packageLogData.setPlStatus("Active");
					packageLogRepo.save(packageLogData);
					newPackageEntity.setIsActive((long) 1);
					packageRepo.save(newPackageEntity);
					//System.out.println(newPackageEntity);
					
					
					
					//<--Sent Email -->
					try {
						HttpHeaders headers = new HttpHeaders();
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);
						HttpEntity request = new HttpEntity(headers);
						ResponseEntity<PackageGlobalTemplate> responseEmailTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Package_Create",
								HttpMethod.GET, request, PackageGlobalTemplate.class);
						System.out.println(responseEmailTemp);
						String ETSubject= responseEmailTemp.getBody().getEtSubject();
						String ETBody=responseEmailTemp.getBody().getEtBody();
						
						String ETPackageFname="__$pkFname$__";
						String ETPackageCdate="__$pkCdate$__";
						String ETPackageValidityNo="__$pkValidityNum$__";
						String ETPackgeValidityType="__$pkValidityType$__";
						
						PackageEntity packageData=packageRepo.getById(newPackageEntity.getPkId());
						String ETPackageFnameResplaceString= packageData.getPkFname()+" "+
						"PackageId"+packageData.getPkId();
						String ETPackageCdateReplace=packageData.getPkCdate().toString();
						String ETPackageValidityNoReplace=packageData.getPkValidityNum().toString();
						String ETPackgeValidityTypereplace=packageData.getPkValidityType();
						String pkData = ETBody.replace(ETPackageFname, ETPackageFnameResplaceString);
						String pkCdate = pkData.replace(ETPackageCdate, ETPackageCdateReplace);
						String pkValidityNo = pkData.replace(ETPackageValidityNo,
								ETPackageValidityNoReplace);
						String mailBody=pkData.replace(ETPackgeValidityType, ETPackgeValidityTypereplace);
						InstituteEntity instData=instituteRepo.getById(newData.getInstId());
						String mailId=instData.getInstEmail();
						JSONObject requestJson = new JSONObject();
						requestJson.put("senderMailId", mailId);
						requestJson.put("subject", ETSubject);
						requestJson.put("body", mailBody);
						requestJson.put("enableHtml", true);
						log.info("requestJson "+requestJson.toString());
						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response = new RestTemplate()
								.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
						
					}
					catch(Exception e)
					{
						throw new CustomException(e.getMessage());
					}
					
					 return new GlobalResponse("Success", "Package Added Succesfully", 200);
				}
			} else {
				throw new CustomException("Please Enter Valid Data");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/update/{pkId}")
	public GlobalResponse updatePackage(@Valid @RequestBody PackageEntity updatePackagedata, @PathVariable long pkId,
			@RequestHeader("Authorization") String token) {
		try {
			log.info("Inside-PackageController.update");
			if (pkId != 0) {
				if (packageRepo.existsById(pkId)) {
					if ((fieldValidation.isEmpty(updatePackagedata.getInstId()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkComment()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkFname()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkName()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkNusers()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkValidityNum()))
							& (fieldValidation.isEmpty(updatePackagedata.getPkValidityType()))) {
						PackageEntity dbData = packageRepo.getById(pkId);
						if (dbData.getInstId() != updatePackagedata.getInstId()) {
							throw new CustomException("Institute id cannot updated");
						}
						updatePackagedata.setCreatedOn(dbData.getCreatedOn());
						updatePackagedata.setPkId(dbData.getPkId());
						updatePackagedata.setPkCdate(dbData.getPkCdate());
						updatePackagedata.setIsActive(dbData.getIsActive());
						updatePackagedata.setIsDeleted(dbData.getIsDeleted());
						updatePackagedata.setUpdatedOn(new Date());
						packageRepo.save(updatePackagedata);
						try {
							HttpHeaders headers = new HttpHeaders();
							headers.set("Authorization", token);
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity request = new HttpEntity(headers);
							ResponseEntity<PackageGlobalTemplate> responseEmailTemp = new RestTemplate().exchange(
									"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Package_Update",
									HttpMethod.GET, request, PackageGlobalTemplate.class);
							System.out.println(responseEmailTemp);
							String ETSubject= responseEmailTemp.getBody().getEtSubject();
							String ETBody=responseEmailTemp.getBody().getEtBody();
							
							String ETPackageFname="__$pkFname$__";
							String ETPackageCdate="__$pkCdate$__";
							String ETPackageValidityNo="__$pkValidityNum$__";
							String ETPackgeValidityType="__$pkValidityType$__";
							
							PackageEntity packageData=packageRepo.getById(pkId);
							String ETPackageFnameResplaceString= packageData.getPkFname()+"     "+
							"PackageId"+packageData.getPkId();
							String ETPackageCdateReplace=packageData.getPkCdate().toString();
							String ETPackageValidityNoReplace=packageData.getPkValidityNum().toString();
							String ETPackgeValidityTypereplace=packageData.getPkValidityType();
							String pkData = ETBody.replace(ETPackageFname, ETPackageFnameResplaceString);
							String pkCdate = pkData.replace(ETPackageCdate, ETPackageCdateReplace);
							String pkValidityNo = pkData.replace(ETPackageValidityNo,
									ETPackageValidityNoReplace);
							String mailBody=pkData.replace(ETPackgeValidityType, ETPackgeValidityTypereplace);
							InstituteEntity instData=instituteRepo.getById(updatePackagedata.getInstId());
							String mailId=instData.getInstEmail();
							JSONObject requestJson = new JSONObject();
							requestJson.put("senderMailId", mailId);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", mailBody);
							requestJson.put("enableHtml", true);
							log.info("requestJson "+requestJson.toString());
							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
							ResponseEntity<String> response = new RestTemplate()
									.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
							
						}
						catch(Exception e)
						{
							throw new CustomException(e.getMessage());
						}
						
						return new GlobalResponse("Success", "Subcription Package Update Succcessfully",200);
					} else {
						throw new CustomException("Please Enter The Valid Information");
					}
				} else {
					throw new CustomException("Record Not Found");
				}
			} else {
				throw new CustomException("Invalid Input Data");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/suspended/{pkId}")
	public GlobalResponse suspend(@PathVariable long pkId,@RequestBody PackageLogEntity packageLogEntity,
			@RequestHeader("Authorization") String token)
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
							suspendedPackageData.setPkStatus("Suspended");
							suspendedPackageData.setIsActive(isActive);
							packageRepo.save(suspendedPackageData);
							try {
								HttpHeaders headers = new HttpHeaders();
								headers.set("Authorization", token);
								headers.setContentType(MediaType.APPLICATION_JSON);
								HttpEntity request = new HttpEntity(headers);
								ResponseEntity<PackageGlobalTemplate> responseEmailTemp = new RestTemplate().exchange(
										"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Package_Suspend",
										HttpMethod.GET, request, PackageGlobalTemplate.class);
								System.out.println(responseEmailTemp);
								String ETSubject= responseEmailTemp.getBody().getEtSubject();
								String ETBody=responseEmailTemp.getBody().getEtBody();
								
								String ETPackageFname="__$pkFname$__";
								String ETPackageCdate="__$pkCdate$__";
								String ETPackageValidityNo="__$pkValidityNum$__";
								String ETPackgeValidityType="__$pkValidityType$__";
								
								PackageEntity packageData=packageRepo.getById(pkId);
								String ETPackageFnameResplaceString= packageData.getPkFname()+"     "+
								"PackageId"+packageData.getPkId();
								String ETPackageCdateReplace=packageData.getPkCdate().toString();
								String ETPackageValidityNoReplace=packageData.getPkValidityNum().toString();
								String ETPackgeValidityTypereplace=packageData.getPkValidityType();
								String pkData = ETBody.replace(ETPackageFname, ETPackageFnameResplaceString);
								String pkCdate = pkData.replace(ETPackageCdate, ETPackageCdateReplace);
								String pkValidityNo = pkData.replace(ETPackageValidityNo,
										ETPackageValidityNoReplace);
								String mailBody=pkData.replace(ETPackgeValidityType, ETPackgeValidityTypereplace);
								InstituteEntity instData=instituteRepo.getById(suspendedPackageData.getInstId());
								String mailId=instData.getInstEmail();
								JSONObject requestJson = new JSONObject();
								requestJson.put("senderMailId", mailId);
								requestJson.put("subject", ETSubject);
								requestJson.put("body", mailBody);
								requestJson.put("enableHtml", true);
								log.info("requestJson "+requestJson.toString());
								HttpEntity<String> entity = new HttpEntity(requestJson, headers);
								ResponseEntity<String> response = new RestTemplate()
										.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
								
							}
							catch(Exception e)
							{
								throw new CustomException(e.getMessage());
							}
							
							return new GlobalResponse("Success", "PackageId Is Suspended",200);
						}
						else
						{
							throw new CustomException("Invalid Field Data");
						}
					}
					else
					{
						throw new CustomException("Id Allready Suspended");
					}
				}
				else
				{
					throw new CustomException("No Record Found");
				}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/instList")
	public List<?> ListOfInstData()
	{
		try
		{
			List<InstituteEntity> instData=instituteRepo.findAll();
			return instData;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/institutePackagelist/{instId}")
	public InstituteEntity institutePackageList(@PathVariable long instId)
	{
		try
		{
			if(instId!=0)	
			{
				if(instituteRepo.existsById(instId))
				{
					InstituteEntity allPackagedata=instituteRepo.getById(instId);
					return allPackagedata;
				}
				else
				{
					throw new CustomException("No Package Found");
				}
			}
			else
			{
				throw new CustomException("Invalid Data Input");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/listTemp")
	public List<?> allListData()
	{
		try
		{
			List<InstituteEntity> packageData=instituteRepo.findAll();
			return packageData;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
