package org.ulearn.packageservice.contoller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
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

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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
	public Map<String, Object> getInstute(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		log.info("Inside - InstituteController.getpackage()");

		int Limit = 10;

		try {
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
					sortBy.orElse("createdOn"));
			Page<InstituteEntity> findAll = instituteRepo.findByAllInst(pagingSort);
			
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


	@GetMapping("/view/{pkId}")
	public Optional<?> viewPackagedata(@PathVariable long pkId,
			@RequestHeader("Authorization") String token) {
		try {
			log.info("Inside-PackageController.view");
			if (pkId != 0) {
				if (packageRepo.existsById(pkId)) {
					PackageEntity packageData = packageRepo.getById(pkId);
					InstituteEntity insttituteEntity= instituteRepo.getById(packageData.getInstId());
					//insttituteEntity.setPackageEntity((List<PackageEntity>) packageData);
					return Optional.of(insttituteEntity);

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
//	@GetMapping("/list/{page}/{limit}/{sortName}/{sort}")
//	public Map<String, Object> getpackagePagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
//			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
//			@RequestParam(defaultValue = "") Optional<String>keyword, @RequestParam Optional<String> sortBy) {
//
//		log.info("Inside - PackageController.getpackagePagination()");
//		
//		try {
//			Pageable pagingSort = null;
//
//			if (sort.equals("ASC")) {
//				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
//			} else {
//				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
//			}
//
//			Page<PackageEntity> findAll = null;
//			if (keyword.isPresent()) {
//				//findAll = packageRepo.Search(keyword.get(), pagingSort);
//			} else {
//				findAll = packageRepo.findByAllInst(pagingSort);
//			}
//			int totalPage=findAll.getTotalPages()-1;
//			if(totalPage < 0) {
//				totalPage=0;
//			}
//			
//			Map<String, Object> response = new HashMap<>();
//			response.put("data", findAll.getContent());
//			response.put("currentPage", findAll.getNumber());
//			response.put("total", findAll.getTotalElements());
//			response.put("totalPage", totalPage);
//			response.put("perPage", findAll.getSize());
//			response.put("perPageElement", findAll.getNumberOfElements());
//
//			if (findAll.getSize() < 1) {
//				throw new CustomException("Package Not Found");
//			} else {
//				return response;
//			}
//		} catch (Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//
//	}

	@PostMapping("/add")
	public GlobalResponse addPackagedata(@Valid @RequestBody PackageEntity newData) {
		try {

			log.info("Inside-PackageController.addPackagedata");
			if ((fieldValidation.isEmpty(newData.getInstId()))
					& (fieldValidation.isEmpty(newData.getPkComment()))
					& (fieldValidation.isEmpty(newData.getPkFname())) 
					& (fieldValidation.isEmpty(newData.getPkName()))
					& (fieldValidation.isEmpty(newData.getPkNusers()))
					& (fieldValidation.isEmpty(newData.getPkValidityNum()))
					& (fieldValidation.isEmpty(newData.getPkValidityType()))) {
				long defaultId=0;
				if(packageRepo.existsById(newData.getInstId()))
				{
					Optional<PackageEntity> oldPackageData=packageRepo.findById(newData.getInstId());
					PackageEntity packageData=oldPackageData.get();
					long parentId=packageData.getInstId();
					System.out.println(parentId);
					newData.setParentId(parentId);
				}
				else
				{
					newData.setParentId(defaultId);
				}
					newData.setCreatedOn(new Date());
					newData.setIsActive((long) 2);
					newData.setIsDeleted((long) 0);
					packageRepo.save(newData);
					List<PackageEntity> recentlyAddedData= packageRepo.findByListInst()
							.stream()
							.filter(Inst -> Inst.getIsActive()==2)
							.filter(Inst -> Inst.getIsDeleted()==0)
							.collect(Collectors.toList());
						PackageEntity newPackagedata=recentlyAddedData.get(0);
						//System.out.println(newPackagedata);
						PackageLogEntity pkLogdata=new PackageLogEntity();
						pkLogdata.setPkId(newPackagedata.getPkId());
						pkLogdata.setPlAction("Added");
						pkLogdata.setPlAdate(newPackagedata.getPkCdate());
						pkLogdata.setPlComment("Subcription Package Added");
						pkLogdata.setPlCreat(new Date());
						pkLogdata.setPlStatus("Running");
						packageLogRepo.save(pkLogdata);
						newPackagedata.setIsActive((long) 1);
						packageRepo.save(newPackagedata);
					 return new GlobalResponse("Success", "Package Added Successfully");
			} else {
				throw new CustomException("Please Enter Valid Data");
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
							throw new CustomException("Institute id cannot updated");
						}
						updatePackagedata.setCreatedOn(dbData.getCreatedOn());
						updatePackagedata.setPkId(dbData.getPkId());
						updatePackagedata.setPkCdate(dbData.getPkCdate());
						updatePackagedata.setIsActive(dbData.getIsActive());
						updatePackagedata.setIsDeleted(dbData.getIsDeleted());
						updatePackagedata.setUpdatedOn(new Date());
						packageRepo.save(updatePackagedata);
						return new GlobalResponse("Success", "Subcription Package Update Succcessfully");
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
							return new GlobalResponse("Success", "PackageId Is Suspended");
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
}
