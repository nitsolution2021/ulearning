package org.ulearn.emailtemplateservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_email_templates")
public class EmailTemplateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ET_ID") private Long etId;
	@Column(name = "ET_NAME") private String etName;
	@Column(name = "ET_SUBJECT") private String etSubject;
	@Column(name = "ET_BODY") private String etBody;
	@Column(name = "ET_TYPE") private String etType;
	@Column(name = "ET_ACTION") private String etAction;
	@Column(name = "IS_ACTIVE") private int isActive;
	@Column(name = "IS_DELETED") private int isDeleted;
	@Column(name = "IS_PRIMARY") private int isPrimary;
	@Column(name = "ET_ORDER") private Long etOrder;
	@Column(name = "ET_TAGS") private String etTags;
	@Column(name = "ET_TAGS_NAME") private String etTagsName;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_ON") private Date updatedOn;
	public EmailTemplateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmailTemplateEntity(Long etId, String etName, String etSubject, String etBody, String etType,
			String etAction, int isActive, int isDeleted, int isPrimary, Long etOrder, String etTags, Date createdOn,
			Date updatedOn) {
		super();
		this.etId = etId;
		this.etName = etName;
		this.etSubject = etSubject;
		this.etBody = etBody;
		this.etType = etType;
		this.etAction = etAction;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.isPrimary = isPrimary;
		this.etOrder = etOrder;
		this.etTags = etTags;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	public Long getEtId() {
		return etId;
	}
	public void setEtId(Long etId) {
		this.etId = etId;
	}
	public String getEtName() {
		return etName;
	}
	public void setEtName(String etName) {
		this.etName = etName;
	}
	public String getEtSubject() {
		return etSubject;
	}
	public void setEtSubject(String etSubject) {
		this.etSubject = etSubject;
	}
	public String getEtBody() {
		return etBody;
	}
	public void setEtBody(String etBody) {
		this.etBody = etBody;
	}
	public String getEtType() {
		return etType;
	}
	public void setEtType(String etType) {
		this.etType = etType;
	}
	public String getEtAction() {
		return etAction;
	}
	public void setEtAction(String etAction) {
		this.etAction = etAction;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}
	public Long getEtOrder() {
		return etOrder;
	}
	public void setEtOrder(Long etOrder) {
		this.etOrder = etOrder;
	}
	public String getEtTags() {
		return etTags;
	}
	public void setEtTags(String etTags) {
		this.etTags = etTags;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "EmailTemplateEntity [etId=" + etId + ", etName=" + etName + ", etSubject=" + etSubject + ", etBody="
				+ etBody + ", etType=" + etType + ", etAction=" + etAction + ", isActive=" + isActive + ", isDeleted="
				+ isDeleted + ", isPrimary=" + isPrimary + ", etOrder=" + etOrder + ", etTags=" + etTags
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
	
	
	
	

}
