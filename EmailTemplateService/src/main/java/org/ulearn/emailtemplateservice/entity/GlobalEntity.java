package org.ulearn.emailtemplateservice.entity;

import java.util.Date;

import javax.persistence.Column;

public class GlobalEntity {
	private Long etId;
	private String etName;
	private String etSubject;
	private String etBody;
	private String etType;
	private String etAction;
	private Long isActive;
	private Long isDeleted;
	private Long isPrimary;
	private Long etOrder;
	private String etTags;
	private Date createdOn;
	private Date updatedOn;
	public GlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GlobalEntity(Long etId, String etName, String etSubject, String etBody, String etType,
			String etAction, Long isActive, Long isDeleted, Long isPrimary, Long etOrder, String etTags, Date createdOn,
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
	public Long getIsActive() {
		return isActive;
	}
	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}
	public Long getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Long isPrimary) {
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
