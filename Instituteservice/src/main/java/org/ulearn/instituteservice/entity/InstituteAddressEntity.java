package org.ulearn.instituteservice.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "tbl_inst_addr")
public class InstituteAddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ADR_ID")
	private Long adrId;
	
	@Column(name = "INST_ID")
	private Long instId;
	
	@Column(name = "ADR_TYPE")
	private String adrType;
	
	@Column(name = "ADR_LINE1")
	private String adrLine1;
	
	@Column(name = "ADR_LINE2")
	private String adrLine2;
	
	@Column(name = "ADR_COUNTRY")
	private Long adrCountry;
	
	@Column(name = "ADR_STATE")
	private Long adrState;
	
	@Column(name = "ADR_DISTRICT")
	private Long adrDistrict;
	
	@Column(name = "ADR_TALUKA")
	private Long adrTaluka;
	
	@Column(name = "ADR_CITY")
	private Long adrCity;
	
	@Column(name = "ADR_PINCODE")
	private String adrPincode;
	
	@Column(name = "ADR_STATUS")
	private String adrStatus;
	
	@Column(name = "ADR_ORDER")
	private Long adrOrder;
	
	@Column(name = "IS_PRIMARY")
	private int isPrimary;
	
	@Column(name = "IS_ACTIVE")
	private int isActive;
	
	@Column(name = "IS_DELETED")
	private int isDeleted;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	public InstituteAddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InstituteAddressEntity(Long adrId, Long instId, String adrType, String adrLine1, String adrLine2,
			Long adrCountry, Long adrState, Long adrDistrict, Long adrTaluka, Long adrCity, String adrPincode,
			String adrStatus, Long adrOrder, int isPrimary, int isActive, int isDeleted, Date createdOn,
			Date updatedOn) {
		super();
		this.adrId = adrId;
		this.instId = instId;
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
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "InstituteAddressEntity [adrId=" + adrId + ", instId=" + instId + ", adrType=" + adrType + ", adrLine1="
				+ adrLine1 + ", adrLine2=" + adrLine2 + ", adrCountry=" + adrCountry + ", adrState=" + adrState
				+ ", adrDistrict=" + adrDistrict + ", adrTaluka=" + adrTaluka + ", adrCity=" + adrCity + ", adrPincode="
				+ adrPincode + ", adrStatus=" + adrStatus + ", adrOrder=" + adrOrder + ", isPrimary=" + isPrimary
				+ ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}

	public Long getAdrId() {
		return adrId;
	}

	public void setAdrId(Long adrId) {
		this.adrId = adrId;
	}

	public Long getInstId() {
		return instId;
	}

	public void setInstId(Long instId) {
		this.instId = instId;
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
	
//	@ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "inst_id"), name = "inst_id",insertable = true,updatable = true)
//	private InstituteEntity instituteEntity;

	
}
