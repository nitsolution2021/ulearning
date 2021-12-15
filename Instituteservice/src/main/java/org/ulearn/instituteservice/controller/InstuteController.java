package org.ulearn.instituteservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import org.ulearn.instituteservice.entity.GlobalResponse;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.servises.InstituteService;

@RestController
@RequestMapping("/institute")
public class InstuteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);

	@Autowired
	private InstituteService instituteService;
	
	@GetMapping("/list")
	public Map<String, Object> getInstute(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy,
			@RequestParam(defaultValue = "0") Integer isDeleted) {
		LOGGER.info("Inside - InstituteController.getInstute()");

		try {
			return this.instituteService.getInstuteList(page, sortBy, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/list/{page}/{limit}/{sortName}/{sort}/{isDeleted}" }, method = RequestMethod.GET)
	public Map<String, Object> getInstutePagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@PathVariable("isDeleted") int isDeleted, @RequestParam(defaultValue = "") Optional<String> keyword,
			@RequestParam Optional<String> sortBy) {

		LOGGER.info("Inside - InstituteController.getInstutePagination()");

		try {
			return this.instituteService.getInstuteListWithPaginagtion(page, limit, sort, sortName, isDeleted, keyword,
					sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/getlist")
	public List<InstituteEntity> getListInstute() {
		LOGGER.info("Inside - InstituteController.getListInstute()");

		try {
			return this.instituteService.getListInstuteService();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@GetMapping("/geteditlicenselist")
	public List<InstituteEntity> geteditlicenselist() {
		LOGGER.info("Inside - InstituteController.geteditlicenselist()");

		try {
			return this.instituteService.getEditLicenseListInstuteService();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@GetMapping("/getlicenselist")
	public List<InstituteEntity> getLicenseListInstute() {
		LOGGER.info("Inside - InstituteController.getLicenseListInstute()");

		try {
			return this.instituteService.getListLicenseInstuteService();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/credentialssent")
	public GlobalResponse postInstituteCredentials(@RequestBody InstituteEntity instituteEntrity,
			@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - InstituteController.postInstituteCredentials()");

		try {
			return this.instituteService.SendInstituteCredentials(instituteEntrity, token);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public GlobalResponse postInstituteDetails(@Valid @RequestBody InstituteGlobalEntity instituteGlobalEntrity,
			@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - InstituteController.postInstituteDetails()");

		try {
			return this.instituteService.postInstituteDetailsService(instituteGlobalEntrity, token);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/view/{instId}")
	public Optional<InstituteEntity> viewInstituteDetails(@PathVariable() long instId) {
		LOGGER.info("Inside - InstituteController.viewInstituteDetails()");
		try {
			return this.instituteService.viewInstituteDetailsService(instId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/update/{instId}")
	public GlobalResponse putInstituteDetails(@Valid @RequestBody InstituteGlobalEntity instituteGlobalEntrity,
			@PathVariable("instId") long instId, @RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - InstituteController.putInstituteDetails()");
		try {
			return this.instituteService.putInstituteDetailsService(instituteGlobalEntrity, instId, token);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/delete")
	public GlobalResponse putInstituteDelete(@RequestBody() InstituteEntity instituteEntrity) {
		LOGGER.info("Inside - InstituteController.putInstituteDelete()");
		try {
			if (instituteEntrity.getInstId()!=null){
				return this.instituteService.putInstituteDeleteService(instituteEntrity.getInstId());
			}else {
				throw new CustomException("Institute Id Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	@PutMapping("/restore")
	public GlobalResponse putInstituteRestore(@RequestBody() InstituteEntity instituteEntrity) {
		LOGGER.info("Inside - InstituteController.putInstituteRestore()");
		try {
			if (instituteEntrity.getInstId()!=null){
				return this.instituteService.putInstituteRestoreService(instituteEntrity.getInstId());
			}else {
				throw new CustomException("Institute Id Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}	
	
	@PutMapping("/status")
	public GlobalResponse putInstituteStatus(@RequestBody() InstituteEntity instituteEntrity) {
		LOGGER.info("Inside - InstituteController.putInstituteStatus()");
		try {
			if (instituteEntrity.getInstId()!=null){
				return this.instituteService.putInstituteStatusService(instituteEntrity.getInstId());
			}else {
				throw new CustomException("Institute Id Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}

	@PutMapping("/muldelete")
	public GlobalResponse putInstituteMulDelete(@RequestBody() List<String> mulDelete) {
		LOGGER.info("Inside - InstituteController.putInstituteMulDelete()"+mulDelete);
		try {
			if (!mulDelete.equals(null)){
				return this.instituteService.putInstituteMulDeleteService(mulDelete);
			}else {
				throw new CustomException("Institute Id Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
}
