package org.ulearn.packageservice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name="tbl_institutes")
public class InstituteEntity implements Serializable{

	@Id
	@Column(name = "INST_ID")
	private Long instId;
	@Column(name = "INST_NAME")
	private String instName;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
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
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
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
	@OneToOne
	@JoinColumn(name = "INST_ID")
	private InstituteAdminEntity instituteAdmin;
	@OneToMany
	@JoinColumn(name = "INST_ID", referencedColumnName = "INST_ID")
	private List<InstituteAddressEntity> instituteAddress = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "INST_ID")
	private List<PackageEntity> packageEntity = new ArrayList<>();
	@OneToOne
	@JoinColumn(name = "INST_ID")
	private LicenseEntity licenseEntity;
	public InstituteEntity(Long instId, String instName, Date instEndDate, String instWebsite, String instEmail,
			String instCnum, String instMnum, Date isntRegDate, String instLogo, String instPanNum, String instGstNum,
			String instStatus, int isActive, int isDeleted, Date createdOn, Date updatedOn,
			InstituteAdminEntity instituteAdmin, List<InstituteAddressEntity> instituteAddress,
			List<PackageEntity> packageEntity, LicenseEntity licenseEntity) {
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
		this.instituteAdmin = instituteAdmin;
		this.instituteAddress = instituteAddress;
		this.packageEntity = packageEntity;
		this.licenseEntity = licenseEntity;
	}
	public InstituteEntity() {
		super();
		// TODO Auto-generated constructor stub
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
	public InstituteAdminEntity getInstituteAdmin() {
		return instituteAdmin;
	}
	public void setInstituteAdmin(InstituteAdminEntity instituteAdmin) {
		this.instituteAdmin = instituteAdmin;
	}
	public List<InstituteAddressEntity> getInstituteAddress() {
		return instituteAddress;
	}
	public void setInstituteAddress(List<InstituteAddressEntity> instituteAddress) {
		this.instituteAddress = instituteAddress;
	}
	public List<PackageEntity> getPackageEntity() {
		return packageEntity;
	}
	public void setPackageEntity(List<PackageEntity> packageEntity) {
		this.packageEntity = packageEntity;
	}
	public LicenseEntity getLicenseEntity() {
		return licenseEntity;
	}
	public void setLicenseEntity(LicenseEntity licenseEntity) {
		this.licenseEntity = licenseEntity;
	}
	@Override
	public String toString() {
		return "InstituteEntity [instId=" + instId + ", instName=" + instName + ", instEndDate=" + instEndDate
				+ ", instWebsite=" + instWebsite + ", instEmail=" + instEmail + ", instCnum=" + instCnum + ", instMnum="
				+ instMnum + ", isntRegDate=" + isntRegDate + ", instLogo=" + instLogo + ", instPanNum=" + instPanNum
				+ ", instGstNum=" + instGstNum + ", instStatus=" + instStatus + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", instituteAdmin=" + instituteAdmin + ", instituteAddress=" + instituteAddress + ", packageEntity="
				+ packageEntity + ", licenseEntity=" + licenseEntity + "]";
	}
	
}