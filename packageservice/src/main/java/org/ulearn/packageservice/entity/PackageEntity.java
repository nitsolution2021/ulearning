package org.ulearn.packageservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "tbl_inst_package")
public class PackageEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK_ID") private Long pkId;
	@Column(name = "INST_ID")@Positive(message = "The Institute Id required") private Long instId;
	@Column(name = "PK_TYPE") @NotEmpty(message = "Package type is required") String pkType;
	@Column(name = "PK_NAME") @NotEmpty(message = "Package name is required") String pkName;
	@Column(name = "PK_FNAME") @NotEmpty(message = "Package Fname is required") String pkFname;
	@Column(name = "PK_NUSERS")@Positive(message = "Package nusers is required") private Long pkNusers;
	
	@Column(name = "PK_VALIDITY_TYPE")@NotEmpty(message = "Package validity type is required") private String pkValidityType;
	@Column(name = "PK_VALIDITY_NUM") @Positive(message = "Package validity no required") Long pkValidityNum;
	@Column(name = "PK_CDATE")private Date pkCdate;
	//@Column(name = "HS_ID") private Long hsId;
	@Column(name = "PK_COMMENT") @NotEmpty(message = "Write some comment on that package") String pkComment;
	@Column(name = "PARENT_ID")@Positive(message = "Parent is required") private Long parentId;
	@Column(name = "IS_ACTIVE") private Long isActive;
	@Column(name = "IS_DELETED") private Long isDeleted;
	@Column(name = "PK_STATUS")@NotEmpty(message = "Package status is requred") private String pkStatus;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_ON") private Date updatedOn;
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	public Long getInstId() {
		return instId;
	}
	public void setInstId(Long instId) {
		this.instId = instId;
	}
	public String getPkType() {
		return pkType;
	}
	public void setPkType(String pkType) {
		this.pkType = pkType;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public String getPkFname() {
		return pkFname;
	}
	public void setPkFname(String pkFname) {
		this.pkFname = pkFname;
	}
	public Long getPkNusers() {
		return pkNusers;
	}
	public void setPkNusers(Long pkNusers) {
		this.pkNusers = pkNusers;
	}
	public String getPkValidityType() {
		return pkValidityType;
	}
	public void setPkValidityType(String pkValidityType) {
		this.pkValidityType = pkValidityType;
	}
	public Long getPkValidityNum() {
		return pkValidityNum;
	}
	public void setPkValidityNum(Long pkValidityNum) {
		this.pkValidityNum = pkValidityNum;
	}
	public Date getPkCdate() {
		return pkCdate;
	}
	public void setPkCdate(Date pkCdate) {
		this.pkCdate = pkCdate;
	}
	/*public Long getHsId() {
		return hsId;
	}
	public void setHsId(Long hsId) {
		this.hsId = hsId;
	}*/
	public String getPkComment() {
		return pkComment;
	}
	public void setPkComment(String pkComment) {
		this.pkComment = pkComment;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	public String getPkStatus() {
		return pkStatus;
	}
	public void setPkStatus(String pkStatus) {
		this.pkStatus = pkStatus;
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
		return "PackageEntity [pkId=" + pkId + ", instId=" + instId + ", pkType=" + pkType + ", pkName=" + pkName
				+ ", pkFname=" + pkFname + ", pkNusers=" + pkNusers + ", pkValidityType=" + pkValidityType
				+ ", pkValidityNum=" + pkValidityNum + ", pkCdate=" + pkCdate + ", pkComment="
				+ pkComment + ", parentId=" + parentId + ", isActive=" + isActive + ", isDeleted=" + isDeleted
				+ ", pkStatus=" + pkStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
	public PackageEntity(Long pkId, Long instId, String pkType, String pkName, String pkFname, Long pkNusers,
			String pkValidityType, Long pkValidityNum, Date pkCdate, Long hsId, String pkComment, Long parentId,
			Long isActive, Long isDeleted, String pkStatus, Date createdOn, Date updatedOn) {
		super();
		this.pkId = pkId;
		this.instId = instId;
		this.pkType = pkType;
		this.pkName = pkName;
		this.pkFname = pkFname;
		this.pkNusers = pkNusers;
		this.pkValidityType = pkValidityType;
		this.pkValidityNum = pkValidityNum;
		this.pkCdate = pkCdate;
		//this.hsId = hsId;
		this.pkComment = pkComment;
		this.parentId = parentId;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.pkStatus = pkStatus;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	public PackageEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
