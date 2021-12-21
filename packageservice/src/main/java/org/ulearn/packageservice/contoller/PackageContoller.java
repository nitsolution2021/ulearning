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
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.ulearn.packageservice.entity.GlobalResponse;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.entity.PackageGlobalTemplate;
import org.ulearn.packageservice.entity.PackageLogEntity;
import org.ulearn.packageservice.exception.CustomException;
import org.ulearn.packageservice.helper.CustomFunction;
import org.ulearn.packageservice.services.PackageService;

@RestController
@RequestMapping("/package")
public class PackageContoller {

	@Autowired
	private PackageService packageService;
	
	@Autowired
	private CustomFunction customFunction;
	public static final Logger log = LoggerFactory.getLogger(PackageContoller.class);

	@GetMapping("/list/{isDelete}")
	public Map<String, Object> getPackage(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy,@PathVariable int isDelete) {
		log.info("Inside - InstituteController.getpackage()");
		try {
			return this.packageService.getPackage(page, sortBy, isDelete);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	@GetMapping("/list/{page}/{limit}/{sortName}/{sort}/{isDelete}")
	public Map<String, Object> getPackagePagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@RequestParam(defaultValue = "") Optional<String>keyword, @RequestParam Optional<String> sortBy,@PathVariable int isDelete)
	{
		try
		{
			return this.packageService.getPackagePagination(page, limit, sort, sortName, keyword, sortBy, isDelete);
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
			return this.packageService.viewPackagedata(pkId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/add")
	public GlobalResponse addPackagedata(@Valid @RequestBody PackageGlobalTemplate packageGlobalData,
			@RequestHeader("Authorization") String token) {
		try {
			log.info("Inside-PackageController.addPackagedata");
			return this.packageService.addPackagedata(packageGlobalData, token);
		}
		 catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/update/{pkId}")
	public GlobalResponse updatePackage(@Valid @RequestBody PackageEntity updatePackagedata, @PathVariable long pkId,
			@RequestHeader("Authorization") String token) {
		try {
			log.info("Inside-PackageController.update");
			return this. packageService.updatePackage(updatePackagedata, pkId, token);
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
			return this.packageService.suspend(pkId, packageLogEntity, token);	
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
			return this.packageService.ListOfInstData();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/listOfPackages/{instId}")
	public List<PackageEntity> listOfAllPackages(@PathVariable long instId)
	{
		try {
			return packageService.getInstDetails(instId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/paginationList/{instId}/{page}/{limit}/{sortName}/{sort}/{isDelete}")
	public Map<String, Object> paginationList(@PathVariable long instId,@PathVariable int limit,@PathVariable int isDelete,
								@PathVariable int page, @PathVariable String sort,@PathVariable String sortName,@RequestParam Optional<String> sortBy)
	{
		try
		{
			return this.packageService.pagination(instId,limit, isDelete,page,sort,sortName,sortBy);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/packageApproval/{pkId}")
	public GlobalResponse packageApprove(@PathVariable long pkId,@RequestHeader("Authorization") String token)
	{
		try
		{
			return this.packageService.approvePackage(pkId,token);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/endPackage/{pkId}")
	public GlobalResponse endPackage(@PathVariable long pkId,@RequestHeader("Authorization") String token,PackageLogEntity packageLogEntity)
	{
		try
		{
			return this.packageService.endPackage(pkId,token,packageLogEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/restorePackage/{pkId}")
	public GlobalResponse restorePackage(@PathVariable long pkId,@RequestHeader("Authorization") String token,PackageLogEntity packageLogEntity)
	{
		try
		{
			return this.packageService.restorePackage(pkId,token,packageLogEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}