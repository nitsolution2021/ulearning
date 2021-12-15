package org.ulearn.packageservice.services;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import java.net.URLEncoder;
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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

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
import org.springframework.web.util.UriUtils;
import org.ulearn.packageservice.entity.DataResponseEntity;
import org.ulearn.packageservice.entity.GlobalResponse;
import org.ulearn.packageservice.entity.InstituteAddressEntity;
import org.ulearn.packageservice.entity.InstituteEntity;
import org.ulearn.packageservice.entity.LicenseEntity;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.entity.PackageGlobalTemplate;
import org.ulearn.packageservice.entity.PackageLogEntity;
import org.ulearn.packageservice.exception.CustomException;
import org.ulearn.packageservice.helper.CustomFunction;
import org.ulearn.packageservice.repo.InstituteRepo;
import org.ulearn.packageservice.repo.LicenseRepo;
import org.ulearn.packageservice.repo.LoginRepository;
import org.ulearn.packageservice.repo.PackageLogRepo;
import org.ulearn.packageservice.repo.PackageRepo;
import org.ulearn.packageservice.validation.FieldValidation;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mashape.unirest.http.Unirest;


@Service
public class PackageService {

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
	
	@Autowired
	private CustomFunction customFunction;
	
	
	public Map<String, Object> getPackage(Optional<Integer> page,Optional<String> sortBy,@PathVariable int isDelete)
	{
		int Limit = 10;
		try {
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
			sortBy.orElse("createdOn"));
	Page<PackageEntity> findAll = packageRepo.findByListpackage(pagingSort,isDelete);			
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
	
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public Map<String, Object> getPackagePagination(int page,int limit,
			String sort, String sortName,
			 Optional<String>keyword,Optional<String> sortBy,int isDelete)
	{
		try
		{
			if(limit==0)
			{
				int countData=(int) instituteRepo.count();
				limit=countData;
			}
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
			if(!keyword.get().isEmpty())
			{
				findAll= packageRepo.Search(keyword.get(),isDelete,pageSort);
			}
			else
			{
				findAll=packageRepo.findByListpackage(pageSort,isDelete);
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
	public Optional<?> viewPackagedata(@PathVariable long pkId)
	{
		try
		{
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
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponse addPackagedata(@Valid @RequestBody PackageGlobalTemplate packageGlobalData,
			@RequestHeader("Authorization") String token)
	{
		try
		{
			if ((fieldValidation.isEmpty(packageGlobalData.getInstId()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkComment()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkFname())) 
					& (fieldValidation.isEmpty(packageGlobalData.getPkName()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkNusers()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkValidityNum()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkCdate()))
					& (fieldValidation.isEmpty(packageGlobalData.getPkValidityType()))) {
				long defaultId=0;
				//System.out.println(packageGlobalData);
				PackageEntity newData=new PackageEntity();
				newData.setPkFname(packageGlobalData.getPkFname());
				newData.setPkName(packageGlobalData.getPkName());
				newData.setPkNusers(packageGlobalData.getPkNusers());
				newData.setPkValidityNum(packageGlobalData.getPkValidityNum());
				newData.setPkValidityType(packageGlobalData.getPkValidityType());
				newData.setInstId(packageGlobalData.getInstId());
				newData.setPkComment(packageGlobalData.getPkComment());
				newData.setPkCdate(packageGlobalData.getPkCdate());
				int defalultParent=0;
				if(instituteRepo.existsById(newData.getInstId()))
				{
					
					
					List<LicenseEntity> licenseData=licenseRepo.findInst(newData.getInstId());
					int licenseExixtense=0;
					LicenseEntity licenseEntity=new LicenseEntity();
					for(int i=0;i<licenseData.size();i++)
					{
						 licenseEntity=licenseData.get(i);
						
						if(licenseEntity.getInstId()==newData.getInstId())
						{
							
							licenseExixtense++;
						}
					}
					if(licenseExixtense!=0)
					{
						//packageRepo.save(newData);
						
						List<PackageEntity> packData=packageRepo.instData(newData.getInstId()); 
						//System.out.println(licenseExixtense);
						int packageExixtense=0;
						for(int i=0;i<packData.size();i++)
						{
							PackageEntity packageEntity=packData.get(i);
							if(packageEntity.getInstId()==newData.getInstId())
							{
								//
								packageExixtense++;
								newData.setParentId(packageEntity.getPkId());
							}
							else
							{
								System.out.println("hi");
								//newData.setPkType("NewPackage");
								newData.setParentId(defaultId);
							}
						}
						if(packageExixtense!=0)
						{
							newData.setPkType("SubPackage");
						}
						else
						{
							newData.setPkType("NewPackage");
						}
						long licenceValidityHours=0;
						long packageValidityHours=0;
						if(newData.getPkValidityType().equals("Days"))
						{
							packageValidityHours=24*newData.getPkValidityNum();
						}
						if(newData.getPkValidityType().equals("Months"))
						{
							packageValidityHours=720*newData.getPkValidityNum();
						}
						if(newData.getPkValidityType().equals("Years"))
						{
							packageValidityHours=8640*newData.getPkValidityNum();
						}
						if(licenseEntity.getLcValidityType().equals("Days"))
						{
							licenceValidityHours=24*licenseEntity.getLcValidityNum();
						}
						if(licenseEntity.getLcValidityType().equals("Months"))
						{
							licenceValidityHours=720*licenseEntity.getLcValidityNum();
						}
						if(licenseEntity.getLcValidityType().equals("Years"))
						{
							licenceValidityHours=8640*licenseEntity.getLcValidityNum();
						}
						if(licenceValidityHours>=packageValidityHours)
						{
							//return new GlobalResponse("Success", "okk", 200);
							newData.setPkStatus("Active");
							newData.setCreatedOn(new Date());
							newData.setIsActive(2);
							newData.setIsDeleted(0);
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
							newPackageEntity.setIsActive(1);
							packageRepo.save(newPackageEntity);
							HttpHeaders headers = new HttpHeaders();
							headers.set("Authorization", token);
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity request = new HttpEntity(headers);
							try {
								InstituteEntity instData=instituteRepo.getById(newPackageEntity.getInstId());
								//System.out.println(instData.getInstituteAdmin().getAmdMnum());
								ResponseEntity<PackageGlobalTemplate> responseEmailTemp=customFunction.getEmailDetails("Package_Create", token);
								//System.out.println(responseEmailTemp.getBody().getStTempId());
								//System.out.println();
								customFunction.sentEmail(newPackageEntity, token, responseEmailTemp,packageLogData);
							}
							catch(Exception e)
							{
								org.json.JSONObject jsonObject = new org.json.JSONObject(e.getMessage().substring(7));																		
								if(!jsonObject.getString("messagee").equals("")) {										
									throw new CustomException(jsonObject.getString("messagee"));
								}
								
								throw new CustomException("Email Service Is Not Running!");
							}
							try
							{
								ResponseEntity<PackageGlobalTemplate> responseSMSTemp=customFunction.getSMSDetail(token,"Package_Create");
								customFunction.sentSMS(newPackageEntity, token, responseSMSTemp);
								return new GlobalResponse("Success", "Package Added Succesfully", 200);
							}
							catch(Exception e)
							{
								org.json.JSONObject jsonObject = new org.json.JSONObject(e.getMessage().substring(7));																		
								if(!jsonObject.getString("messagee").equals("")) {										
									throw new CustomException(jsonObject.getString("messagee"));
								}
								
								throw new CustomException("SMS Service Is Not Running!");
							}
							
						}
						else
						{
							throw new CustomException("Subcribtion Plan Should not be exceed License Plan");
						}
					}
					else
					{
						throw new CustomException("License Not Found");
					}
				}
				else
				{
					throw new CustomException("institute Not found");
				}
			}
			else 
			{
				throw new CustomException("Please Enter Valid Data");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponse updatePackage(@Valid @RequestBody PackageEntity updatePackagedata, @PathVariable long pkId,
			@RequestHeader("Authorization") String token)
	{
		try
		{
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
						List<LicenseEntity> licenseData=licenseRepo.findInst(updatePackagedata.getInstId());
						int licenseExixtense=0;
//						LicenseEntity licenseEntity=licenseData.get(0);
//						System.out.println(licenseEntity);
//						System.out.println(newData.getInstId());
						LicenseEntity licenseEntity=new LicenseEntity();
						for(int i=0;i<licenseData.size();i++)
						{
							 licenseEntity=licenseData.get(i);
							
							if(licenseEntity.getInstId()==updatePackagedata.getInstId())
							{
								//LicenseEntity licenseEntity1=
								licenseExixtense++;
							}
						}
						
						long packageValidityHours=0;
						long licenceValidityHours=0;
						if(updatePackagedata.getPkValidityType().equals("Days"))
						{
							packageValidityHours=24*updatePackagedata.getPkValidityNum();
						}
						if(updatePackagedata.getPkValidityType().equals("Months"))
						{
							packageValidityHours=720*updatePackagedata.getPkValidityNum();
						}
						if(updatePackagedata.getPkValidityType().equals("Years"))
						{
							packageValidityHours=8640*updatePackagedata.getPkValidityNum();
						}
						if(licenseEntity.getLcValidityType().equals("Days"))
						{
							licenceValidityHours=24*licenseEntity.getLcValidityNum();
						}
						if(licenseEntity.getLcValidityType().equals("Months"))
						{
							licenceValidityHours=720*licenseEntity.getLcValidityNum();
						}
						if(licenseEntity.getLcValidityType().equals("Years"))
						{
							licenceValidityHours=8640*licenseEntity.getLcValidityNum();
						}
						if(licenceValidityHours>=packageValidityHours)
						{
						updatePackagedata.setCreatedOn(dbData.getCreatedOn());
						updatePackagedata.setPkId(dbData.getPkId());
						updatePackagedata.setPkCdate(dbData.getPkCdate());
						updatePackagedata.setIsActive(dbData.getIsActive());
						updatePackagedata.setIsDeleted(dbData.getIsDeleted());
						updatePackagedata.setPkStatus("Active");
						updatePackagedata.setUpdatedOn(new Date());
						PackageLogEntity logEntity=new PackageLogEntity();
						packageRepo.save(updatePackagedata);
						InstituteEntity instituteEntity=instituteRepo.getById(updatePackagedata.getInstId());
						PackageLogEntity packageLogEntity=new PackageLogEntity();
						try
						{
							ResponseEntity<PackageGlobalTemplate> emailTemplateData=customFunction.getEmailDetails("Package_Update", token);
							//System.out.println(emailTemplateData);
							ResponseEntity<PackageGlobalTemplate> smsTemplateData=customFunction.getSMSDetail(token,"Package_Update");
							//System.out.println();
							System.out.println(updatePackagedata);
							packageLogEntity.setPkId(pkId);
							packageLogEntity.setPlAction("Update");
							packageLogEntity.setPlAdate(new Date());
							packageLogEntity.setPlComment("No Command");
							packageLogEntity.setPlCreat(new Date());
							packageLogEntity.setPlStatus("Active");
							System.out.println(packageLogEntity);
							customFunction.sentEmail(updatePackagedata, token, emailTemplateData, packageLogEntity);
							System.out.println(instituteEntity.getInstituteAdmin().getAmdMnum());
							System.out.println(instituteEntity.getInstituteAdmin().getAmdEmail());
							customFunction.sentSMS(updatePackagedata, token, smsTemplateData);
							return new GlobalResponse("Success", "Package Update Successfully", 200);
						}
						catch(Exception e)
						{
							throw new CustomException(e.getMessage());
						}
						
						
					}
						else
						{
							throw new CustomException("Subcribtion Plan Should Not Be Exceed License Plan");
						}
					}
						else {
						throw new CustomException("Please Enter The Valid Information");
					}
				} else {
					throw new CustomException("Record Not Found");
				}
			} else {
				throw new CustomException("Invalid Input Data");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponse suspend(long pkId, PackageLogEntity packageLogEntity,
			@RequestHeader("Authorization") String token)
	{
		try {
			if(packageRepo.existsById(pkId))
			{
				PackageEntity suspendedPackageData=packageRepo.getById(pkId);
				int isActive=0; 
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
						logData.setPlStatus("Active");
						logData.setPlUpdate(null);
						packageLogRepo.save(logData);
						suspendedPackageData.setPkStatus("Suspended");
						suspendedPackageData.setIsActive(isActive);
						suspendedPackageData.setUpdatedOn(packageLogEntity.getPlAdate());
						packageRepo.save(suspendedPackageData);
						try
						{
							ResponseEntity<PackageGlobalTemplate> emailTemplateData=customFunction.getEmailDetails("Package_Suspend", token);
							ResponseEntity<PackageGlobalTemplate> smsTemplateData=customFunction.getSMSDetail(token,"Package_Suspend");
							
							customFunction.sentEmail(suspendedPackageData, token, emailTemplateData, logData);
							customFunction.sentSMS(suspendedPackageData, token, smsTemplateData);
							return new GlobalResponse("Success", "Package Suspended Successfully", 200);
						}
						catch(Exception e)
						{
							throw new CustomException(e.getMessage());
						}
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
	public List<?> ListOfInstData()
	{
		try
		{
			List<InstituteEntity> instData=instituteRepo.findAll();
			ArrayList<InstituteEntity> instituteData=new ArrayList<>();
			for(int i=0;i<instData.size();i++)
			{
				if(licenseRepo.findInst(instData.get(i).getInstId())!=null)
				{
					
					instituteData.add(instData.get(i));
				}
			}
			return instituteData;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public List<PackageEntity> getInstDetails(long instId) {
		// TODO Auto-generated method stub
		try
		{
			if(instId!=0)
			{
				List<PackageEntity> instPackageDetailsEntities=packageRepo.instData(instId);
				return instPackageDetailsEntities;
			}
			else
			{
				throw new CustomException("invalid Institute Id");
			}
		}
		catch(Exception e)
		{
			 throw new CustomException(e.getMessage());
		}
	}
	public Map<String, Object> pagination(long instId,int limit,int isDelete,int  page, String sort,String sortName,Optional<String> sortby)
	{
		try
		{
			Pageable pageSort=null;
			if(limit==0)
			{
				int countData=(int) instituteRepo.count();
				limit=countData;
			}
			Pageable pagingSort=null;
			if (sort == "ASC") {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortby.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortby.orElse(sortName));
			}
			Page<PackageEntity> findAll = packageRepo.getInstData(instId,pageSort,isDelete);			
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
	public GlobalResponse approvePackage(long pkId ,String token) {
		try
		{
			if(pkId!=0)
			{
				if(packageRepo.existsById(pkId))
				{
					PackageEntity packageDetail=packageRepo.getById(pkId);
					packageDetail.setPkStatus("Approved");
					packageRepo.save(packageDetail);
					PackageLogEntity logData=new PackageLogEntity();
					logData.setPkId(pkId);
					logData.setPlAction("Approve");
					logData.setPlAdate(new Date());
					logData.setPlComment("Package Approved");
					logData.setPlCreat(new Date());
					logData.setPlStatus("Active");
					packageLogRepo.save(logData);
					//ResponseEntity<PackageGlobalTemplate> smsTemplateData=customFunction.getSMSDetail(token,"");
					try
					{
						ResponseEntity<PackageGlobalTemplate> emailTemplateData=customFunction.getEmailDetails("Package_Approve", token);
						ResponseEntity<PackageGlobalTemplate> smsTemplateData=customFunction.getSMSDetail(token,"Package_Approve");
						System.out.println(smsTemplateData);
						customFunction.sentEmail(packageDetail, token, emailTemplateData, logData);
						customFunction.sentSMS(packageDetail, token, smsTemplateData);
						return new GlobalResponse("Success", "Package Approved Successfully", 200);
					}
					catch(Exception e)
					{
						throw new CustomException(e.getMessage());
					}
				}
				else
				{
					throw new CustomException("Package Does Not Exist");
				}
			}
			else
			{
				throw new CustomException("Invalid Institute Id");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponse endPackage(long pkId, String token,PackageLogEntity packageLogEntity) {
		try
		{
			if(pkId!=0)
			{
				if(packageRepo.existsById(pkId))
				{
					PackageEntity packageEntity=packageRepo.getById(pkId);
					packageEntity.setPkStatus("End");
					PackageLogEntity logData=new PackageLogEntity();
					logData.setPkId(pkId);
					logData.setPlAction("End");
					logData.setPlAdate(packageLogEntity.getPlAdate());
					logData.setPlComment(packageLogEntity.getPlComment());
					logData.setPlCreat(new Date());
					logData.setPlStatus("Active");
					packageLogRepo.save(logData);
					try
					{
						ResponseEntity<PackageGlobalTemplate> emailTemplateData=customFunction.getEmailDetails("Package_End", token);
						ResponseEntity<PackageGlobalTemplate> smsTemplateData=customFunction.getSMSDetail(token,"Package_End");
						System.out.println(smsTemplateData);
						customFunction.sentEmail(packageEntity, token, emailTemplateData, logData);
						customFunction.sentSMS(packageEntity, token, smsTemplateData);
						return new GlobalResponse("Success", "Package Ended Succesfully", 200);
					}
					catch(Exception e)
					{
						throw new CustomException(e.getMessage());
					}
				}
				else
				{
					throw new CustomException("Package Id Does Not Exist");
				}
			}
			else
			{
				throw new CustomException("Invalid Package Id");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponse restorePackage(long pkId, String token,PackageLogEntity packageLogEntity) {
		try
		{
			if(pkId!=0)
			{
			if(packageRepo.existsById(pkId))
			{
				PackageEntity packageEntity=packageRepo.getById(pkId);
				packageEntity.setPkStatus("Active");
				PackageLogEntity logData=new PackageLogEntity();
				logData.setPkId(pkId);
				logData.setPlAction("Restore");
				logData.setPlAdate(packageLogEntity.getPlAdate());
				logData.setPlComment(packageLogEntity.getPlComment());
				logData.setPlCreat(new Date());
				logData.setPlStatus("Active");
				packageLogRepo.save(logData);
				try
				{
					ResponseEntity<PackageGlobalTemplate> emailTemplateData=customFunction.getEmailDetails("Package_Restore", token);
					ResponseEntity<PackageGlobalTemplate> smsTemplateData=customFunction.getSMSDetail(token,"Package_Restore");
					System.out.println(smsTemplateData);
					customFunction.sentEmail(packageEntity, token, emailTemplateData, logData);
					customFunction.sentSMS(packageEntity, token, smsTemplateData);
					return new GlobalResponse("Success", "Package Restored Succesfully", 200);
				}
				catch(Exception e)
				{
					throw new CustomException(e.getMessage());
				}
			}
			else
			{
				throw new CustomException("Package Id Does Not Exist");
			}
		}
			else
		{
			throw new CustomException("Invalid Package Id");
		}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
}
