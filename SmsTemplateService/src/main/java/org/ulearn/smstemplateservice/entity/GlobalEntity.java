package org.ulearn.smstemplateservice.entity;

import java.util.Date;

public class GlobalEntity {
	private Long stId;
	private String stName;
	private String stSubject;
	private String stBody;
	private String stType;
	private String stAction;
	private int isActive;
	private int isDeleted;
	private int isPrimary;
	private int stOrder;
	private String stTags;
	private String stTempId;
	private Date createdOn;
	private Date updatedOn;

	public GlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalEntity(Long stId, String stName, String stSubject, String stBody, String stType, String stAction,
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
		return "GlobalEntity [stId=" + stId + ", stName=" + stName + ", stSubject=" + stSubject + ", stBody=" + stBody
				+ ", stType=" + stType + ", stAction=" + stAction + ", isActive=" + isActive + ", isDeleted="
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
