package org.ulearn.hsncodeservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "tbl_hsn_sac")
public class HsnCodeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HS_ID")
	private Long hsId;

	@NotEmpty(message = "HSN Code is Required")
	@Column(name = "HS_CODE")
	private String hsCode;

	@NotEmpty(message = "Description is Required")
	@Column(name = "HS_DESC")
	private String hsDesc;
	
	@Positive(message = "SGST Percentage is Required")
	@Column(name = "HS_SGST")
	private Double hsSgst;
	
	@Positive(message = "CGST Percentage is Required")
	@Column(name = "HS_CGST")
	private Double hsCgst;

	@Positive(message = "IGST Percentage is Required")
	@Column(name = "HS_IGST")
	private Double hsIgst;

	@Column(name = "HS_ORDER")
	private String hsOrder;

	@Positive(message = "Service Type is Required")
	@Column(name = "SERV_ID")
	private Long servId;

	@Column(name = "IS_DELETED")
	private int isDeleted;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	public HsnCodeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HsnCodeEntity(Long hsId, String hsCode, String hsDesc, Double hsSgst, Double hsCgst, Double hsIgst,
			String hsOrder, Long servId, int isDeleted, int isActive, Date createdOn) {
		super();
		this.hsId = hsId;
		this.hsCode = hsCode;
		this.hsDesc = hsDesc;
		this.hsSgst = hsSgst;
		this.hsCgst = hsCgst;
		this.hsIgst = hsIgst;
		this.hsOrder = hsOrder;
		this.servId = servId;
		this.isDeleted = isDeleted;
		this.isActive = isActive;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "HsnCodeEntity [hsId=" + hsId + ", hsCode=" + hsCode + ", hsDesc=" + hsDesc + ", hsSgst=" + hsSgst
				+ ", hsCgst=" + hsCgst + ", hsIgst=" + hsIgst + ", hsOrder=" + hsOrder + ", servId=" + servId
				+ ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdOn=" + createdOn + "]";
	}

	public Long getHsId() {
		return hsId;
	}

	public void setHsId(Long hsId) {
		this.hsId = hsId;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getHsDesc() {
		return hsDesc;
	}

	public void setHsDesc(String hsDesc) {
		this.hsDesc = hsDesc;
	}

	public Double getHsSgst() {
		return hsSgst;
	}

	public void setHsSgst(Double hsSgst) {
		this.hsSgst = hsSgst;
	}

	public Double getHsCgst() {
		return hsCgst;
	}

	public void setHsCgst(Double hsCgst) {
		this.hsCgst = hsCgst;
	}

	public Double getHsIgst() {
		return hsIgst;
	}

	public void setHsIgst(Double hsIgst) {
		this.hsIgst = hsIgst;
	}

	public String getHsOrder() {
		return hsOrder;
	}

	public void setHsOrder(String hsOrder) {
		this.hsOrder = hsOrder;
	}

	public Long getServId() {
		return servId;
	}

	public void setServId(Long servId) {
		this.servId = servId;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
