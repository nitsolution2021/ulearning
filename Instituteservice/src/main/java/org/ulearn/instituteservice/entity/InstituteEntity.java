package org.ulearn.instituteservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_institutes")
public class InstituteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INST_ID")
	private Long instId;
	@Column(name = "INST_NAME")
	private String instName;
	@Column(name = "INST_EDATE")
	private Date instEndDate;
	@Column(name = "INST_WEBSITE")
	private String instWebsite;
	@Column(name = "INST_EMAIL")
	private String instEmail;
	@Column(name = "INST_CNUM")
	private String instCnum;
	@Column(name = "INST_MNUM")
	private String instMnum;
	@Column(name = "ISNT_RDATE")
	private Date isntRegDate;
	@Column(name = "INST_LOGO")
	private String instLogo;
	@Column(name = "INST_PAN_NUM")
	private String instPanNum;
	@Column(name = "INST_GST_NUM")
	private String instGstNum;
	@Column(name = "INST_STATUS")
	private String instStatus;
	@Column(name = "IS_ACTIVE")
	private int isActive;
	@Column(name = "IS_DELETED")
	private int isDeleted;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	public InstituteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InstituteEntity(Long instId, String instName, Date instEndDate, String instWebsite, String instEmail,
			String instCnum, String instMnum, Date isntRegDate, String instLogo, String instPanNum, String instGstNum,
			String instStatus, int isActive, int isDeleted, Date createdOn, Date updatedOn) {
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
	}

	@Override
	public String toString() {
		return "InstituteEntrity [instId=" + instId + ", instName=" + instName + ", instEndDate=" + instEndDate
				+ ", instWebsite=" + instWebsite + ", instEmail=" + instEmail + ", instCnum=" + instCnum + ", instMnum="
				+ instMnum + ", isntRegDate=" + isntRegDate + ", instLogo=" + instLogo + ", instPanNum=" + instPanNum
				+ ", instGstNum=" + instGstNum + ", instStatus=" + instStatus + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
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

}
