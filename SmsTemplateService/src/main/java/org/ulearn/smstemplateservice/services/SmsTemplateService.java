package org.ulearn.smstemplateservice.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.smstemplateservice.entity.GlobalEntity;
import org.ulearn.smstemplateservice.entity.GlobalResponseEntity;
import org.ulearn.smstemplateservice.entity.SmsTemplateEntity;
import org.ulearn.smstemplateservice.exception.CustomException;
import org.ulearn.smstemplateservice.repository.SmsTemplateRepo;
import org.ulearn.smstemplateservice.validation.FieldValidation;

@Service
public class SmsTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsTemplateService.class);

	@Autowired
	private SmsTemplateRepo smsTemplateRepo;

	@Autowired
	private FieldValidation fieldValidation;

	public GlobalResponseEntity addSmsTemplate(GlobalEntity globalEntity) {
		LOGGER.info("Inside - SmsTemplateService.addSmsTemplate()");
		try {

			if (validateData(globalEntity)) {
				SmsTemplateEntity smsTemplateEntity = new SmsTemplateEntity();
				smsTemplateEntity.setIsActive(1);
				smsTemplateEntity.setIsDeleted(0);
				smsTemplateEntity.setIsPrimary(globalEntity.getIsPrimary());
				smsTemplateEntity.setStAction(globalEntity.getStAction());
				smsTemplateEntity.setCreatedOn(new Date());
				smsTemplateEntity.setStBody(globalEntity.getStBody());
				smsTemplateEntity.setStName(globalEntity.getStName());
				smsTemplateEntity.setStOrder(globalEntity.getStOrder());
				smsTemplateEntity.setStTags(globalEntity.getStTags());
				smsTemplateEntity.setStTempId(globalEntity.getStTempId());
				SmsTemplateEntity save = smsTemplateRepo.save(smsTemplateEntity);
				if (!save.equals(null)) {
					return new GlobalResponseEntity("Success", "SMS template added successful");
				} else {
					throw new CustomException("Internal server error");
				}

			} else {
				throw new CustomException("Validation error");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<SmsTemplateEntity> getSmsTemplate() {
		LOGGER.info("Inside - SmsTemplateService.getSmsTemplate()");
		try {
			List<SmsTemplateEntity> findAll = smsTemplateRepo.findAll();
			if (findAll.size() > 0) {
				return findAll;
			} else {
				throw new CustomException("Data not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponseEntity updateSmsTemplate(Long stId, GlobalEntity globalEntity) {
		LOGGER.info("Inside - SmsTemplateService.updateSmsTemplate()");
		try {
			Optional<SmsTemplateEntity> findById = smsTemplateRepo.findById(stId);
			if (findById.isPresent()) {
				if (validateData(globalEntity)) {
					SmsTemplateEntity smsTemplateEntity = new SmsTemplateEntity();
					smsTemplateEntity.setIsActive(findById.get().getIsActive());
					smsTemplateEntity.setIsDeleted(findById.get().getIsDeleted());
					smsTemplateEntity.setIsPrimary(globalEntity.getIsPrimary());
					smsTemplateEntity.setStAction(globalEntity.getStAction());
					smsTemplateEntity.setCreatedOn(findById.get().getCreatedOn());
					smsTemplateEntity.setStBody(globalEntity.getStBody());
					smsTemplateEntity.setStName(globalEntity.getStName());
					smsTemplateEntity.setStOrder(findById.get().getStOrder());
					smsTemplateEntity.setStTags(findById.get().getStTags());
					smsTemplateEntity.setStTempId(globalEntity.getStTempId());
					smsTemplateEntity.setStId(stId);
					SmsTemplateEntity save = smsTemplateRepo.save(smsTemplateEntity);
					if (!save.equals(null)) {
						return new GlobalResponseEntity("Success", "SMS template updated successful");
					} else {
						throw new CustomException("Data not save");
					}
				} else {
					throw new CustomException("Validation error");
				}
			} else {
				throw new CustomException("Data not found for your request");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	private boolean validateData(GlobalEntity globalEntity) {

		if (!fieldValidation.isEmpty(globalEntity.getStName())) {
			throw new CustomException("Template Name required");
		} else if (!fieldValidation.isEmpty(globalEntity.getStSubject())) {
			// SMS template Subject represent SMS Header
			throw new CustomException("SMS template header required");
		} else if (!fieldValidation.isEmpty(globalEntity.getStBody())) {
			// SMS template Body represent SMS Content
			throw new CustomException("SMS template content required");
		} else if (!fieldValidation.isEmpty(globalEntity.getStType())) {
			// SMS template Type represent Template For
			throw new CustomException("Template For required");
		} else if (!fieldValidation.isEmpty(globalEntity.getIsPrimary())) {
			// SMS template isPrimary represent Set Default
			throw new CustomException("Set Default required");
		} else if (!fieldValidation.isEmpty(globalEntity.getStTempId())) {
			// stTempId represent Header Id
			throw new CustomException("Header Id required");
		} else {
			return true;
		}

	}
}
