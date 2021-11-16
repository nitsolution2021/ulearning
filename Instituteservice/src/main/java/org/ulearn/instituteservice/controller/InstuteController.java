package org.ulearn.instituteservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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

import springfox.documentation.spring.web.json.Json;

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
	public List<InstituteEntity> getInstute() {
		LOGGER.info("Inside - InstituteController.getInstute()");

		try {


			List<InstituteEntity> findAll = instituteRepo.findAll();

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
	public GlobalResponse postInstituteDetails(@Valid @RequestBody InstituteGlobalEntity instituteGlobalEntrity,@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - InstituteController.postInstituteDetails()");

		try {					
			
					
			Optional<InstituteEntity> findByInstituteName = instituteRepo.findByInstName(instituteGlobalEntrity.getInstName());
			Optional<InstituteEntity> findByInstEmail = instituteRepo.findByInstEmail(instituteGlobalEntrity.getInstEmail());

			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrOrder()))
//					& (fieldValidation.isEmpty(instituteGlobalEntrity.getIsPrimary()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdFname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdLname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdDob()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdUsername()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdPassword()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdPpic()))					
					) {
				if (!findByInstituteName.isPresent()) {
					if (!findByInstEmail.isPresent()) {

						InstituteEntity filterInsDetails = new InstituteEntity();

						filterInsDetails.setInstName(instituteGlobalEntrity.getInstName());
						filterInsDetails.setInstEndDate(instituteGlobalEntrity.getInstEndDate());
						filterInsDetails.setInstWebsite(instituteGlobalEntrity.getInstWebsite());
						filterInsDetails.setInstEmail(instituteGlobalEntrity.getInstEmail());
						filterInsDetails.setInstCnum(instituteGlobalEntrity.getInstCnum());
						filterInsDetails.setInstMnum(instituteGlobalEntrity.getInstMnum());
						filterInsDetails.setIsntRegDate(new Date());
						filterInsDetails.setInstLogo(instituteGlobalEntrity.getInstLogo());
						filterInsDetails.setInstPanNum(instituteGlobalEntrity.getInstPanNum());
						filterInsDetails.setInstGstNum(instituteGlobalEntrity.getInstGstNum());
						filterInsDetails.setInstStatus(instituteGlobalEntrity.getInstStatus());
						filterInsDetails.setIsActive(0);
						filterInsDetails.setIsDeleted(0);
						filterInsDetails.setCreatedOn(new Date());
						filterInsDetails.setUpdatedOn(new Date());

						InstituteEntity save = instituteRepo.save(filterInsDetails);
						
						
						InstituteAddressEntity filterInsAdrDetails = new InstituteAddressEntity();

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
						filterInsAdrDetails.setIsPrimary(instituteGlobalEntrity.getIsPrimary());						
						filterInsAdrDetails.setAdrStatus(instituteGlobalEntrity.getAdrStatus());
						filterInsAdrDetails.setIsActive(0);
						filterInsAdrDetails.setIsDeleted(0);
						filterInsAdrDetails.setCreatedOn(new Date());
						filterInsAdrDetails.setUpdatedOn(new Date());

						InstituteAddressEntity InsAdrDetails = instituteAddressRepo.save(filterInsAdrDetails);
						
						
						String mailid = instituteGlobalEntrity.getAmdEmail();
						String subject = "Institute Admin Registration from uLearn";
						String body = "Dear "+instituteGlobalEntrity.getAmdFname()+" "+instituteGlobalEntrity.getAmdLname()+
									"<br><br> Welcome to uLearn <br><br>"
									+"Your are successfully register with us.<br><br>"
									+"You login Credentials is - <br>"
									+"Username - "+instituteGlobalEntrity.getAmdUsername()+"<br>"
									+"Password - "+instituteGlobalEntrity.getAmdPassword()+"<br><br>"
									+"Regards,<br>uLearn.co.in";
						
						HttpHeaders headers = new HttpHeaders();
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);
						
						JSONObject requestJson = new JSONObject();
						requestJson.put("senderMailId", mailid);
						requestJson.put("subject", subject);
						requestJson.put("body", body);
						requestJson.put("enableHtml", true);

						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response=new RestTemplate().postForEntity("http://localhost:8088/dev/login/sendMail/", entity, String.class);						
												
						
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
						filterInsAmdDetails.setUpdatedOn(new Date());

						InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);
						
						return new GlobalResponse("SUCCESS", "Institute Added Successfully");
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
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))					
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrOrder()))
//					& (fieldValidation.isEmpty(instituteGlobalEntrity.getIsPrimary()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdFname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdLname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdDob()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdUsername()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdPassword()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdPpic()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdmId()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstId()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrId()))
					) {
				Optional<InstituteEntity> findById = instituteRepo.findById(instId);
				List<InstituteEntity> findByInstName = instituteRepo.findByInstUnqName(instId,instituteGlobalEntrity.getInstName());
//				LOGGER.info("Inside - InstituteController.putInstituteDetails()///"+findById);
				List<InstituteEntity> findByInstEmail = instituteRepo.findByInstUnqEmail(instId,instituteGlobalEntrity.getInstEmail());
				Optional<InstituteAddressEntity> findByAdrId = instituteAddressRepo.findById(instituteGlobalEntrity.getAdrId());
				Optional<InstituteAdminEntity> findByAdminId = instituteAdminRepo.findById(instituteGlobalEntrity.getAdmId());
				
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
							InstEntrity.setInstStatus(instituteGlobalEntrity.getInstStatus());
							InstEntrity.setInstWebsite(instituteGlobalEntrity.getInstWebsite());
							InstEntrity.setIsActive(instituteGlobalEntrity.getIsActive());
							InstEntrity.setIsntRegDate(findById.get().getIsntRegDate());
							InstEntrity.setUpdatedOn(new Date());
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
							filterInsAdrDetails.setIsPrimary(instituteGlobalEntrity.getIsPrimary());						
							filterInsAdrDetails.setAdrStatus(instituteGlobalEntrity.getAdrStatus());
							filterInsAdrDetails.setIsActive(0);
							filterInsAdrDetails.setIsDeleted(0);
							filterInsAdrDetails.setCreatedOn(new Date());
							filterInsAdrDetails.setUpdatedOn(new Date());

							InstituteAddressEntity InsAdrDetails = instituteAddressRepo.save(filterInsAdrDetails);
							}
							
							InstituteAdminEntity filterInsAmdDetails = new InstituteAdminEntity();							
														
							
//							if(findByAdmId.get().getAmdUsername() != instituteGlobalEntrity.getAmdUsername() && findByAdmId.get().getAmdEmail() != ""){}
//							String mailid = instituteGlobalEntrity.getAmdEmail();
//							String subject = "Institute Admin Registration from uLearn";
//							String body = "Dear "+instituteGlobalEntrity.getAmdFname()+" "+instituteGlobalEntrity.getAmdLname()+
//										"<br><br> Welcome to uLearn <br><br>"
//										+"Your are successfully register with us.<br><br>"
//										+"You login Credentials is - <br>"
//										+"Username - "+instituteGlobalEntrity.getAmdUsername()+"<br>"
//										+"Password - "+instituteGlobalEntrity.getAmdPassword()+"<br><br>"
//										+"Regards,<br>uLearn.co.in";
//							
//							HttpHeaders headers = new HttpHeaders();
//							headers.set("Authorization", token);
//							headers.setContentType(MediaType.APPLICATION_JSON);
//							
//							JSONObject requestJson = new JSONObject();
//							requestJson.put("senderMailId", mailid);
//							requestJson.put("subject", subject);
//							requestJson.put("body", body);
//							requestJson.put("enableHtml", true);
//
//							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
//							ResponseEntity<String> response=new RestTemplate().postForEntity("http://localhost:8088/dev/login/sendMail/", entity, String.class);
							
							if (findByAdminId.isPresent()) {
								
							filterInsAmdDetails.setAdmId(instituteGlobalEntrity.getAmdId());
							filterInsAmdDetails.setAmdFname(instituteGlobalEntrity.getAmdFname());
							filterInsAmdDetails.setAmdLname(instituteGlobalEntrity.getAmdLname());
							filterInsAmdDetails.setAmdDob(instituteGlobalEntrity.getAmdDob());
							filterInsAmdDetails.setAmdMnum(instituteGlobalEntrity.getAmdMnum());
							filterInsAmdDetails.setAmdEmail(instituteGlobalEntrity.getAmdEmail());
							filterInsAmdDetails.setAmdUsername(instituteGlobalEntrity.getAmdUsername());
							filterInsAmdDetails.setAmdPassword(passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword()));
							filterInsAmdDetails.setAmdPpic(instituteGlobalEntrity.getAmdPpic());
							filterInsAmdDetails.setInstId(save.getInstId());
							filterInsAmdDetails.setCreatedOn(findByAdminId.get().getCreatedOn());
							filterInsAmdDetails.setUpdatedOn(new Date());

							InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);
							}
							return new GlobalResponse("SUCCESS", "Institute Updated Successfully");
							
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
	@GetMapping("/insIdvalidation/{insId}")
	public String insIdvalidation(@PathVariable long insId)
	{
		try
		{
			LOGGER.info("Inside-InstituteController.insIdvalidation");
			if(instituteRepo.existsById(insId))
			{
				return "Ok";
			}
			else
			{
				return "notOk";
			}
		}
		catch(Exception e)
		{
			return "Exception";
		}
	}
}
