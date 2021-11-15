package org.ulearn.smstemplateservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.smstemplateservice.entity.GlobalEntity;
import org.ulearn.smstemplateservice.entity.GlobalResponseEntity;
import org.ulearn.smstemplateservice.entity.SmsTemplateEntity;
import org.ulearn.smstemplateservice.exception.CustomException;
import org.ulearn.smstemplateservice.services.SmsTemplateService;

@RestController
@RequestMapping("/smsTemplate")
public class SmsTemplateController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsTemplateController.class);
	
	@Autowired
	private SmsTemplateService smsTemplateService; 
	
	@PostMapping("/add")
	public GlobalResponseEntity addSmsTemplate(@RequestBody GlobalEntity globalEntity) {
		LOGGER.info("Inside - SmsTemplateController.addSmsTemplate()");
		try {
			
			return smsTemplateService.addSmsTemplate(globalEntity);
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/list")
	public List<SmsTemplateEntity> getSmsTemplate() {
		LOGGER.info("Inside - SmsTemplateController.getSmsTemplate()");
		try {
			return smsTemplateService.getSmsTemplate();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/update/{stId}")
	public GlobalResponseEntity updateSmsTemplate(@PathVariable Long stId, @RequestBody GlobalEntity globalEntity) {
		LOGGER.info("Inside - SmsTemplateController.getSmsTemplate()");
		try {
			return smsTemplateService.updateSmsTemplate(stId, globalEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{stId}")
	public GlobalResponseEntity deleteSmsTemplate(@PathVariable Long stId) {
		LOGGER.info("Inside - SmsTemplateController.deleteSmsTemplate()");
		try {
			return smsTemplateService.deleteSmsTemplate(stId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/list/{stId}")
	public SmsTemplateEntity getSmsTemplateById(@PathVariable Long stId) {
		LOGGER.info("Inside - SmsTemplateController.getSmsTemplateById()");
		try {
			return smsTemplateService.getSmsTemplateById(stId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/smsFor/list")
	public List<SmsTemplateEntity> getSmsTemplateForDropDown() {
		LOGGER.info("Inside - SmsTemplateController.getSmsTemplateForDropDown()");
		try {
			return smsTemplateService.getSmsTemplateForDropDown();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
}
