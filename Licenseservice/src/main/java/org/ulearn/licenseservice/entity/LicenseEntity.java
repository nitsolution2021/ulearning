package org.ulearn.licenseservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_inst_license")
public class LicenseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LC_ID")
	private Long lcId;
	
	
	@Column(name = "INST_ID")
	private Long instId;
	
	@Column(name="LC_NAME")
	private String lcName;
	
	@JsonFormat(pattern = "yyyy/mm/dd")
	@Column(name = "LC_CDATE")
	private Date lcCreatDate;
	
	@Column(name = "LC_TYPE")
	private String lcType;
	
	@Column(name = "LC_STYPE")
	private String lcStype;
	
	@Column(name = "LC_VALIDITY_TYPE")
	private String lcValidityType;
	
	@Column(name = "LC_VALIDITY_NUM")
	private int lcValidityNum;
	
	@JsonFormat(pattern = "yyyy/mm/dd")
	@Column(name = "LC_EDATE")
	private Date lcEndDate;
	
	@Column(name = "LC_COMMENT")
	private String lcComment;
	
	@Column(name = "LC_STATUS")
	private String lcStatus;
	
	@Column(name = "IS_ACTIVE")
	private int isActive;
	
	@Column(name = "IS_DELETED")
	private int isDeleted;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	

	

	
	public LicenseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LicenseEntity(Long lcId, Long instId, String lcName, Date lcCreatDate, String lcType, String lcStype,
			String lcValidityType, int lcValidityNum, Date lcEndDate, String lcComment, String lcStatus,
			int isActive, int isDeleted, Date createdOn, Date updatedOn) {
		super();
		this.lcId = lcId;
		this.instId = instId;
		this.lcName = lcName;
		this.lcCreatDate = lcCreatDate;
		this.lcType = lcType;
		this.lcStype = lcStype;
		this.lcValidityType = lcValidityType;
		this.lcValidityNum = lcValidityNum;
		this.lcEndDate = lcEndDate;
		this.lcComment = lcComment;
		this.lcStatus = lcStatus;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "LicenseEntity [lcId=" + lcId + ", instId=" + instId + ", lcName=" + lcName + ", lcCreatDate="
				+ lcCreatDate + ", lcType=" + lcType + ", lcStype=" + lcStype + ", lcValidityType=" + lcValidityType
				+ ", lcValidityNum=" + lcValidityNum + ", lcEndDate=" + lcEndDate + ", lcComment="
				+ lcComment + ", lcStatus=" + lcStatus + ", isActive=" + isActive + ", isDeleted=" + isDeleted
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", getLcName()=" + getLcName()
				+ ", getLcId()=" + getLcId() + ", getInstId()=" + getInstId() + ", getLcCreatDate()=" + getLcCreatDate()
				+ ", getLcType()=" + getLcType() + ", getLcStype()=" + getLcStype() + ", getLcValidityType()="
				+ getLcValidityType() + ", getLcValidityNum()=" + getLcValidityNum() + ", getLcEndDate()="
				+ getLcEndDate() + ", getLcComment()=" + getLcComment()
				+ ", getLcStatus()=" + getLcStatus() + ", getIsActive()=" + getIsActive() + ", getIsDeleted()="
				+ getIsDeleted() + ", getCreatedOn()=" + getCreatedOn() + ", getUpdatedOn()=" + getUpdatedOn()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public String getLcName() {
		return lcName;
	}

	public void setLcName(String lcName) {
		this.lcName = lcName;
	}

	public Long getLcId() {
		return lcId;
	}

	public void setLcId(Long lcId) {
		this.lcId = lcId;
	}

	public Long getInstId() {
		return instId;
	}

	public void setInstId(Long instId) {
		this.instId = instId;
	}

	public Date getLcCreatDate() {
		return lcCreatDate;
	}

	public void setLcCreatDate(Date lcCreatDate) {
		this.lcCreatDate = lcCreatDate;
	}

	public String getLcType() {
		return lcType;
	}

	public void setLcType(String lcType) {
		this.lcType = lcType;
	}

	public String getLcStype() {
		return lcStype;
	}

	public void setLcStype(String lcStype) {
		this.lcStype = lcStype;
	}

	public String getLcValidityType() {
		return lcValidityType;
	}

	public void setLcValidityType(String lcValidityType) {
		this.lcValidityType = lcValidityType;
	}

	public int getLcValidityNum() {
		return lcValidityNum;
	}

	public void setLcValidityNum(int lcValidityNum) {
		this.lcValidityNum = lcValidityNum;
	}

	public Date getLcEndDate() {
		return lcEndDate;
	}

	public void setLcEndDate(Date lcEndDate) {
		this.lcEndDate = lcEndDate;
	}

	public String getLcComment() {
		return lcComment;
	}

	public void setLcComment(String lcComment) {
		this.lcComment = lcComment;
	}

	public String getLcStatus() {
		return lcStatus;
	}

	public void setLcStatus(String lcStatus) {
		this.lcStatus = lcStatus;
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
