package org.ulearn.emailtemplateservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ulearn.emailtemplateservice.entity.EmailTemplateEntity;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplateEntity, Long>{
	
	@Query(value = "select obj from EmailTemplateEntity obj where obj.isDeleted = ?1 and obj.etId = ?2")
	public List<EmailTemplateEntity> findByIdAndDelete(int isDeleted, Long etId);
	
	@Query(value = "select obj from EmailTemplateEntity obj where obj.isDeleted = ?1")
	public List<EmailTemplateEntity> findAllAndDelete(int isDeleted);
	
	@Query(value = "select obj from EmailTemplateEntity obj where obj.etType = ?1")
	public List<EmailTemplateEntity> findAllByDefaultTemplate(String etType);
	
	public List<EmailTemplateEntity> findByEtAction(String etAction);
	
	@Query(value = "select obj from EmailTemplateEntity obj where obj.etAction = ?1 and obj.etType = ?2")
	public List<EmailTemplateEntity> findByEtActionWithDefaultET(String etAction,String etType);
	
	@Query(value = "select obj.etId,obj.etAction from EmailTemplateEntity obj where obj.etType = ?1")
	public List<EmailTemplateEntity> findAllByDefaultET(String etType);
	
	@Query(value = "select obj from EmailTemplateEntity obj where obj.etAction = ?1 and obj.isPrimary = ?2")
	public List<EmailTemplateEntity> getPrimaryETByAction(String etAction, int isPrimary);
}
