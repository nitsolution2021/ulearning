package org.ulearn.smstemplateservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.smstemplateservice.entity.SmsTemplateEntity;

public interface SmsTemplateRepo extends JpaRepository<SmsTemplateEntity, Long> {

}
