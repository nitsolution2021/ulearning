package org.ulearn.instituteservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class InstituteGlobalEntity {


//	@Column(name = "INST_NAME")
	private String INST_NAME;
//	@Column(name = "INST_EDATE")
//	private Date instEndDate;
//	@Column(name = "INST_WEBSITE")
//	private String instWebsite;
//	@Column(name = "INST_EMAIL")
//	private String instEmail;
//	@Column(name = "INST_CNUM")
//	private String instCnum;
//	@Column(name = "INST_MNUM")
//	private String instMnum;
//	@Column(name = "ISNT_RDATE")
//	private Date isntRegDate;
//	@Column(name = "INST_LOGO")
//	private String instLogo;
//	@Column(name = "INST_PAN_NUM")
//	private String instPanNum;
//	@Column(name = "INST_GST_NUM")
//	private String instGstNum;
//	@Column(name = "INST_STATUS")
//	private String instStatus;
//	
//	
//	@Column(name = "ADR_ID")
//	private Long adrId;
//	
//	@Column(name = "INST_ID")
//	private Long instId;
//	
//	@Column(name = "ADR_TYPE")
//	private String adrType;
//	
//	@Column(name = "ADR_LINE1")
//	private String adrLine1;
//	
//	@Column(name = "ADR_LINE2")
//	private String adrLine2;
//	
//	@Column(name = "ADR_COUNTRY")
//	private Long adrCountry;
//	
//	@Column(name = "ADR_STATE")
//	private Long adrState;
//	
//	@Column(name = "ADR_DISTRICT")
//	private Long adrDistrict;
//	
//	@Column(name = "ADR_TALUKA")
//	private Long adrTaluka;
//	
//	@Column(name = "ADR_CITY")
//	private Long adrCity;
//	
//	@Column(name = "ADR_PINCODE")
//	private String adrPincode;
//	
//	@Column(name = "ADR_STATUS")
//	private String adrStatus;
//	
//	@Column(name = "ADR_ORDER")
//	private Long adrOrder;
//	
//	@Column(name = "IS_PRIMARY")
//	private int isPrimary;
//	
//	@Column(name = "IS_ACTIVE")
//	private int isActive;
//	
//	@Column(name = "IS_DELETED")
//	private int isDeleted;
//	
//	@Column(name = "CREATED_ON")
//	private Date createdOn;
//	
//	@Column(name = "UPDATED_ON")
//	private Date updatedOn;
//
//	public InstituteGlobalEntity(Long instId, String instName, Date instEndDate, String instWebsite, String instEmail,
//			String instCnum, String instMnum, Date isntRegDate, String instLogo, String instPanNum, String instGstNum,
//			String instStatus, int isActive, int isDeleted, Date createdOn, Date updatedOn, Long adrId, String adrType,
//			String adrLine1, String adrLine2, Long adrCountry, Long adrState, Long adrDistrict, Long adrTaluka,
//			Long adrCity, String adrPincode, String adrStatus, Long adrOrder, int isPrimary) {
//		super();
//		this.instId = instId;
//		this.instName = instName;
//		this.instEndDate = instEndDate;
//		this.instWebsite = instWebsite;
//		this.instEmail = instEmail;
//		this.instCnum = instCnum;
//		this.instMnum = instMnum;
//		this.isntRegDate = isntRegDate;
//		this.instLogo = instLogo;
//		this.instPanNum = instPanNum;
//		this.instGstNum = instGstNum;
//		this.instStatus = instStatus;
//		this.isActive = isActive;
//		this.isDeleted = isDeleted;
//		this.createdOn = createdOn;
//		this.updatedOn = updatedOn;
//		this.adrId = adrId;
//		this.adrType = adrType;
//		this.adrLine1 = adrLine1;
//		this.adrLine2 = adrLine2;
//		this.adrCountry = adrCountry;
//		this.adrState = adrState;
//		this.adrDistrict = adrDistrict;
//		this.adrTaluka = adrTaluka;
//		this.adrCity = adrCity;
//		this.adrPincode = adrPincode;
//		this.adrStatus = adrStatus;
//		this.adrOrder = adrOrder;
//		this.isPrimary = isPrimary;
//	}
//	public InstituteGlobalEntity() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public Long getInstId() {
//		return instId;
//	}
//	public void setInstId(Long instId) {
//		this.instId = instId;
//	}
//	public String getInstName() {
//		return instName;
//	}
//	public void setInstName(String instName) {
//		this.instName = instName;
//	}
//	public Date getInstEndDate() {
//		return instEndDate;
//	}
//	public void setInstEndDate(Date instEndDate) {
//		this.instEndDate = instEndDate;
//	}
//	public String getInstWebsite() {
//		return instWebsite;
//	}
//	public void setInstWebsite(String instWebsite) {
//		this.instWebsite = instWebsite;
//	}
//	public String getInstEmail() {
//		return instEmail;
//	}
//	public void setInstEmail(String instEmail) {
//		this.instEmail = instEmail;
//	}
//	public String getInstCnum() {
//		return instCnum;
//	}
//	public void setInstCnum(String instCnum) {
//		this.instCnum = instCnum;
//	}
//	public String getInstMnum() {
//		return instMnum;
//	}
//	public void setInstMnum(String instMnum) {
//		this.instMnum = instMnum;
//	}
//	public Date getIsntRegDate() {
//		return isntRegDate;
//	}
//	public void setIsntRegDate(Date isntRegDate) {
//		this.isntRegDate = isntRegDate;
//	}
//	public String getInstLogo() {
//		return instLogo;
//	}
//	public void setInstLogo(String instLogo) {
//		this.instLogo = instLogo;
//	}
//	public String getInstPanNum() {
//		return instPanNum;
//	}
//	public void setInstPanNum(String instPanNum) {
//		this.instPanNum = instPanNum;
//	}
//	public String getInstGstNum() {
//		return instGstNum;
//	}
//	public void setInstGstNum(String instGstNum) {
//		this.instGstNum = instGstNum;
//	}
//	public String getInstStatus() {
//		return instStatus;
//	}
//	public void setInstStatus(String instStatus) {
//		this.instStatus = instStatus;
//	}
//	public int getIsActive() {
//		return isActive;
//	}
//	public void setIsActive(int isActive) {
//		this.isActive = isActive;
//	}
//	public int getIsDeleted() {
//		return isDeleted;
//	}
//	public void setIsDeleted(int isDeleted) {
//		this.isDeleted = isDeleted;
//	}
//	public Date getCreatedOn() {
//		return createdOn;
//	}
//	public void setCreatedOn(Date createdOn) {
//		this.createdOn = createdOn;
//	}
//	public Date getUpdatedOn() {
//		return updatedOn;
//	}
//	public void setUpdatedOn(Date updatedOn) {
//		this.updatedOn = updatedOn;
//	}
//	public Long getAdrId() {
//		return adrId;
//	}
//	public void setAdrId(Long adrId) {
//		this.adrId = adrId;
//	}
//	public String getAdrType() {
//		return adrType;
//	}
//	public void setAdrType(String adrType) {
//		this.adrType = adrType;
//	}
//	public String getAdrLine1() {
//		return adrLine1;
//	}
//	public void setAdrLine1(String adrLine1) {
//		this.adrLine1 = adrLine1;
//	}
//	public String getAdrLine2() {
//		return adrLine2;
//	}
//	public void setAdrLine2(String adrLine2) {
//		this.adrLine2 = adrLine2;
//	}
//	public Long getAdrCountry() {
//		return adrCountry;
//	}
//	public void setAdrCountry(Long adrCountry) {
//		this.adrCountry = adrCountry;
//	}
//	public Long getAdrState() {
//		return adrState;
//	}
//	public void setAdrState(Long adrState) {
//		this.adrState = adrState;
//	}
//	public Long getAdrDistrict() {
//		return adrDistrict;
//	}
//	public void setAdrDistrict(Long adrDistrict) {
//		this.adrDistrict = adrDistrict;
//	}
//	public Long getAdrTaluka() {
//		return adrTaluka;
//	}
//	public void setAdrTaluka(Long adrTaluka) {
//		this.adrTaluka = adrTaluka;
//	}
//	public Long getAdrCity() {
//		return adrCity;
//	}
//	public void setAdrCity(Long adrCity) {
//		this.adrCity = adrCity;
//	}
//	public String getAdrPincode() {
//		return adrPincode;
//	}
//	public void setAdrPincode(String adrPincode) {
//		this.adrPincode = adrPincode;
//	}
//	public String getAdrStatus() {
//		return adrStatus;
//	}
//	public void setAdrStatus(String adrStatus) {
//		this.adrStatus = adrStatus;
//	}
//	public Long getAdrOrder() {
//		return adrOrder;
//	}
//	public void setAdrOrder(Long adrOrder) {
//		this.adrOrder = adrOrder;
//	}
//	public int getIsPrimary() {
//		return isPrimary;
//	}
//	public void setIsPrimary(int isPrimary) {
//		this.isPrimary = isPrimary;
//	}
//	@Override
//	public String toString() {
//		return "InstituteGlobalEntity [instId=" + instId + ", instName=" + instName + ", instEndDate=" + instEndDate
//				+ ", instWebsite=" + instWebsite + ", instEmail=" + instEmail + ", instCnum=" + instCnum + ", instMnum="
//				+ instMnum + ", isntRegDate=" + isntRegDate + ", instLogo=" + instLogo + ", instPanNum=" + instPanNum
//				+ ", instGstNum=" + instGstNum + ", instStatus=" + instStatus + ", isActive=" + isActive
//				+ ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", adrId="
//				+ adrId + ", adrType=" + adrType + ", adrLine1=" + adrLine1 + ", adrLine2=" + adrLine2 + ", adrCountry="
//				+ adrCountry + ", adrState=" + adrState + ", adrDistrict=" + adrDistrict + ", adrTaluka=" + adrTaluka
//				+ ", adrCity=" + adrCity + ", adrPincode=" + adrPincode + ", adrStatus=" + adrStatus + ", adrOrder="
//				+ adrOrder + ", isPrimary=" + isPrimary + "]";
//	}
	
	
	
	
}
