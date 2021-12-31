package org.ulearn.packageservice.helper;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import org.ulearn.packageservice.entity.GlobalResponse;
import org.ulearn.packageservice.entity.InstituteEntity;
import org.ulearn.packageservice.entity.PackageEntity;
import org.ulearn.packageservice.entity.PackageGlobalTemplate;
import org.ulearn.packageservice.entity.PackageLogEntity;
import org.ulearn.packageservice.exception.CustomException;
import org.ulearn.packageservice.repo.InstituteRepo;
import org.ulearn.packageservice.repo.PackageLogRepo;
import org.ulearn.packageservice.repo.PackageRepo;
import org.ulearn.packageservice.services.PackageService;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
@Service
public class CustomFunction {
	@Autowired
	private PackageRepo packageRepo;
	
	@Autowired
	private InstituteRepo instituteRepo;

	@Autowired
	private PackageLogRepo packageLogRepo;
	
	//@Async("asyncExecutor")
	public ResponseEntity<PackageGlobalTemplate> getEmailDetails(String ETAction,String token)
	{
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity request = new HttpEntity(headers);
			String emailGetURL="http://65.1.66.115:8085/dev/emailTemplate/getPrimaryETByAction/"+ETAction;
			ResponseEntity<PackageGlobalTemplate> responseEmailTemp=new RestTemplate().exchange(emailGetURL,HttpMethod.GET, request, PackageGlobalTemplate.class);
			return responseEmailTemp;
			
		}
		catch(Exception e)
		{
			throw new CustomException("Email TemplateData Not Found");
		}
	}
	//@Async("asyncExecutor")
	public void sentEmail( PackageEntity newPackageEntity,String token,ResponseEntity<PackageGlobalTemplate>responseEmailTemp,PackageLogEntity packageLogEntity,String ETAction)
	{
		try
		{
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity request = new HttpEntity(headers);
			String ETSubject= responseEmailTemp.getBody().getEtSubject();
			String ETBody=responseEmailTemp.getBody().getEtBody();
			System.out.println("okk_Email");
			String ETAdminFname="__$amdFname$__";
			String ETinstId="__$instId$__";
			String ETinstName="__$instName$__";
			String ETinstMnum="__$instMnum$__";
			String ETpkId="__$pkId$__";
			String ETPackageFname="__$pkFname$__";
			String ETPackageDate="__$pkDate$__";
			//String ETPackageAdate="__$pkAdate$__";
			String ETPackageValidityNo="__$pkValidityNum$__";
			String ETPackgeValidityType="__$pkValidityType$__";
			PackageEntity packageEntity=packageRepo.getById(newPackageEntity.getPkId());
			InstituteEntity instituteData=instituteRepo.getById(packageEntity.getInstId());
			String ETreplace = ETBody.replace(ETAdminFname,instituteData.getInstituteAdmin().getAmdFname());
			String ETreplace1 = ETreplace.replace(ETinstId,packageEntity.getInstId().toString());
			String ETreplace2 = ETreplace1.replace(ETinstName,instituteData.getInstName());
			String ETreplace3 = ETreplace2.replace(ETinstMnum,instituteData.getInstMnum());
			String ETreplace4= ETreplace3.replace(ETpkId,packageEntity.getPkId().toString());
			String ETreplace5 = ETreplace4.replace(ETPackageFname,packageEntity.getPkFname());
			String ETreplace6=null;
			
			if(packageLogEntity.getPlAction().equals("Suspended") || packageLogEntity.getPlAction().equals("Package_End") || packageLogEntity.getPlAction().equals("Package_Restore"))
			{
				ETreplace6 = ETreplace5.replace(ETPackageDate,packageLogEntity.getPlAdate().toString());
			}
			else
			{
				ETreplace6 = ETreplace5.replace(ETPackageDate,packageEntity.getPkCdate().toString());
			}
			String ETreplace7 = ETreplace6.replace(ETPackageValidityNo,packageEntity.getPkValidityNum().toString() );
			String ETreplace8 = ETreplace7.replace(ETPackgeValidityType,packageEntity.getPkValidityType() );
			InstituteEntity instData=instituteRepo.getById(packageEntity.getInstId());
			JSONObject requestJson = new JSONObject();
			String mailId=instData.getInstEmail();
			requestJson.put("senderMailId", mailId);
			requestJson.put("subject", ETSubject);
			requestJson.put("body", ETreplace8);
			//System.out.println(requestJson);
			requestJson.put("enableHtml", true);
			HttpEntity<String> entity = new HttpEntity(requestJson, headers);
			ResponseEntity<String> response = new RestTemplate()
					.postForEntity("http://65.1.66.115:8085/dev/login/sendMail/", entity, String.class);
			
			
		}
		catch(Exception e)
		{
			getSMSDetail(token, ETAction);
		}
	}
	//@Async("asyncExecutor")
	public void sentSMS(PackageEntity packageEntity,String token,ResponseEntity<PackageGlobalTemplate> smsTemplateData)
	{
		try
		{
			String STSubject=smsTemplateData.getBody().getStSubject();
			String STBody=smsTemplateData.getBody().getStBody();
			InstituteEntity instituteData=instituteRepo.getById(packageEntity.getInstId());
			String STAdminFname="__$amdFname$__";
			String STPackageFname="_$InstName$_";
//			String STPackageCdate="__$pkCdate$__";
//			String STPackageValidityNo="__$pkValidityNum$__";
//			String STPackgeValidityType="__$pkValidityType$__";
			String STreplace = STBody.replace(STAdminFname,instituteData.getInstituteAdmin().getAmdFname());
			String STreplace1 = STreplace.replace(STPackageFname,packageEntity.getPkFname());
//			String STreplace2 = STreplace1.replace(STPackageCdate,packageData.getPkCdate().toString() );
//			String STreplace3 = STreplace2.replace(STPackageValidityNo,packageData.getPkValidityNum().toString() );
//			String STreplace4 = STreplace3.replace(STPackgeValidityType,packageData.getPkValidityType() );
			String amdMnum=instituteData.getInstMnum().substring(3);
			
			String encode = UriUtils.encode(STreplace1, "UTF-8");
			
			Unirest.setTimeouts(0, 0);
			HttpResponse<JsonNode> asJson = Unirest.get(
					"http://msg.jmdinfotek.in/api/mt/SendSMS?channel=Trans&DCS=0&flashsms=0&route=07&senderid=uLearn&user=technosoft_dev&password=Techno@8585&text="
							+ encode + "&number="+amdMnum+"&dlt="+smsTemplateData.getBody().getStTempId())
					.asJson();
			System.out.println("okk_SMS");
		}
		catch(Exception e)
		{
			
			throw new CustomException("SMS Service Is Not Running!");
		}
	}
	//@Async("asyncExecutor")
	public ResponseEntity<PackageGlobalTemplate> getSMSDetail(String token,String SMSAction)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity(headers);
		try
		{
			String SMSTemplateURL="http://65.1.66.115:8085/dev/smsTemplate/getPrimarySTByAction/"+SMSAction;
			ResponseEntity<PackageGlobalTemplate> responseSMSTemp = new RestTemplate().exchange(SMSTemplateURL,
					HttpMethod.GET, request, PackageGlobalTemplate.class);
			return responseSMSTemp;
		}
		catch(Exception e)
		{			
			throw new CustomException("Communication Service Not Woring But Process has been Successfully Done");
		}
	}
}
