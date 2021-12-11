package org.ulearn.instituteservice.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;
import org.ulearn.instituteservice.exception.CustomException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class CustomFunction {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomFunction.class);

	public void SentSMS(String processedSMSBodyContent, String number, String ETStTempId) {
		try {

			String encode = UriUtils.encodePath(processedSMSBodyContent, "UTF-8");
			Unirest.setTimeouts(0, 0);
			HttpResponse<JsonNode> asJson = Unirest.get(
					"http://msg.jmdinfotek.in/api/mt/SendSMS?channel=Trans&DCS=0&flashsms=0&route=07&senderid=uLearn&user=technosoft_dev&password=Techno@8585&text="
							+ encode + "&number=" + number + "&dlt=" + ETStTempId)
					.asJson();
			org.json.JSONObject object = asJson.getBody().getObject();
			String ErrorCode = object.getString("ErrorCode");
			LOGGER.info("Inside - CustomFunction.SentSMS()-AHADUL-" + asJson.getBody() + "//");
			if (ErrorCode.equals("006")) {
				throw new CustomException("Invalid Template Text Of SMS Gateway!");
			} else if (!ErrorCode.equals("000")) {
				throw new CustomException("Failed to Sent SMS!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public void SentEmail(HttpEntity<String> entity) {

		try {
			ResponseEntity<String> response = new RestTemplate()
					.postForEntity("http://65.1.66.115:8086/dev/login/sendMail/", entity, String.class);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<InstituteGlobalEntity> GetEmailDetails(String token, String ActionType) {

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity request = new HttpEntity(headers);
			ResponseEntity<InstituteGlobalEntity> responseEmailTemp = new RestTemplate().exchange(
					"http://65.1.66.115:8090/dev/emailTemplate/getPrimaryETByAction/" + ActionType,
					HttpMethod.GET, request, InstituteGlobalEntity.class);

			return responseEmailTemp;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	public ResponseEntity<InstituteGlobalEntity> GetSMSDetails(String token, String ActionType) {

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.setContentType(MediaType.APPLICATION_JSON);
			LOGGER.info("Inside - CustomFunction.SentSMS()-AHADUL-" + ActionType + "//");
			HttpEntity request = new HttpEntity(headers);
			ResponseEntity<InstituteGlobalEntity> responseSmsTemp = new RestTemplate().exchange(
					"http://65.1.66.115:8091/dev/smsTemplate/getPrimarySTByAction/"+ActionType,
					HttpMethod.GET, request, InstituteGlobalEntity.class);

			return responseSmsTemp;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
