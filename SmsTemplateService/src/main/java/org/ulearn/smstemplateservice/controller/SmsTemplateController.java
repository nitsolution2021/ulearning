package org.ulearn.smstemplateservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/list/{delete}")
	public Map<String, Object> getSmsTemplate(@PathVariable int delete, @RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - SmsTemplateController.getSmsTemplate()");
		try {
			return smsTemplateService.getSmsTemplate(delete, page, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping(value = "/list/{page}/{limit}/{sortName}/{sort}")
	public Map<String, Object> getSmsTemplatePagination(@PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@RequestParam(defaultValue = "") Optional<String> keyword, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - SmsTemplateController.getSmsTemplatePagination()");
		try {
			return smsTemplateService.getSmsTemplatePagination(page, limit, sort, sortName, keyword, sortBy);
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
	
	@PutMapping("/setDefault/{stId}/{stAction}")
	public GlobalResponseEntity setDefaultSmsTemplate(@PathVariable Long stId, @PathVariable String stAction) {
		LOGGER.info("Inside - SmsTemplateController.setDefaultSmsTemplate()");
		try {
			return smsTemplateService.setDefaultSmsTemplate(stId, stAction);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/getPrimarySTByAction/{action}")
	public SmsTemplateEntity getPrimaryETByAction(@PathVariable String action) {
		LOGGER.info("Inside - SmsTemplateController.getPrimaryETByAction()");
		try {
			return smsTemplateService.getPrimaryETByAction(action);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/getTags/{action}")
	public List<Map<String, String>> getTags(@PathVariable String action) {
		LOGGER.info("Inside - SmsTemplateController.getTags()");
		try {
			return smsTemplateService.getTags(action);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	
}
