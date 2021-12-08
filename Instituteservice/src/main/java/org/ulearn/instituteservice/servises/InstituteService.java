package org.ulearn.instituteservice.servises;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ulearn.instituteservice.controller.InstuteController;
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

@Service
public class InstituteService {
	@Autowired
	private InstituteRepo instituteRepo;

	@Autowired
	private FieldValidation fieldValidation;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private InstituteAddressRepo instituteAddressRepo;

	@Autowired
	private InstituteAdminRepo instituteAdminRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);

	public Map<String, Object> getInstuteList(Optional<Integer> page, Optional<String> sortBy, int isDeleted) {

		try {
			int Limit = 10;
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC, sortBy.orElse("instId"));
			Page<InstituteEntity> findAll = instituteRepo.findByAllInst(isDeleted, pagingSort);

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}
			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getInstuteListWithPaginagtion(int page, int limit, String sort, String sortName,
			int isDeleted, Optional<String> keyword, Optional<String> sortBy) {

		try {

			Pageable pagingSort = null;

			if (sort == "ASC") {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}
			String keywordVal = keyword.get();
			Page<InstituteEntity> findAll = null;

			if (keyword.get().isEmpty()) {
				findAll = instituteRepo.findByAllInst(isDeleted, pagingSort);

			} else {
				findAll = instituteRepo.Search(isDeleted, keyword.get(), pagingSort);

			}

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse SendInstituteCredentials(InstituteEntity instituteEntrity, String token) {

		try {

			if ((fieldValidation.isEmpty(instituteEntrity.getInstId()))
					& (fieldValidation.isEmail(instituteEntrity.getInstEmail()))) {

				List<InstituteEntity> findByIdAndEmail = instituteRepo.findByIdAndEmail(instituteEntrity.getInstId(),
						instituteEntrity.getInstEmail());
				if (findByIdAndEmail.size() <= 1) {

					JSONObject requestJson = null;
					JSONObject requestJsons = null;
					HttpHeaders headers = new HttpHeaders();
					HttpHeaders headersForSms = new HttpHeaders();

					try {
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);

						HttpEntity request = new HttpEntity(headers);

						// ResponseEntity<String> person
						// =RestTemplate.postForEntity("http://sms.techno-soft.co.in/api/mt/SendSMS?user=technosoft_dev&password=Techno@8585&senderid=uLearn&channel=Promo&DCS=0&flashsms=0&number=917699983717&text=test
						// message&route=##&peid=##",HttpMethod.POST, headers,String.class);

//						Start fast2sms
//						headersForSms.set("Authorization", "gRdDP5EMSnQtkmuse41hjpIOo6Bb0XcN2rxKlZ8FJwGLY3zC9ANeazHgiPS7pZVjdUX0fb2rGTWskQIu");
//						End Start fast2sms

//						HttpEntity requestForSms = new HttpEntity<>(headersForSms);

//						Start fast2sms
//						requestJsons = new JSONObject();
//						requestJsons.put("route", "q");
//						requestJsons.put("message", "Testing Ahadul");
//						requestJsons.put("language", "english");
//						requestJsons.put("flash", 0);
//						requestJsons.put("numbers", "7699983717");
//						HttpEntity requestForSms = new HttpEntity<>(requestJsons,headersForSms);
//						End Start fast2sms

//						RestTemplate Template=new RestTemplate();
//						String postForObject = Template.postForObject("http://sms.techno-soft.co.in/api/mt/SendSMS?user=technosoft_dev&password=Techno@8585&senderid=uLearn&channel=Promo&DCS=0&flashsms=0&number=917699983717&text=test message&route=##&peid=##", requestForSms, String.class);
//						String postForObject = Template.postForObject("https://www.fast2sms.com/dev/bulkV2", requestForSms, String.class);

						ResponseEntity<InstituteGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Institute_Credentials",
								HttpMethod.GET, request, InstituteGlobalEntity.class);

						String ETSubject = responseEmailTemp.getBody().getEtSubject();
						String ETBody = responseEmailTemp.getBody().getEtBody();

						String Password = null;
						int length = 10;
						boolean useLetters = true;
						boolean useNumbers = false;
						Password = RandomStringUtils.random(length, useLetters, useNumbers);
						String HashPassword = passwordEncoder.encode(Password);

						InstituteAdminEntity getById = instituteAdminRepo.getById(findByIdAndEmail.get(0).getInstId());
						if (getById.getInstId() != null) {
							getById.setAmdPassword(HashPassword);
							getById.setUpdatedOn(new Date());
							InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(getById);

							String ETTargetName = "__$name$__";
							String ETTargetUsername = "__$username$__";
							String ETTargetPassword = "__$password$__";
							String ETNameReplacement = findByIdAndEmail.get(0).getInstituteAdmin().get(0).getAmdFname()
									+ " " + findByIdAndEmail.get(0).getInstituteAdmin().get(0).getAmdLname();
							String ETUsernameReplacement = findByIdAndEmail.get(0).getInstituteAdmin().get(0)
									.getAmdUsername();
							String ETPasswordReplacement = Password;

							String processedName = ETBody.replace(ETTargetName, ETNameReplacement);
							String processedUsername = processedName.replace(ETTargetUsername, ETUsernameReplacement);
							String processedMailBodyContent = processedUsername.replace(ETTargetPassword,
									ETPasswordReplacement);
							String mailid = findByIdAndEmail.get(0).getInstEmail();

							requestJson = new JSONObject();
							requestJson.put("senderMailId", mailid);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", processedMailBodyContent);
							requestJson.put("enableHtml", true);
						}
					} catch (Exception e) {
						if (e.getMessage().equals("No Data Present")) {
							throw new CustomException(e.getMessage());
						}
						throw new CustomException("Email Service Is Not Running!");
					}
					try {
						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response = new RestTemplate()
								.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
					} catch (Exception e) {
						throw new CustomException("Login Service Is Not Running");
					}
				}
			}
			return new GlobalResponse("SUCCESS", 200, "You Have Successfully Sent A Credentials");
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public List<InstituteEntity> getListInstuteService() {

		try {
			List<InstituteEntity> findAll = instituteRepo.findByListInst().stream()
					.filter(Inst -> Inst.getIsDeleted() == 0).filter(Inst -> Inst.getIsActive() == 1)
					.filter(instituteLicense -> instituteLicense.getInstituteLicense() == null)
					.collect(Collectors.toList());
			if (findAll.size() <= 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return findAll;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse postInstituteDetailsService(@Valid InstituteGlobalEntity instituteGlobalEntrity,
			String token) {
		try {

			Optional<InstituteEntity> findByInstituteName = instituteRepo
					.findByInstName(instituteGlobalEntrity.getInstName());
			Optional<InstituteEntity> findByInstEmail = instituteRepo
					.findByInstEmail(instituteGlobalEntrity.getInstEmail());

			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmail(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdFname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdLname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdDob()))
					& (fieldValidation.isEmail(instituteGlobalEntrity.getAmdEmail()))
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
						filterInsAmdDetails
								.setAmdPassword(passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword()));
						filterInsAmdDetails.setAmdPpic(instituteGlobalEntrity.getAmdPpic());
						filterInsAmdDetails.setInstId(save.getInstId());
						filterInsAmdDetails.setCreatedOn(new Date());

						InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);
						JSONObject requestJson = null;
						HttpHeaders headers = new HttpHeaders();
						try {

							headers.set("Authorization", token);
							headers.setContentType(MediaType.APPLICATION_JSON);

							HttpEntity request = new HttpEntity(headers);
							ResponseEntity<InstituteGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
									"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Institute_Create",
									HttpMethod.GET, request, InstituteGlobalEntity.class);

							String ETSubject = responseEmailTemp.getBody().getEtSubject();
							String ETBody = responseEmailTemp.getBody().getEtBody();

							String ETTargetName = "__$name$__";
//							String ETTargetUsername = "__$username$__";
//							String ETTargetPassword = "__$password$__";
							String ETNameReplacement = instituteGlobalEntrity.getAmdFname() + " "
									+ instituteGlobalEntrity.getAmdLname();
//							String ETUsernameReplacement = instituteGlobalEntrity.getAmdUsername();
//							String ETPasswordReplacement = instituteGlobalEntrity.getAmdPassword();

							String processedName = ETBody.replace(ETTargetName, ETNameReplacement);
//							String processedUsername = processedName.replace(ETTargetUsername, ETUsernameReplacement);
//							String processedMailBodyContent = processedUsername.replace(ETTargetPassword,ETPasswordReplacement);
							String mailid = instituteGlobalEntrity.getInstEmail();

							requestJson = new JSONObject();
							requestJson.put("senderMailId", mailid);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", processedName);
							requestJson.put("enableHtml", true);
						} catch (Exception e) {
							if (e.getMessage().equals("No Data Present")) {
								throw new CustomException(e.getMessage());
							}
							throw new CustomException("Institute Added Successfully But Email Service Is Not Running!");
						}
						try {
							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
							ResponseEntity<String> response = new RestTemplate()
									.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
						} catch (Exception e) {
							throw new CustomException("Institute Added Successfully But Login Service Is Not Running");
						}
						return new GlobalResponse("SUCCESS", 200, "Institute Added Successfully");
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

	public Optional<InstituteEntity> viewInstituteDetailsService(long instId) {
		try {
			Optional<InstituteEntity> findById = this.instituteRepo.findById(instId);
			if (!(findById.isPresent())) {
				throw new CustomException("Institute Not Found!");
			} else {
				LOGGER.info("findById:- " + findById.get().getInstituteAdmin().toString());
				return findById;
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse putInstituteDetailsService(@Valid InstituteGlobalEntity instituteGlobalEntrity, long instId,
			String token) {
		try {
			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmail(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdFname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdLname()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAmdDob()))
					& (fieldValidation.isEmail(instituteGlobalEntrity.getAmdEmail()))
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
								boolean PassCheck = findByAdminId.get().getAmdPassword()
										.equals(instituteGlobalEntrity.getAmdPassword());
								if (PassCheck == true) {
									Password = instituteGlobalEntrity.getAmdPassword();
								} else {
									Password = passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword());
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
								JSONObject requestJson = null;
								HttpHeaders headers = new HttpHeaders();
								try {

									headers.set("Authorization", token);
									headers.setContentType(MediaType.APPLICATION_JSON);

									HttpEntity request = new HttpEntity(headers);
									ResponseEntity<InstituteGlobalEntity> responseEmailTemp = new RestTemplate()
											.exchange(
													"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/Institute_Update",
													HttpMethod.GET, request, InstituteGlobalEntity.class);
									String ETSubject = responseEmailTemp.getBody().getEtSubject();
									String ETBody = responseEmailTemp.getBody().getEtBody();

									String ETTargetName = "__$instname$__";
									String ETNameReplacement = instituteGlobalEntrity.getInstName();
									String processedMailBodyContent = ETBody.replace(ETTargetName, ETNameReplacement);

									String mailid = instituteGlobalEntrity.getInstEmail();

									requestJson = new JSONObject();
									requestJson.put("senderMailId", mailid);
									requestJson.put("subject", ETSubject);
									requestJson.put("body", processedMailBodyContent);
									requestJson.put("enableHtml", true);

								} catch (Exception e) {
									if (e.getMessage().equals("No Data Present")) {
										throw new CustomException(e.getMessage());
									}
									throw new CustomException(
											"Institute Updated Successfully But Email Service Is Not Running!");
								}
								try {
									HttpEntity<String> entity = new HttpEntity(requestJson, headers);
									ResponseEntity<String> response = new RestTemplate().postForEntity(
											"http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
								} catch (Exception e) {
									throw new CustomException(
											"Institute Updated Successfully But Email Service Is Not Running!");
								}
							}
							return new GlobalResponse("SUCCESS", 200, "Institute Updated Successfully");

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
