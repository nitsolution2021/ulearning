package org.ulearn.smstemplateservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.smstemplateservice.entity.GlobalEntity;
import org.ulearn.smstemplateservice.entity.GlobalResponseEntity;
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
}
