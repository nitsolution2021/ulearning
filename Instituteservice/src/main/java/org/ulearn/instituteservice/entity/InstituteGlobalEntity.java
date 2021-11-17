package org.ulearn.instituteservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InstituteGlobalEntity {


	private Long instId;
	@NotEmpty(message = "The Institute Name is Required")
	private String instName;
	private Date instEndDate;
	private String instWebsite;
	@Email
	private String instEmail;
	@NotEmpty(message = "The Institute Contact Number is Required")
	private String instCnum;
	@NotEmpty(message = "The Institute Mobile Number is Required")
	private String instMnum;
	private Date isntRegDate;
	@NotEmpty(message = "The Institute Logo is Required")
	private String instLogo;
	@NotEmpty(message = "The Institute Pan is Required")
	private String instPanNum;
	@NotEmpty(message = "The Institute GST Number is Required")
	private String instGstNum;
	@NotEmpty(message = "The Institute Status is Required")
	private String instStatus;
	private int isActive;
	private int isDeleted;
	private Date createdOn;
	private Date updatedOn;
	
	private Long adrId;
	private Long admId;
	private String adrType;
	@NotEmpty(message = "The Address Line 1 is Required")
	private String adrLine1;
	@NotEmpty(message = "The Address Line 2 is Required")
	private String adrLine2;	
	@Positive(message = "The Country is Required")
	private Long adrCountry;	
	@Positive(message = "The State Name is Required")
	private Long adrState;
	@Positive(message = "The District is Required")
	private Long adrDistrict;	
	@Positive(message = "The Taluka is Required")
	private Long adrTaluka;	
	@Positive(message = "The City is Required")
	private Long adrCity;
	@NotEmpty(message = "The Pincode is Required")
	private String adrPincode;
	private String adrStatus;
	private Long adrOrder;
	private int isPrimary;
	
	
	private Long amdId;
	@NotEmpty(message = "Owner Frist Name is Required")
	private String amdFname;
	@NotEmpty(message = "Owner Last Name is Required")
	private String amdLname;
	@NotNull(message = "Owner Date of Birth is Required")
	private Date amdDob;
	@NotEmpty(message = "Owner Mobile Number is Required")
	private String amdMnum;
	@Email
	private String amdEmail;
	@NotEmpty(message = "Owner Username is Required")
	private String amdUsername;
	@NotEmpty(message = "Owner Password is Required")
	private String amdPassword;
	private String amdPpic;
	
	
	private Long etId;
	private String etName;
	private String etSubject;
	private String etBody;
	private String etType;
	private String etAction;
	
	
	public InstituteGlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InstituteGlobalEntity(Long instId, @NotEmpty(message = "The Institute Name is Required") String instName,
			Date instEndDate, String instWebsite, @Email String instEmail,
			@NotEmpty(message = "The Institute Contact Number is Required") String instCnum,
			@NotEmpty(message = "The Institute Mobile Number is Required") String instMnum, Date isntRegDate,
			@NotEmpty(message = "The Institute Logo is Required") String instLogo,
			@NotEmpty(message = "The Institute Pan is Required") String instPanNum,
			@NotEmpty(message = "The Institute GST Number is Required") String instGstNum,
			@NotEmpty(message = "The Institute Status is Required") String instStatus, int isActive, int isDeleted,
			Date createdOn, Date updatedOn, Long adrId, Long admId, String adrType,
			@NotEmpty(message = "The Address Line 1 is Required") String adrLine1,
			@NotEmpty(message = "The Address Line 2 is Required") String adrLine2,
			@Positive(message = "The Country is Required") Long adrCountry,
			@Positive(message = "The State Name is Required") Long adrState,
			@Positive(message = "The District is Required") Long adrDistrict,
			@Positive(message = "The Taluka is Required") Long adrTaluka,
			@Positive(message = "The City is Required") Long adrCity,
			@NotEmpty(message = "The Pincode is Required") String adrPincode, String adrStatus, Long adrOrder,
			int isPrimary, Long amdId, @NotEmpty(message = "Owner Frist Name is Required") String amdFname,
			@NotEmpty(message = "Owner Last Name is Required") String amdLname,
			@NotNull(message = "Owner Date of Birth is Required") Date amdDob,
			@NotEmpty(message = "Owner Mobile Number is Required") String amdMnum, @Email String amdEmail,
			@NotEmpty(message = "Owner Username is Required") String amdUsername,
			@NotEmpty(message = "Owner Password is Required") String amdPassword, String amdPpic, Long etId,
			String etName, String etSubject, String etBody, String etType, String etAction) {
		super();
		this.instId = instId;
		this.instName = instName;
		this.instEndDate = instEndDate;
		this.instWebsite = instWebsite;
		this.instEmail = instEmail;
		this.instCnum = instCnum;
		this.instMnum = instMnum;
		this.isntRegDate = isntRegDate;
		this.instLogo = instLogo;
		this.instPanNum = instPanNum;
		this.instGstNum = instGstNum;
		this.instStatus = instStatus;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.adrId = adrId;
		this.admId = admId;
		this.adrType = adrType;
		this.adrLine1 = adrLine1;
		this.adrLine2 = adrLine2;
		this.adrCountry = adrCountry;
		this.adrState = adrState;
		this.adrDistrict = adrDistrict;
		this.adrTaluka = adrTaluka;
		this.adrCity = adrCity;
		this.adrPincode = adrPincode;
		this.adrStatus = adrStatus;
		this.adrOrder = adrOrder;
		this.isPrimary = isPrimary;
		this.amdId = amdId;
		this.amdFname = amdFname;
		this.amdLname = amdLname;
		this.amdDob = amdDob;
		this.amdMnum = amdMnum;
		this.amdEmail = amdEmail;
		this.amdUsername = amdUsername;
		this.amdPassword = amdPassword;
		this.amdPpic = amdPpic;
		this.etId = etId;
		this.etName = etName;
		this.etSubject = etSubject;
		this.etBody = etBody;
		this.etType = etType;
		this.etAction = etAction;
	}


	@Override
	public String toString() {
		return "InstituteGlobalEntity [instId=" + instId + ", instName=" + instName + ", instEndDate=" + instEndDate
				+ ", instWebsite=" + instWebsite + ", instEmail=" + instEmail + ", instCnum=" + instCnum + ", instMnum="
				+ instMnum + ", isntRegDate=" + isntRegDate + ", instLogo=" + instLogo + ", instPanNum=" + instPanNum
				+ ", instGstNum=" + instGstNum + ", instStatus=" + instStatus + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", adrId="
				+ adrId + ", admId=" + admId + ", adrType=" + adrType + ", adrLine1=" + adrLine1 + ", adrLine2="
				+ adrLine2 + ", adrCountry=" + adrCountry + ", adrState=" + adrState + ", adrDistrict=" + adrDistrict
				+ ", adrTaluka=" + adrTaluka + ", adrCity=" + adrCity + ", adrPincode=" + adrPincode + ", adrStatus="
				+ adrStatus + ", adrOrder=" + adrOrder + ", isPrimary=" + isPrimary + ", amdId=" + amdId + ", amdFname="
				+ amdFname + ", amdLname=" + amdLname + ", amdDob=" + amdDob + ", amdMnum=" + amdMnum + ", amdEmail="
				+ amdEmail + ", amdUsername=" + amdUsername + ", amdPassword=" + amdPassword + ", amdPpic=" + amdPpic
				+ ", etId=" + etId + ", etName=" + etName + ", etSubject=" + etSubject + ", etBody=" + etBody
				+ ", etType=" + etType + ", etAction=" + etAction + "]";
	}


	public Long getInstId() {
		return instId;
	}


	public void setInstId(Long instId) {
		this.instId = instId;
	}


	public String getInstName() {
		return instName;
	}


	public void setInstName(String instName) {
		this.instName = instName;
	}


	public Date getInstEndDate() {
		return instEndDate;
	}


	public void setInstEndDate(Date instEndDate) {
		this.instEndDate = instEndDate;
	}


	public String getInstWebsite() {
		return instWebsite;
	}


	public void setInstWebsite(String instWebsite) {
		this.instWebsite = instWebsite;
	}


	public String getInstEmail() {
		return instEmail;
	}


	public void setInstEmail(String instEmail) {
		this.instEmail = instEmail;
	}


	public String getInstCnum() {
		return instCnum;
	}


	public void setInstCnum(String instCnum) {
		this.instCnum = instCnum;
	}


	public String getInstMnum() {
		return instMnum;
	}


	public void setInstMnum(String instMnum) {
		this.instMnum = instMnum;
	}


	public Date getIsntRegDate() {
		return isntRegDate;
	}


	public void setIsntRegDate(Date isntRegDate) {
		this.isntRegDate = isntRegDate;
	}


	public String getInstLogo() {
		return instLogo;
	}


	public void setInstLogo(String instLogo) {
		this.instLogo = instLogo;
	}


	public String getInstPanNum() {
		return instPanNum;
	}


	public void setInstPanNum(String instPanNum) {
		this.instPanNum = instPanNum;
	}


	public String getInstGstNum() {
		return instGstNum;
	}


	public void setInstGstNum(String instGstNum) {
		this.instGstNum = instGstNum;
	}


	public String getInstStatus() {
		return instStatus;
	}


	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
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


	public Long getAdrId() {
		return adrId;
	}


	public void setAdrId(Long adrId) {
		this.adrId = adrId;
	}


	public Long getAdmId() {
		return admId;
	}


	public void setAdmId(Long admId) {
		this.admId = admId;
	}


	public String getAdrType() {
		return adrType;
	}


	public void setAdrType(String adrType) {
		this.adrType = adrType;
	}


	public String getAdrLine1() {
		return adrLine1;
	}


	public void setAdrLine1(String adrLine1) {
		this.adrLine1 = adrLine1;
	}


	public String getAdrLine2() {
		return adrLine2;
	}


	public void setAdrLine2(String adrLine2) {
		this.adrLine2 = adrLine2;
	}


	public Long getAdrCountry() {
		return adrCountry;
	}


	public void setAdrCountry(Long adrCountry) {
		this.adrCountry = adrCountry;
	}


	public Long getAdrState() {
		return adrState;
	}


	public void setAdrState(Long adrState) {
		this.adrState = adrState;
	}


	public Long getAdrDistrict() {
		return adrDistrict;
	}


	public void setAdrDistrict(Long adrDistrict) {
		this.adrDistrict = adrDistrict;
	}


	public Long getAdrTaluka() {
		return adrTaluka;
	}


	public void setAdrTaluka(Long adrTaluka) {
		this.adrTaluka = adrTaluka;
	}


	public Long getAdrCity() {
		return adrCity;
	}


	public void setAdrCity(Long adrCity) {
		this.adrCity = adrCity;
	}


	public String getAdrPincode() {
		return adrPincode;
	}


	public void setAdrPincode(String adrPincode) {
		this.adrPincode = adrPincode;
	}


	public String getAdrStatus() {
		return adrStatus;
	}


	public void setAdrStatus(String adrStatus) {
		this.adrStatus = adrStatus;
	}


	public Long getAdrOrder() {
		return adrOrder;
	}


	public void setAdrOrder(Long adrOrder) {
		this.adrOrder = adrOrder;
	}


	public int getIsPrimary() {
		return isPrimary;
	}


	public void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}


	public Long getAmdId() {
		return amdId;
	}


	public void setAmdId(Long amdId) {
		this.amdId = amdId;
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


	public Date getAmdDob() {
		return amdDob;
	}


	public void setAmdDob(Date amdDob) {
		this.amdDob = amdDob;
	}


	public String getAmdMnum() {
		return amdMnum;
	}


	public void setAmdMnum(String amdMnum) {
		this.amdMnum = amdMnum;
	}


	public String getAmdEmail() {
		return amdEmail;
	}


	public void setAmdEmail(String amdEmail) {
		this.amdEmail = amdEmail;
	}


	public String getAmdUsername() {
		return amdUsername;
	}


	public void setAmdUsername(String amdUsername) {
		this.amdUsername = amdUsername;
	}


	public String getAmdPassword() {
		return amdPassword;
	}


	public void setAmdPassword(String amdPassword) {
		this.amdPassword = amdPassword;
	}


	public String getAmdPpic() {
		return amdPpic;
	}


	public void setAmdPpic(String amdPpic) {
		this.amdPpic = amdPpic;
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


		
}
