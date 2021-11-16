package org.ulearn.smstemplateservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_sms_templates")
public class SmsTemplateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ST_ID")
	private Long stId;
	@Column(name = "ST_NAME")
	private String stName;
	@Column(name = "ST_SUBJECT")
	private String stSubject;
	@Column(name = "ST_BODY")
	private String stBody;
	@Column(name = "ST_TYPE")
	private String stType;
	@Column(name = "ST_ACTION")
	private String stAction;
	@Column(name = "IS_ACTIVE")
	private int isActive;
	@Column(name = "IS_DELETED")
	private int isDeleted;
	@Column(name = "IS_PRIMARY")
	private int isPrimary;
	@Column(name = "ST_ORDER")
	private int stOrder;
	@Column(name = "ST_TAGS")
	private String stTags;
	@Column(name = "ST_TEMP_ID")
	private String stTempId;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	public SmsTemplateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SmsTemplateEntity(Long stId, String stName, String stSubject, String stBody, String stType, String stAction,
			int isActive, int isDeleted, int isPrimary, int stOrder, String stTags, String stTempId, Date createdOn,
			Date updatedOn) {
		super();
		this.stId = stId;
		this.stName = stName;
		this.stSubject = stSubject;
		this.stBody = stBody;
		this.stType = stType;
		this.stAction = stAction;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.isPrimary = isPrimary;
		this.stOrder = stOrder;
		this.stTags = stTags;
		this.stTempId = stTempId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "SmsTemplateEntity [stId=" + stId + ", stName=" + stName + ", stSubject=" + stSubject + ", stBody="
				+ stBody + ", stType=" + stType + ", stAction=" + stAction + ", isActive=" + isActive + ", isDeleted="
				+ isDeleted + ", isPrimary=" + isPrimary + ", stOrder=" + stOrder + ", stTags=" + stTags + ", stTempId="
				+ stTempId + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

	public Long getStId() {
		return stId;
	}

	public void setStId(Long stId) {
		this.stId = stId;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	public String getStSubject() {
		return stSubject;
	}

	public void setStSubject(String stSubject) {
		this.stSubject = stSubject;
	}

	public String getStBody() {
		return stBody;
	}

	public void setStBody(String stBody) {
		this.stBody = stBody;
	}

	public String getStType() {
		return stType;
	}

	public void setStType(String stType) {
		this.stType = stType;
	}

	public String getStAction() {
		return stAction;
	}

	public void setStAction(String stAction) {
		this.stAction = stAction;
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

	public int getStOrder() {
		return stOrder;
	}

	public void setStOrder(int stOrder) {
		this.stOrder = stOrder;
	}

	public String getStTags() {
		return stTags;
	}

	public void setStTags(String stTags) {
		this.stTags = stTags;
	}

	public String getStTempId() {
		return stTempId;
	}

	public void setStTempId(String stTempId) {
		this.stTempId = stTempId;
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

}
