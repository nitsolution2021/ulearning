package org.ulearn.instituteservice.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.annotations.Where;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.ModelAndView;
import org.ulearn.instituteservice.entity.GlobalResponse;
import org.ulearn.instituteservice.entity.InstituteAddressEntity;
import org.ulearn.instituteservice.entity.InstituteAdminEntity;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.repository.InstituteAddressRepo;
import org.ulearn.instituteservice.repository.InstituteAdminRepo;
import org.ulearn.instituteservice.repository.InstituteRepo;
import org.ulearn.instituteservice.validation.FieldValidation;


@RestController
@RequestMapping("/institute")
public class InstuteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);

	@Autowired
	private InstituteRepo instituteRepo;

	@Autowired
	private InstituteAddressRepo instituteAddressRepo;

	@Autowired
	private InstituteAdminRepo instituteAdminRepo;

	@Autowired
	private FieldValidation fieldValidation;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/list")
	public Map<String, Object> getInstute(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - InstituteController.getInstute()");

		int Limit = 10;

		try {
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
					sortBy.orElse("instId"));
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
				throw new CustomException("Institute Not Found!");
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
			@RequestParam(defaultValue = "") Optional<String>keyword, @RequestParam Optional<String> sortBy) {

		LOGGER.info("Inside - InstituteController.getInstutePagination()");
		
		try {
			Pageable pagingSort = null;

			if (sort=="ASC") {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}
			String keywordVal=keyword.get();
			Page<InstituteEntity> findAll = null;
			
			if (keywordVal.isEmpty()) {
				findAll = instituteRepo.findAll(pagingSort);				
			} else {
				findAll = instituteRepo.Search(keyword.get(), pagingSort);
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
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/getlist")
	public List<InstituteEntity> getListInstute() {
		LOGGER.info("Inside - InstituteController.getListInstute()");

		try {			
			List<InstituteEntity> findAll = instituteRepo.findByListInst()
			.stream()
            .filter(Inst -> Inst.getIsDeleted() == 0)
            .filter(Inst -> Inst.getIsActive() == 1)
            .filter(instituteLicense -> instituteLicense.getInstituteLicense() == null)            
            .collect(Collectors.toList());
			if (findAll.size() < 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return findAll;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PostMapping("/add")
	public GlobalResponse postInstituteDetails(@Valid @RequestBody InstituteGlobalEntity instituteGlobalEntrity,
			@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - InstituteController.postInstituteDetails()");

		try {

			Optional<InstituteEntity> findByInstituteName = instituteRepo
					.findByInstName(instituteGlobalEntrity.getInstName());
			Optional<InstituteEntity> findByInstEmail = instituteRepo
					.findByInstEmail(instituteGlobalEntrity.getInstEmail());

			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine2()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdFname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdLname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdDob()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdUsername()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdPassword()))) {
				if (!findByInstituteName.isPresent()) {
					if (!findByInstEmail.isPresent()) {

						InstituteEntity filterInsDetails = new InstituteEntity();

						filterInsDetails.setInstName(instituteGlobalEntrity.getInstName());
						filterInsDetails.setInstEndDate(instituteGlobalEntrity.getInstEndDate());
						filterInsDetails.setInstWebsite(instituteGlobalEntrity.getInstWebsite());
						filterInsDetails.setInstEmail(instituteGlobalEntrity.getInstEmail());
						filterInsDetails.setInstCnum(instituteGlobalEntrity.getInstCnum());
						filterInsDetails.setInstMnum(instituteGlobalEntrity.getInstMnum());
						filterInsDetails.setIsntRegDate(instituteGlobalEntrity.getIsntRegDate());
						filterInsDetails.setInstLogo(instituteGlobalEntrity.getInstLogo());
						filterInsDetails.setInstPanNum(instituteGlobalEntrity.getInstPanNum());
						filterInsDetails.setInstGstNum(instituteGlobalEntrity.getInstGstNum());
						filterInsDetails.setInstStatus("1");
						filterInsDetails.setIsActive(1);
						filterInsDetails.setIsDeleted(0);
						filterInsDetails.setCreatedOn(new Date());

						InstituteEntity save = instituteRepo.save(filterInsDetails);

						InstituteAddressEntity filterInsAdrDetails = new InstituteAddressEntity();

						filterInsAdrDetails.setAdrCity(instituteGlobalEntrity.getAdrCity());
						filterInsAdrDetails.setAdrCountry(instituteGlobalEntrity.getAdrCountry());
						filterInsAdrDetails.setAdrDistrict(instituteGlobalEntrity.getAdrDistrict());
						filterInsAdrDetails.setAdrLine1(instituteGlobalEntrity.getAdrLine1());
						filterInsAdrDetails.setAdrLine2(instituteGlobalEntrity.getAdrLine2());
						filterInsAdrDetails.setAdrOrder((long) 1);
						filterInsAdrDetails.setAdrPincode(instituteGlobalEntrity.getAdrPincode());
						filterInsAdrDetails.setAdrState(instituteGlobalEntrity.getAdrState());
						filterInsAdrDetails.setAdrTaluka(instituteGlobalEntrity.getAdrTaluka());
						filterInsAdrDetails.setAdrType(instituteGlobalEntrity.getAdrType());
						filterInsAdrDetails.setInstId(save.getInstId());
						filterInsAdrDetails.setIsPrimary(1);
						filterInsAdrDetails.setAdrStatus("1");
						filterInsAdrDetails.setIsActive(1);
						filterInsAdrDetails.setIsDeleted(0);
						filterInsAdrDetails.setCreatedOn(new Date());
						

						InstituteAddressEntity InsAdrDetails = instituteAddressRepo.save(filterInsAdrDetails);
 
						InstituteAdminEntity filterInsAmdDetails = new InstituteAdminEntity();

						filterInsAmdDetails.setAmdFname(instituteGlobalEntrity.getAmdFname());
						filterInsAmdDetails.setAmdLname(instituteGlobalEntrity.getAmdLname());
						filterInsAmdDetails.setAmdDob(instituteGlobalEntrity.getAmdDob());
						filterInsAmdDetails.setAmdMnum(instituteGlobalEntrity.getAmdMnum());
						filterInsAmdDetails.setAmdEmail(instituteGlobalEntrity.getAmdEmail());
						filterInsAmdDetails.setAmdUsername(instituteGlobalEntrity.getAmdUsername());
						filterInsAmdDetails.setAmdPassword(passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword()));
						filterInsAmdDetails.setAmdPpic(instituteGlobalEntrity.getAmdPpic());
						filterInsAmdDetails.setInstId(save.getInstId());
						filterInsAmdDetails.setCreatedOn(new Date());
						

						InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);

						HttpHeaders headers = new HttpHeaders();
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);

						HttpEntity request = new HttpEntity(headers);
						ResponseEntity<InstituteGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Institute_Create",
								HttpMethod.GET, request, InstituteGlobalEntity.class);
						String ETSubject = responseEmailTemp.getBody().getEtSubject();
						String ETBody = responseEmailTemp.getBody().getEtBody();

						String ETTargetName = "__$name$__";
						String ETTargetUsername = "__$username$__";
						String ETTargetPassword = "__$password$__";
						String ETNameReplacement = instituteGlobalEntrity.getAmdFname() + " "
								+ instituteGlobalEntrity.getAmdLname();
						String ETUsernameReplacement = instituteGlobalEntrity.getAmdUsername();
						String ETPasswordReplacement = instituteGlobalEntrity.getAmdPassword();

						String processedName = ETBody.replace(ETTargetName, ETNameReplacement);
						String processedUsername = processedName.replace(ETTargetUsername, ETUsernameReplacement);
						String processedMailBodyContent = processedUsername.replace(ETTargetPassword,
								ETPasswordReplacement);

						String mailid = instituteGlobalEntrity.getAmdEmail();
						
						
						
						JSONObject requestJson = new JSONObject();
						requestJson.put("senderMailId", mailid);
						requestJson.put("subject", ETSubject);
						requestJson.put("body", processedMailBodyContent);
						requestJson.put("enableHtml", true);

						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response = new RestTemplate().postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);

						return new GlobalResponse("SUCCESS",200, "Institute Added Successfully");
					} else {
						throw new CustomException("Institute Email Already Exist!");
					}
				} else {
					throw new CustomException("Institute Name Already Exist!");
				}
			} else {
				throw new CustomException("Validation Error!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
//	@GetMapping("/views")
//	public String view(Model model) { 
//		LOGGER.info("Inside - InstituteController.view()");
////		Map<String, Object> params = new HashMap();
////		ModelAndView mav = new ModelAndView("InstituteRegister");
////	    mav.addObject("listEmployees", instituteRepo.findAll());
//	    
//	    InstituteEntity employee= new InstituteEntity();
//		 model.addAttribute("employee", employee);
//		return "InstituteRegister";
//	
//	}
	
	@GetMapping("/view/{instId}")
	public Optional<InstituteEntity> viewInstituteDetails(@PathVariable() long instId) {
		LOGGER.info("Inside - InstituteController.viewInstituteDetails()");
		try {
			Optional<InstituteEntity> findById = this.instituteRepo.findById(instId);
			if (!(findById.isPresent())) {
				throw new CustomException("Institute Not Found!");
			} else {
				return findById;
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	

	@PutMapping("/update/{instId}")
	public GlobalResponse putInstituteDetails(@Valid @RequestBody InstituteGlobalEntity instituteGlobalEntrity,
			@PathVariable("instId") long instId, @RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - InstituteController.putInstituteDetails()");
		try {
			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine2()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdFname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdLname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdDob()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdUsername()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdPassword()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdId()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstId()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrId()))) {
				
				Optional<InstituteEntity> findById = instituteRepo.findById(instId);
				List<InstituteEntity> findByInstName = instituteRepo.findByInstUnqName(instId,
						instituteGlobalEntrity.getInstName());
				List<InstituteEntity> findByInstEmail = instituteRepo.findByInstUnqEmail(instId,
						instituteGlobalEntrity.getInstEmail());
				Optional<InstituteAddressEntity> findByAdrId = instituteAddressRepo
						.findById(instituteGlobalEntrity.getAdrId());
				Optional<InstituteAdminEntity> findByAdminId = instituteAdminRepo
						.findById(instituteGlobalEntrity.getAmdId());

				if (findById.isPresent()) {

					if (findByInstName.size() == 0) {
						if (findByInstEmail.size() == 0) {

							InstituteEntity InstEntrity = new InstituteEntity();

							InstEntrity.setInstCnum(instituteGlobalEntrity.getInstCnum());
							InstEntrity.setInstEmail(instituteGlobalEntrity.getInstEmail());
							InstEntrity.setInstEndDate(instituteGlobalEntrity.getInstEndDate());
							InstEntrity.setInstGstNum(instituteGlobalEntrity.getInstGstNum());
							InstEntrity.setInstId(instituteGlobalEntrity.getInstId());
							InstEntrity.setInstLogo(instituteGlobalEntrity.getInstLogo());
							InstEntrity.setInstMnum(instituteGlobalEntrity.getInstMnum());
							InstEntrity.setInstName(instituteGlobalEntrity.getInstName());
							InstEntrity.setInstPanNum(instituteGlobalEntrity.getInstPanNum());
							InstEntrity.setInstStatus(findById.get().getInstStatus());
							InstEntrity.setInstWebsite(instituteGlobalEntrity.getInstWebsite());
							InstEntrity.setIsActive(findById.get().getIsActive());
							InstEntrity.setIsntRegDate(findById.get().getIsntRegDate());
							InstEntrity.setCreatedOn(findById.get().getCreatedOn());
							InstEntrity.setUpdatedOn(new Date());							
							InstEntrity.setIsDeleted(findById.get().getIsDeleted());
							InstituteEntity save = instituteRepo.save(InstEntrity);

							if (findByAdrId.isPresent()) {

								InstituteAddressEntity filterInsAdrDetails = new InstituteAddressEntity();

								filterInsAdrDetails.setAdrId(instituteGlobalEntrity.getAdrId());
								filterInsAdrDetails.setAdrCity(instituteGlobalEntrity.getAdrCity());
								filterInsAdrDetails.setAdrCountry(instituteGlobalEntrity.getAdrCountry());
								filterInsAdrDetails.setAdrDistrict(instituteGlobalEntrity.getAdrDistrict());
								filterInsAdrDetails.setAdrLine1(instituteGlobalEntrity.getAdrLine1());
								filterInsAdrDetails.setAdrLine2(instituteGlobalEntrity.getAdrLine2());
								filterInsAdrDetails.setAdrOrder(instituteGlobalEntrity.getAdrOrder());
								filterInsAdrDetails.setAdrPincode(instituteGlobalEntrity.getAdrPincode());
								filterInsAdrDetails.setAdrState(instituteGlobalEntrity.getAdrState());
								filterInsAdrDetails.setAdrTaluka(instituteGlobalEntrity.getAdrTaluka());
								filterInsAdrDetails.setAdrType(instituteGlobalEntrity.getAdrType());
								filterInsAdrDetails.setInstId(save.getInstId());
								filterInsAdrDetails.setIsPrimary(findByAdrId.get().getIsPrimary());
								filterInsAdrDetails.setAdrStatus(findByAdrId.get().getAdrStatus());
								filterInsAdrDetails.setIsActive(findByAdrId.get().getIsActive());
								filterInsAdrDetails.setIsDeleted(findByAdrId.get().getIsDeleted());
								filterInsAdrDetails.setCreatedOn(findByAdrId.get().getCreatedOn());
								filterInsAdrDetails.setUpdatedOn(new Date());

								InstituteAddressEntity InsAdrDetails = instituteAddressRepo.save(filterInsAdrDetails);
							}

							InstituteAdminEntity filterInsAmdDetails = new InstituteAdminEntity();

							if (findByAdminId.isPresent()) {
								String Password;
								boolean PassCheck=findByAdminId.get().getAmdPassword().equals(instituteGlobalEntrity.getAmdPassword());
								if(PassCheck == true) {
									 Password=instituteGlobalEntrity.getAmdPassword();
								}else {
									 Password=passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword());
								}
								
								filterInsAmdDetails.setAmdId(instituteGlobalEntrity.getAmdId());
								filterInsAmdDetails.setAmdFname(instituteGlobalEntrity.getAmdFname());
								filterInsAmdDetails.setAmdLname(instituteGlobalEntrity.getAmdLname());
								filterInsAmdDetails.setAmdDob(instituteGlobalEntrity.getAmdDob());
								filterInsAmdDetails.setAmdMnum(instituteGlobalEntrity.getAmdMnum());
								filterInsAmdDetails.setAmdEmail(instituteGlobalEntrity.getAmdEmail());
								filterInsAmdDetails.setAmdUsername(instituteGlobalEntrity.getAmdUsername());
								filterInsAmdDetails.setAmdPassword(Password);
								filterInsAmdDetails.setAmdPpic(instituteGlobalEntrity.getAmdPpic());
								filterInsAmdDetails.setInstId(save.getInstId());
								filterInsAmdDetails.setCreatedOn(findByAdminId.get().getCreatedOn());
								filterInsAmdDetails.setUpdatedOn(new Date());

								InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);
							}
							return new GlobalResponse("SUCCESS",200, "Institute Updated Successfully");

						} else {
							throw new CustomException("Institute Email Already Exist!");
						}
					} else {
						throw new CustomException("Institute Name Already Exist!");
					}
				} else {
					throw new CustomException("Institute Not Exist!");
				}
			} else {
				throw new CustomException("Validation Error!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
