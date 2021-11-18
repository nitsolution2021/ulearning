package org.ulearn.licenseservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="tbl_inst_license_log")
public class LicenseLogEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="LL_ID")
	private Long llId;
	
	@Column(name="LC_ID")
	private int lcIdFk;
	
	@Column(name="LL_ACTION")
	private String llAction;
	
	@Column(name="LL_VALIDITY_TYPE")
	private String llValidityType;
	
	@Column(name="LL_VALIDITY_NUM")
	private int llValidityNum;
	
	@JsonFormat(pattern = "yyyy/mm/dd")
	@Column(name="LL_SDATE")
	private Date llSdate;
	
	@JsonFormat(pattern = "yyyy/mm/dd")
	@Column(name="LL_EDATE")
	private Date llEdate;
	
	@Column(name="LL_COMMENT")
	private String llComment;
	
	@Column(name="LL_STATUS")
	private String llStatus;
	
	@Column(name="CREATED_ON")
	private Date createdOn;
	
	@Column(name="UPDATED_ON")
	private Date updatedOn;
	
	
	
	
	
	public LicenseLogEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LicenseLogEntity(Long llId, int lcIdFk, String llAction, String llValidityType, int llValidityNum,
			Date llSdate, Date llEdate, String llComment, String llStatus, Date createdOn, Date updatedOn) {
		super();
		this.llId = llId;
		this.lcIdFk = lcIdFk;
		this.llAction = llAction;
		this.llValidityType = llValidityType;
		this.llValidityNum = llValidityNum;
		this.llSdate = llSdate;
		this.llEdate = llEdate;
		this.llComment = llComment;
		this.llStatus = llStatus;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	
	
	@Override
	public String toString() {
		return "LicenseLogEntity [llId=" + llId + ", lcIdFk=" + lcIdFk + ", llAction=" + llAction + ", llValidityType="
				+ llValidityType + ", llValidityNum=" + llValidityNum + ", llSdate=" + llSdate + ", llEdate=" + llEdate
				+ ", llComment=" + llComment + ", llStatus=" + llStatus + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", getLlId()=" + getLlId() + ", getLcIdFk()=" + getLcIdFk() + ", getLlAction()="
				+ getLlAction() + ", getLlValidityType()=" + getLlValidityType() + ", getLlValidityNum()="
				+ getLlValidityNum() + ", getLlSdate()=" + getLlSdate() + ", getLlEdate()=" + getLlEdate()
				+ ", getLlComment()=" + getLlComment() + ", getLlStatus()=" + getLlStatus() + ", getCreatedOn()="
				+ getCreatedOn() + ", getUpdatedOn()=" + getUpdatedOn() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public Long getLlId() {
		return llId;
	}
	public void setLlId(Long llId) {
		this.llId = llId;
	}
	public int getLcIdFk() {
		return lcIdFk;
	}
	public void setLcIdFk(int lcIdFk) {
		this.lcIdFk = lcIdFk;
	}
	public String getLlAction() {
		return llAction;
	}
	public void setLlAction(String llAction) {
		this.llAction = llAction;
	}
	public String getLlValidityType() {
		return llValidityType;
	}
	public void setLlValidityType(String llValidityType) {
		this.llValidityType = llValidityType;
	}
	public int getLlValidityNum() {
		return llValidityNum;
	}
	public void setLlValidityNum(int llValidityNum) {
		this.llValidityNum = llValidityNum;
	}
	public Date getLlSdate() {
		return llSdate;
	}
	public void setLlSdate(Date llSdate) {
		this.llSdate = llSdate;
	}
	public Date getLlEdate() {
		return llEdate;
	}
	public void setLlEdate(Date llEdate) {
		this.llEdate = llEdate;
	}
	public String getLlComment() {
		return llComment;
	}
	public void setLlComment(String llComment) {
		this.llComment = llComment;
	}
	public String getLlStatus() {
		return llStatus;
	}
	public void setLlStatus(String llStatus) {
		this.llStatus = llStatus;
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
