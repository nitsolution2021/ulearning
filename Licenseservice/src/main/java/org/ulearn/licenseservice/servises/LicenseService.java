package org.ulearn.licenseservice.servises;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import org.ulearn.licenseservice.controller.LicenseController;
import org.ulearn.licenseservice.entity.GlobalResponse;
import org.ulearn.licenseservice.entity.LicenseEntity;
import org.ulearn.licenseservice.entity.LicenseGlobalEntity;
import org.ulearn.licenseservice.entity.LicenseLogEntity;
import org.ulearn.licenseservice.exception.CustomException;
import org.ulearn.licenseservice.repository.LicenseLogRepo;
import org.ulearn.licenseservice.repository.LicenseRepo;
import org.ulearn.licenseservice.validation.FieldValidation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class LicenseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicenseController.class);

	@Autowired
	public LicenseRepo licenseRepo;

	@Autowired
	public FieldValidation fieldValidation;

	@Autowired
	public LicenseLogRepo licenseLogRepo;

	public GlobalResponse addLicense(LicenseEntity license, String token) {

		try {

			if (fieldValidation.isEmpty(license.getInstId()) & fieldValidation.isEmpty(license.getLcName())
					& fieldValidation.isEmpty(license.getLcCreatDate()) & fieldValidation.isEmpty(license.getLcType())
					& fieldValidation.isEmpty(license.getLcStype())
					& fieldValidation.isEmpty(license.getLcValidityType())
					& fieldValidation.isEmpty(license.getLcValidityNum())
					& fieldValidation.isEmpty(license.getLcEndDate())) {

				Optional<LicenseEntity> findByInstId = licenseRepo.findByInstId(license.getInstId());
				if (!findByInstId.isPresent()) {

					LicenseEntity licenseAdd = new LicenseEntity();

					// Set date for license table
					licenseAdd.setLcName(license.getLcName());
					licenseAdd.setInstId(license.getInstId());
					licenseAdd.setLcCreatDate(license.getLcCreatDate());
					licenseAdd.setLcType(license.getLcType());
					licenseAdd.setLcStype(license.getLcStype());
					licenseAdd.setLcValidityType(license.getLcValidityType());
					licenseAdd.setLcValidityNum(license.getLcValidityNum());
					licenseAdd.setLcEndDate(license.getLcEndDate());
					licenseAdd.setLcComment(license.getLcComment());
					licenseAdd.setLcStatus("Complete");
					licenseAdd.setIsActive(1);
					licenseAdd.setIsDeleted(0);
					licenseAdd.setCreatedOn(new Date());

					// Insert data into license table
					LicenseEntity save = licenseRepo.save(licenseAdd);

					// Set data for license log table

					LicenseLogEntity licenseLogAdd = new LicenseLogEntity();

					licenseLogAdd.setLcIdFk(save.getLcId());
					licenseLogAdd.setLlAction("Add license");
					licenseLogAdd.setLlValidityType(save.getLcValidityType());
					licenseLogAdd.setLlValidityNum(Integer.parseInt(save.getLcValidityNum() + ""));
					licenseLogAdd.setLlSdate(save.getLcCreatDate());
					licenseLogAdd.setLlEdate(save.getLcEndDate());
					licenseLogAdd.setLlComment(save.getLcComment());
					licenseLogAdd.setLlStatus("Complete");
					licenseLogAdd.setCreatedOn(new Date());

					LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);

					HttpHeaders headers = new HttpHeaders();
					headers.set("Authorization", token);
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity request = new HttpEntity(headers);

					String stLicnName = "";
					String stLicnValidatyNum = "";
					String stLicnValidatyType = "";
					String stLicnCreateDate = "";
					String stLicnEndDate = "";
					String stLicnServerType = "";
					String stLicnType = "";

					String ETTargetName = "__$AdmName$__";
					String stInstNameTags = "__$InstName$__";
					String stInstMailTags = "__$InstMail$__";
					String stLicnNameTags = "__$LcName$__";
					String stLicnValidatyNumTags = "__$LicnValidatyNum$__";
					String stLicnValidatyTypeTags = "__$LicnValidatyType$__";
					String stLicnCreateDateTags = "__$LicnCreateDate$__";
					String stLicnEndDateTags = "__$LicnEndDate$__";
					String stLicnServerTypeTags = "__$LicnServerType$__";
					String stLicnTypeTags = "__$LicnType$__";

					String emailId = "";
					String amdFname = "";
					String amdLname = "";
					String number = "";
					String stInstName = "";
					try {

						Unirest.setTimeouts(0, 0);
						HttpResponse<JsonNode> asJson = Unirest
								.get("http://65.1.66.115:8087/dev/institute/view/" + save.getInstId())
								.header("Authorization", token).asJson();
						org.json.JSONObject object = asJson.getBody().getObject();
						emailId = object.getString("instEmail");
						number = object.getString("instMnum");
						stInstName = object.getString("instName");
						JSONArray jsonArray = object.getJSONArray("instituteAdmin");
						org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
						amdFname = jsonObject.getString("amdFname");
						amdLname = jsonObject.getString("amdLname");
//							 LOGGER.info("Inside the LicenseController Update License"+object.getString("instEmail"));
					} catch (Exception e) {
						throw new CustomException("Problem In Institute Service");

					}

					String ETSubject;
					String ETBody;
//						String ETTargetName;
					String ETNameReplacement;
					String processedName;
					// HttpEntity request = new HttpEntity(headers);

					try {

						ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Create",
								HttpMethod.GET, request, LicenseGlobalEntity.class);
						ETSubject = responseEmailTemp.getBody().getEtSubject();
						ETBody = responseEmailTemp.getBody().getEtBody();

						ETNameReplacement = amdFname + " " + amdLname;
						stLicnName = save.getLcName();
						stLicnValidatyNum = save.getLcValidityNum() + "";
						stLicnValidatyType = save.getLcValidityType();
						stLicnCreateDate = save.getLcCreatDate() + "";
						stLicnEndDate = save.getLcEndDate() + "";
						stLicnServerType = save.getLcStype();
						stLicnType = save.getLcType();

						String replace = ETBody.replace(ETTargetName, ETNameReplacement);
						String replace2 = replace.replace(stInstNameTags, stInstName);
						String replace3 = replace2.replace(stInstMailTags, emailId);
						String replace4 = replace3.replace(stLicnNameTags, stLicnName);
						String replace5 = replace4.replace(stLicnValidatyNumTags, stLicnValidatyNum);
						String replace6 = replace5.replace(stLicnValidatyTypeTags, stLicnValidatyType);
						String replace7 = replace6.replace(stLicnCreateDateTags, stLicnCreateDate);
						String replace8 = replace7.replace(stLicnEndDateTags, stLicnEndDate);
						String replace9 = replace8.replace(stLicnServerTypeTags, stLicnServerType);
						processedName = replace9.replace(stLicnTypeTags, stLicnType);

					} catch (Exception e) {

						if (!save.equals(null)) {
							throw new CustomException(
									"License Have Added But There Is a Problem in EmailTemplate View.Please Check.");
						} else {
							throw new CustomException("Error From Mail template.");
						}

					}

					try {

						JSONObject requestJson = new JSONObject();
						requestJson.put("senderMailId", emailId);
						requestJson.put("subject", ETSubject);
						requestJson.put("body", processedName);
						requestJson.put("enableHtml", true);

						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response = new RestTemplate()
								.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
					} catch (Exception e) {

						if (!save.equals(null)) {
							throw new CustomException(
									"License Have Added But There Is a Problem In SendMail.Please Check.");
						} else {
							throw new CustomException("Error from sendMail.");
						}

					}

					// ****---------- CODE ADDED BY SOUMEN -------****//
					String processedSMSBodyContent = "";
					String ETStTempId = "";

					try {
						ResponseEntity<LicenseGlobalEntity> responseSmsTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8091/dev/smsTemplate/getPrimarySTByAction/License_Create",
								HttpMethod.GET, request, LicenseGlobalEntity.class);

//							String STSubject = responseSmsTemp.getBody().getStSubject();
						String STBody = responseSmsTemp.getBody().getStBody();
						ETStTempId = responseSmsTemp.getBody().getStTempId();

						ETNameReplacement = amdFname + " " + amdLname;
						stLicnName = save.getLcName();
						stLicnValidatyNum = save.getLcValidityNum() + "";
						stLicnValidatyType = save.getLcValidityType();
						stLicnCreateDate = save.getLcCreatDate() + "";
						stLicnEndDate = save.getLcEndDate() + "";
						stLicnServerType = save.getLcStype();
						stLicnType = save.getLcType();

						String replace = STBody.replace(ETTargetName, ETNameReplacement);
						String replace2 = replace.replace(stInstNameTags, stInstName);
						String replace3 = replace2.replace(stInstMailTags, emailId);
						String replace4 = replace3.replace(stLicnNameTags, stLicnName);
						String replace5 = replace4.replace(stLicnValidatyNumTags, stLicnValidatyNum);
						String replace6 = replace5.replace(stLicnValidatyTypeTags, stLicnValidatyType);
						String replace7 = replace6.replace(stLicnCreateDateTags, stLicnCreateDate);
						String replace8 = replace7.replace(stLicnEndDateTags, stLicnEndDate);
						String replace9 = replace8.replace(stLicnServerTypeTags, stLicnServerType);
						processedSMSBodyContent = replace9.replace(stLicnTypeTags, stLicnType);

						LOGGER.info("asJson  number " + number);

						number = number.substring(3);

					} catch (Exception e) {
//							throw new CustomException("SMS Service Is Not Running!");
						throw new CustomException("Gatting Error From SMS Template");
					}
					try {

						String encode = UriUtils.encodePath(processedSMSBodyContent, "UTF-8");
						Unirest.setTimeouts(0, 0);
						HttpResponse<JsonNode> asJson = Unirest.get(
								"http://msg.jmdinfotek.in/api/mt/SendSMS?channel=Trans&DCS=0&flashsms=0&route=07&senderid=uLearn&user=technosoft_dev&password=Techno@8585&text="
										+ encode + "&number=" + number + "&dlt=" + ETStTempId)
								.asJson();

						LOGGER.info("asJson url -  " + number);
						org.json.JSONObject object = asJson.getBody().getObject();
						String ErrorCode = object.getString("ErrorCode");

						if (ErrorCode.equals("006")) {
							throw new CustomException("Invalid Template Text!");
						} else if (!ErrorCode.equals("000")) {
							throw new CustomException("Failed to Sent SMS!");
						}
					} catch (Exception e) {
//							throw new CustomException(e.getMessage());
						throw new CustomException("Something Went To Wrong From SMS Gateway");
					}

					// **** CLOSED ****//

					if (!save.equals(null)) {
						return new GlobalResponse("SUCCESS", "License Added successfully", 200);
					} else {
						throw new CustomException("Data not store");
					}
				} else {
					throw new CustomException("A License Have Already Assigned For This Institute..");
				}
			} else {
				throw new CustomException("Some Required Value Are Missing..");
			}
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage());
		}

	}

	public GlobalResponse updateLicense(int licenseId, LicenseEntity licenseForUpdate, String token) {
		// TODO Auto-generated method stub

		try {

			if (fieldValidation.isEmpty(licenseForUpdate.getInstId())
					& fieldValidation.isEmpty(licenseForUpdate.getLcName())
					& fieldValidation.isEmpty(licenseForUpdate.getLcCreatDate())
					& fieldValidation.isEmpty(licenseForUpdate.getLcType())
					& fieldValidation.isEmpty(licenseForUpdate.getLcStype())
					& fieldValidation.isEmpty(licenseForUpdate.getLcValidityType())
					& fieldValidation.isEmpty(licenseForUpdate.getLcValidityNum())
					& fieldValidation.isEmpty(licenseForUpdate.getLcEndDate())) {

				Optional<LicenseEntity> findById = this.licenseRepo.findById(licenseId);

				if (findById.isPresent()) {

					Long instituteId = null;
					if (findById.get().getInstId().equals(licenseForUpdate.getInstId())) {
						instituteId = licenseForUpdate.getInstId();
					} else {
						Optional<LicenseEntity> findByInstId = licenseRepo.findByInstId(licenseForUpdate.getInstId());
						if (!findByInstId.isPresent()) {
							instituteId = licenseForUpdate.getInstId();
						} else {

							throw new CustomException(
									"A License Have Already Assigned for This Institute. Please Select Another Institute.");
						}
					}

					LicenseEntity licenseUpdate = new LicenseEntity();

					licenseUpdate.setLcName(licenseForUpdate.getLcName());
					licenseUpdate.setLcId(licenseId);
					licenseUpdate.setInstId(instituteId);
					licenseUpdate.setLcCreatDate(licenseForUpdate.getLcCreatDate());
					licenseUpdate.setLcType(licenseForUpdate.getLcType());
					licenseUpdate.setLcStype(licenseForUpdate.getLcStype());
					licenseUpdate.setLcValidityType(licenseForUpdate.getLcValidityType());
					licenseUpdate.setLcValidityNum(licenseForUpdate.getLcValidityNum());
					licenseUpdate.setLcEndDate(licenseForUpdate.getLcEndDate());
					licenseUpdate.setLcComment(licenseForUpdate.getLcComment());
					licenseUpdate.setLcStatus(findById.get().getLcStatus());
					licenseUpdate.setIsActive(findById.get().getIsActive());
					licenseUpdate.setIsDeleted(findById.get().getIsDeleted());
					licenseUpdate.setCreatedOn(findById.get().getCreatedOn());
					licenseUpdate.setUpdatedOn(new Date());

					LicenseEntity save = licenseRepo.save(licenseUpdate);

					// Set data for license log table

					LicenseLogEntity licenseLogAdd = new LicenseLogEntity();

					licenseLogAdd.setLcIdFk(save.getLcId());
					licenseLogAdd.setLlAction("Update license");
					licenseLogAdd.setLlValidityType(save.getLcValidityType());
					licenseLogAdd.setLlValidityNum(Integer.parseInt(save.getLcValidityNum() + ""));
					licenseLogAdd.setLlSdate(save.getLcCreatDate());
					licenseLogAdd.setLlEdate(save.getLcEndDate());
					licenseLogAdd.setLlComment(save.getLcComment());
					licenseLogAdd.setLlStatus("Complete");
					licenseLogAdd.setCreatedOn(new Date());
					licenseLogAdd.setUpdatedOn(new Date());

					LicenseLogEntity save2 = licenseLogRepo.save(licenseLogAdd);

					HttpHeaders headers = new HttpHeaders();
					headers.set("Authorization", token);
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity request = new HttpEntity(headers);

					String stLicnName = "";
					String stLicnValidatyNum = "";
					String stLicnValidatyType = "";
					String stLicnCreateDate = "";
					String stLicnEndDate = "";
					String stLicnServerType = "";
					String stLicnType = "";

					String ETTargetName = "__$AdmName$__";
					String stInstNameTags = "__$InstName$__";
					String stInstMailTags = "__$InstMail$__";
					String stLicnNameTags = "__$LcName$__";
					String stLicnValidatyNumTags = "__$LicnValidatyNum$__";
					String stLicnValidatyTypeTags = "__$LicnValidatyType$__";
					String stLicnCreateDateTags = "__$LicnCreateDate$__";
					String stLicnEndDateTags = "__$LicnEndDate$__";
					String stLicnServerTypeTags = "__$LicnServerType$__";
					String stLicnTypeTags = "__$LicnType$__";

					String emailId = null;
					String amdFname = null;
					String amdLname = null;
					String number = "";
					String stInstName = "";

					try {
						Unirest.setTimeouts(0, 0);
						HttpResponse<JsonNode> asJson = Unirest
								.get("http://65.1.66.115:8087/dev/institute/view/" + save.getInstId())
								.header("Authorization", token).asJson();
						org.json.JSONObject object = asJson.getBody().getObject();
//								 emailId =object.getString("instEmail");
						emailId = object.getString("instEmail");
						number = object.getString("instMnum");
						stInstName = object.getString("instName");
						JSONArray jsonArray = object.getJSONArray("instituteAdmin");
						org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
						amdFname = jsonObject.getString("amdFname");
						amdLname = jsonObject.getString("amdLname");
					} catch (Exception e) {
						if (!save.equals(null)) {
							throw new CustomException(
									"License Have Updated But There Is a Problem in Institute Service. Please Check.");
						} else {
							throw new CustomException("Error from institute view.");
						}

					}

					String ETSubject;
					String ETBody;
//							String ETTargetName;
					String ETNameReplacement;
					String processedName;
					// HttpEntity request = new HttpEntity(headers);

					try {

						ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Update",
								HttpMethod.GET, request, LicenseGlobalEntity.class);
						ETSubject = responseEmailTemp.getBody().getEtSubject();
						ETBody = responseEmailTemp.getBody().getEtBody();

						ETNameReplacement = amdFname + " " + amdLname;
						stLicnName = save.getLcName();
						stLicnValidatyNum = save.getLcValidityNum() + "";
						stLicnValidatyType = save.getLcValidityType();
						stLicnCreateDate = save.getLcCreatDate() + "";
						stLicnEndDate = save.getLcEndDate() + "";
						stLicnServerType = save.getLcStype();
						stLicnType = save.getLcType();

						String replace = ETBody.replace(ETTargetName, ETNameReplacement);
						String replace2 = replace.replace(stInstNameTags, stInstName);
						String replace3 = replace2.replace(stInstMailTags, emailId);
						String replace4 = replace3.replace(stLicnNameTags, stLicnName);
						String replace5 = replace4.replace(stLicnValidatyNumTags, stLicnValidatyNum);
						String replace6 = replace5.replace(stLicnValidatyTypeTags, stLicnValidatyType);
						String replace7 = replace6.replace(stLicnCreateDateTags, stLicnCreateDate);
						String replace8 = replace7.replace(stLicnEndDateTags, stLicnEndDate);
						String replace9 = replace8.replace(stLicnServerTypeTags, stLicnServerType);
						processedName = replace9.replace(stLicnTypeTags, stLicnType);

					} catch (Exception e) {
						if (!save.equals(null)) {
							throw new CustomException(
									"License Have Updated But There is a Problem In EmailTemplate Service. Please Check.");
						} else {
							throw new CustomException("Error from mail template.");
						}

					}

					try {

						JSONObject requestJson = new JSONObject();
						requestJson.put("senderMailId", emailId);
						requestJson.put("subject", ETSubject);
						requestJson.put("body", processedName);
						requestJson.put("enableHtml", true);

						HttpEntity<String> entity = new HttpEntity(requestJson, headers);
						ResponseEntity<String> response = new RestTemplate()
								.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
					} catch (Exception e) {
						if (!save.equals(null)) {
							throw new CustomException(
									"License have updated but there is a problem in sendMail.please check.");
						} else {
							throw new CustomException("Error from sendMail.");
						}

					}

					// ****---------- CODE ADDED BY SOUMEN -------****//
					String processedSMSBodyContent = null;
					String ETStTempId = null;

					try {
						ResponseEntity<LicenseGlobalEntity> responseSmsTemp = new RestTemplate().exchange(
								"http://65.1.66.115:8091/dev/smsTemplate/getPrimarySTByAction/License_Update",
								HttpMethod.GET, request, LicenseGlobalEntity.class);

						String STBody = responseSmsTemp.getBody().getStBody();
						ETStTempId = responseSmsTemp.getBody().getStTempId();

						ETNameReplacement = amdFname + " " + amdLname;

						stLicnName = save.getLcName();
						stLicnValidatyNum = save.getLcValidityNum() + "";
						stLicnValidatyType = save.getLcValidityType();
						stLicnCreateDate = save.getLcCreatDate() + "";
						stLicnEndDate = save.getLcEndDate() + "";
						stLicnServerType = save.getLcStype();
						stLicnType = save.getLcType();

						String replace = STBody.replace(ETTargetName, ETNameReplacement);
						String replace2 = replace.replace(stInstNameTags, stInstName);
						String replace3 = replace2.replace(stInstMailTags, emailId);
						String replace4 = replace3.replace(stLicnNameTags, stLicnName);
						String replace5 = replace4.replace(stLicnValidatyNumTags, stLicnValidatyNum);
						String replace6 = replace5.replace(stLicnValidatyTypeTags, stLicnValidatyType);
						String replace7 = replace6.replace(stLicnCreateDateTags, stLicnCreateDate);
						String replace8 = replace7.replace(stLicnEndDateTags, stLicnEndDate);
						String replace9 = replace8.replace(stLicnServerTypeTags, stLicnServerType);
						processedSMSBodyContent = replace9.replace(stLicnTypeTags, stLicnType);

						number = number.substring(3);

					} catch (Exception e) {
//								throw new CustomException("SMS Service Is Not Running!");
						throw new CustomException(e.getMessage());
					}
					try {

						String encode = UriUtils.encodePath(processedSMSBodyContent, "UTF-8");
						Unirest.setTimeouts(0, 0);
						HttpResponse<JsonNode> asJson = Unirest.get(
								"http://msg.jmdinfotek.in/api/mt/SendSMS?channel=Trans&DCS=0&flashsms=0&route=07&senderid=uLearn&user=technosoft_dev&password=Techno@8585&text="
										+ encode + "&number=" + number + "&dlt=" + ETStTempId)
								.asJson();

						org.json.JSONObject object = asJson.getBody().getObject();
						String ErrorCode = object.getString("ErrorCode");

						if (ErrorCode.equals("006")) {
							throw new CustomException("Invalid Template Text!");
						} else if (!ErrorCode.equals("000")) {
							throw new CustomException("Failed To Sent SMS!");
						}
					} catch (Exception e) {
						throw new CustomException(e.getMessage());
//								throw new CustomException("Something Went To Wrong From SMS Gateway");
					}

					// **** CLOSED ****//

					if (!save.equals(null)) {
						return new GlobalResponse("SUCCESS", "License Update Successfull", 200);
					} else {
						throw new CustomException("Update Not Successfull");
					}

				} else {
					throw new CustomException("License Not Found For This Id  " + licenseId);
				}
			} else {

				throw new CustomException("Some Required Value Are Missing, Please Check..");
			}
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage());
		}
	}

	public Map<String, Object> getAllLicenseList(Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		// TODO Auto-generated method stub

		int Limit = 10;
		try {

			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC,
					sortBy.orElse("createdOn"));
			Page<LicenseEntity> findAll = licenseRepo.findAll(pagingSort);

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

			if (findAll.getSize() < 1) {
				throw new CustomException("License Not Found!");
			} else {
				return response;
			}
//			
//			List<LicenseEntity> findAll = LicenseRepo.findAllIsNotDeleted();
//			if(findAll.size()<1) {
//				throw new CustomException("No license found");
//			}
//			else {
//				return findAll;
//			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> forGetLicensePagination(int page, int limit, Optional<String> sortBy, String sortName,
			String sort, int isDeleted, Optional<String> keyword) {

		try {
			Pageable pagingSort = null;
			int CountData = (int) licenseRepo.count();
			if (limit == 0) {
				limit = CountData;
			}
			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<LicenseEntity> findAll = null;

			if (keyword.get().isEmpty()) {

				findAll = licenseRepo.findByAllLicense(isDeleted, pagingSort);

			} else {

				findAll = licenseRepo.Search(keyword.get(), isDeleted, pagingSort);
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

			if (findAll.getSize() < 1) {
				throw new CustomException("License Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Optional<LicenseEntity> getLicense(int lcId) {
		// TODO Auto-generated method stub

		try {

			Optional<LicenseEntity> findById = licenseRepo.findById(lcId);
			if (findById.isPresent()) {
				return findById;
			} else {
				throw new CustomException("No License Found For This Id");
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse addSuspendLicense(int lcId, LicenseLogEntity licenseLogEntitySuspend, String token) {
		// TODO Auto-generated method stub

		try {

			if (fieldValidation.isEmpty(licenseLogEntitySuspend.getLlEdate())
					& fieldValidation.isEmpty(licenseLogEntitySuspend.getLlComment())) {

				LicenseEntity findById = this.licenseRepo.getById(lcId);
				if (findById.getIsActive() != 0) {

					Optional<LicenseLogEntity> findByLcId = licenseLogRepo.findByLcId(lcId, "Add suspend");
					if (findByLcId.isPresent()) {
						throw new CustomException("This license have already suspended..");
					} else {

						LicenseLogEntity licenseLogEntityForSuspend = new LicenseLogEntity();
						licenseLogEntityForSuspend.setLcIdFk(lcId);
						licenseLogEntityForSuspend.setLlAction("Add Suspend");
						licenseLogEntityForSuspend.setLlEdate(licenseLogEntitySuspend.getLlEdate());
						licenseLogEntityForSuspend.setLlComment(licenseLogEntitySuspend.getLlComment());
						licenseLogEntityForSuspend.setLlStatus("Complete");
						licenseLogEntityForSuspend.setCreatedOn(new Date());

						LicenseLogEntity save = licenseLogRepo.save(licenseLogEntityForSuspend);

						String lStatus = "Suspending";
						findById.setLcStatus(lStatus);
						LicenseEntity save2 = licenseRepo.save(findById);

//					For sending mail	
						HttpHeaders headers = new HttpHeaders();
						headers.set("Authorization", token);
						headers.setContentType(MediaType.APPLICATION_JSON);

						HttpEntity request = new HttpEntity(headers);

						String processedSMSBodyContent = "";
						String stLicnName = "";
						String stLicnValidatyNum = "";
						String stLicnValidatyType = "";
						String stLicnCreateDate = "";
						String stLicnEndDate = "";
						String stLicnServerType = "";
						String stLicnType = "";
						String ETStTempId = "";
						String stLicnSusDate=null;

						String ETTargetName = "__$AdmName$__";
						String stInstNameTags = "__$InstName$__";
						String stInstMailTags = "__$InstMail$__";
						String stLicnNameTags = "__$LicnName$__";
						String stLicnValidatyNumTags = "__$LicnValidatyNum$__";
						String stLicnValidatyTypeTags = "__$LicnValidatyTyp$__";
						String stLicnCreateDateTags = "__$LicnCreateDate$__";
						String stLicnEndDateTags = "__$LicnEndDate$__";
						String stLicnServerTypeTags = "__$LicnServerType$__";
						String stLicnTypeTags = "__$LicnType$__";
						String licenseSuspendDate = "__$LicnSuspendDate$__";

						String emailId = "";
						String amdFname = "";
						String amdLname = "";
						String number = "";
						String stInstName = "";
						
						String strDateFormat = "yyyy-MM-dd";
					    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
					    String formattedDate= dateFormat.format(licenseLogEntitySuspend.getLlEdate());
					    
						try {

							Unirest.setTimeouts(0, 0);
							HttpResponse<JsonNode> asJson = Unirest
									.get("http://65.1.66.115:8087/dev/institute/view/" + findById.getInstId())
									.header("Authorization", token).asJson();
							org.json.JSONObject object = asJson.getBody().getObject();
							emailId = object.getString("instEmail");
							number = object.getString("instMnum");
							stInstName = object.getString("instName");
							JSONArray jsonArray = object.getJSONArray("instituteAdmin");
							org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
							amdFname = jsonObject.getString("amdFname");
							amdLname = jsonObject.getString("amdLname");
//							 LOGGER.info("Inside the LicenseController Update License"+object.getString("instEmail"));
						} catch (Exception e) {
							throw new CustomException("Gatting Error From Institute Service.");

						}

						String ETSubject;
						String ETBody;
//						String ETTargetName;
//						String licenseSuspendDate;
						String ETNameReplacement;
						String ETDateReplacement;
						String processedDate;
						String processedName;
						// HttpEntity request = new HttpEntity(headers);

						try {

							ResponseEntity<LicenseGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
									"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/License_Suspend",
									HttpMethod.GET, request, LicenseGlobalEntity.class);
							ETSubject = responseEmailTemp.getBody().getEtSubject();
							ETBody = responseEmailTemp.getBody().getEtBody();

							stLicnName = save2.getLcName();
							stLicnValidatyNum = save2.getLcValidityNum() + "";
							stLicnValidatyType = save2.getLcValidityType();
							stLicnCreateDate = save2.getLcCreatDate() + "";
							stLicnEndDate = save2.getLcEndDate() + "";
							stLicnServerType = save2.getLcStype();
							stLicnType = save2.getLcType();

							ETNameReplacement = amdFname + " " + amdLname;
							ETDateReplacement = formattedDate;
							processedDate = ETBody.replace(licenseSuspendDate, ETDateReplacement);
							String replace2 = processedDate.replace(stInstNameTags, stInstName);
							String replace3 = replace2.replace(stInstMailTags, emailId);
							String replace4 = replace3.replace(stLicnNameTags, stLicnName);
							String replace5 = replace4.replace(stLicnValidatyNumTags, stLicnValidatyNum);
							String replace6 = replace5.replace(stLicnValidatyTypeTags, stLicnValidatyType);
							String replace7 = replace6.replace(stLicnCreateDateTags, stLicnCreateDate);
							String replace8 = replace7.replace(stLicnEndDateTags, stLicnEndDate);
							String replace9 = replace8.replace(stLicnServerTypeTags, stLicnServerType);
							String replace10 = replace9.replace(stLicnTypeTags, stLicnType);
							processedName = replace10.replace(ETTargetName, ETNameReplacement);

						} catch (Exception e) {

							throw new CustomException("Error from mail template.");
						}

						try {

							JSONObject requestJson = new JSONObject();
							requestJson.put("senderMailId", emailId);
							requestJson.put("subject", ETSubject);
							requestJson.put("body", processedName);
							requestJson.put("enableHtml", true);

							HttpEntity<String> entity = new HttpEntity(requestJson, headers);
							ResponseEntity<String> response = new RestTemplate()
									.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);
						} catch (Exception e) {

							throw new CustomException("Error from sendMail.");

						}

//						End send mail

						// ****---------- CODE ADDED BY SOUMEN -------****//

						try {
							ResponseEntity<LicenseGlobalEntity> responseSmsTemp = new RestTemplate().exchange(
									"http://65.1.66.115:8091/dev/smsTemplate/getPrimarySTByAction/License_Suspend",
									HttpMethod.GET, request, LicenseGlobalEntity.class);

//							String STSubject = responseSmsTemp.getBody().getStSubject();
							String STBody = responseSmsTemp.getBody().getStBody();
							ETStTempId = responseSmsTemp.getBody().getStTempId();

							ETNameReplacement = amdFname + " " + amdLname;

							stLicnName = save2.getLcName();
							stLicnValidatyNum = save2.getLcValidityNum() + "";
							stLicnValidatyType = save2.getLcValidityType();
							stLicnCreateDate = save2.getLcCreatDate() + "";
							stLicnEndDate = save2.getLcEndDate() + "";
							stLicnServerType = save2.getLcStype();
							stLicnType = save2.getLcType();
							stLicnSusDate = formattedDate;

							String replace = STBody.replace(ETTargetName, ETNameReplacement);
							String replace2 = replace.replace(stInstNameTags, stInstName);
							String replace3 = replace2.replace(stInstMailTags, emailId);
							String replace4 = replace3.replace(stLicnNameTags, stLicnName);
							String replace5 = replace4.replace(stLicnValidatyNumTags, stLicnValidatyNum);
							String replace6 = replace5.replace(stLicnValidatyTypeTags, stLicnValidatyType);
							String replace7 = replace6.replace(stLicnCreateDateTags, stLicnCreateDate);
							String replace8 = replace7.replace(stLicnEndDateTags, stLicnEndDate);
							String replace9 = replace8.replace(stLicnServerTypeTags, stLicnServerType);
							String replace10 = replace9.replace(licenseSuspendDate, stLicnSusDate);
							processedSMSBodyContent = replace10.replace(stLicnTypeTags, stLicnType);

							number = number.substring(3);

						} catch (Exception e) {
							throw new CustomException("SMS Service Is Not Running!");
//							throw new CustomException(e.getMessage());
						}
						try {

							String encode = UriUtils.encodePath(processedSMSBodyContent, "UTF-8");
							Unirest.setTimeouts(0, 0);
							HttpResponse<JsonNode> asJson = Unirest.get(
									"http://msg.jmdinfotek.in/api/mt/SendSMS?channel=Trans&DCS=0&flashsms=0&route=07&senderid=uLearn&user=technosoft_dev&password=Techno@8585&text="
											+ encode + "&number=" + number + "&dlt=" + ETStTempId)
									.asJson();

							org.json.JSONObject object = asJson.getBody().getObject();
							String ErrorCode = object.getString("ErrorCode");

							if (ErrorCode.equals("006")) {
								throw new CustomException("Invalid Template Text!");
							} else if (!ErrorCode.equals("000")) {
								throw new CustomException("Failed to Sent SMS!");
							}
						} catch (Exception e) {
							throw new CustomException(e.getMessage());
//							throw new CustomException("Something Went To Wrong From SMS Gateway");
						}

						// **** CLOSED ****//

						if (!save.equals(null)) {
							return new GlobalResponse("SUCCESS", "This License Will Be Suspend Of The Selected Date.",
									200);
						} else {
							throw new CustomException("Suspend Date Not Add.. ");
						}
					}
				} else {
					throw new CustomException("This License Has Not Active.");
				}
			} else {
				throw new CustomException("Some Required Value Are Missing, Please Check..");
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getSingleInstitutetLicenseService(Long instId, int page, int limit,
			Optional<String> sortBy, String sortName, String sort, Optional<String> keyword, int isDeleted) {

		try {
			Pageable pagingSort = null;
			int CountData = (int) licenseRepo.count();
			if (limit == 0) {
				limit = CountData;
			}
			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<LicenseEntity> findAll = licenseRepo.findByAllInstLicense(instId, isDeleted, pagingSort);

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

			if (findAll.getSize() < 1) {
				throw new CustomException("License Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
