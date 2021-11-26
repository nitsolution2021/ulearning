package org.ulearn.licenseservice.entity;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class LicenseGlobalEntity {

	@Column(name = "LC_ID")
	private Long lcId;
	
	//@NotEmpty(message = "Please select any institute name")
	@Column(name = "INST_ID")
	private Long instId;
	
	//@NotEmpty(message = "License name can not be null ")
	@Column(name="LC_NAME")
	private String lcName;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	@Column(name = "LC_CDATE")
	private Date lcCreatDate;
	
	//@NotEmpty(message = "License type can not be null ")
	@Column(name = "LC_TYPE")
	private String lcType;
	
	//@NotEmpty(message = "License status type can not be null ")
	@Column(name = "LC_STYPE")
	private String lcStype;
	
	//@NotEmpty(message = "License validity type can not be null ")
	@Column(name = "LC_VALIDITY_TYPE")
	private String lcValidityType;
	
	//@NotEmpty(message = "License validity number can not be null ")
	@Column(name = "LC_VALIDITY_NUM")
	private int lcValidityNum;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	@Column(name = "LC_EDATE")
	private Date lcEndDate;
	
	//@NotEmpty(message = "License comment can not be null ")
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
	
	private String instEmail;
	private String instName;
	private String amdFname;
	private String amdLname;
	private String amdEmail;
	private InstituteAdminEntity instituteAdmin;
	
	private String etSubject;
	private String etBody;
	
	

	public LicenseGlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}




	public LicenseGlobalEntity(Long lcId, Long instId, String lcName, Date lcCreatDate, String lcType, String lcStype,
			String lcValidityType, int lcValidityNum, Date lcEndDate, String lcComment, String lcStatus, int isActive,
			int isDeleted, Date createdOn, Date updatedOn, String instEmail, String instName, String amdFname,
			String amdLname, String amdEmail, InstituteAdminEntity instituteAdmin, String etSubject, String etBody) {
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
		this.instEmail = instEmail;
		this.instName = instName;
		this.amdFname = amdFname;
		this.amdLname = amdLname;
		this.amdEmail = amdEmail;
		this.instituteAdmin = instituteAdmin;
		this.etSubject = etSubject;
		this.etBody = etBody;
	}




	@Override
	public String toString() {
		return "LicenseGlobalEntity [lcId=" + lcId + ", instId=" + instId + ", lcName=" + lcName + ", lcCreatDate="
				+ lcCreatDate + ", lcType=" + lcType + ", lcStype=" + lcStype + ", lcValidityType=" + lcValidityType
				+ ", lcValidityNum=" + lcValidityNum + ", lcEndDate=" + lcEndDate + ", lcComment=" + lcComment
				+ ", lcStatus=" + lcStatus + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", instEmail=" + instEmail + ", instName=" + instName
				+ ", amdFname=" + amdFname + ", amdLname=" + amdLname + ", amdEmail=" + amdEmail + ", instituteAdmin="
				+ instituteAdmin + ", etSubject=" + etSubject + ", etBody=" + etBody + "]";
	}




	public InstituteAdminEntity getInstituteAdmin() {
		return instituteAdmin;
	}

	public void setInstituteAdmin(InstituteAdminEntity instituteAdmin) {
		this.instituteAdmin = instituteAdmin;
	}





	public String getAmdFname() {
		return amdFname;
	}
	
	public void setAmdFname(String amdFname) {
		this.amdFname = amdFname;
	}
	
	public String getAmdLname() {
		return amdLname;
	}

	public void setAmdLname(String amdLname) {
		this.amdLname = amdLname;
	}


	public String getAmdEmail() {
		return amdEmail;
	}

	public void setAmdEmail(String amdEmail) {
		this.amdEmail = amdEmail;
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

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
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

	public String getLcName() {
		return lcName;
	}

	public void setLcName(String lcName) {
		this.lcName = lcName;
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

	public String getInstEmail() {
		return instEmail;
	}

	public void setInstEmail(String instEmail) {
		this.instEmail = instEmail;
	}
	
	
}
