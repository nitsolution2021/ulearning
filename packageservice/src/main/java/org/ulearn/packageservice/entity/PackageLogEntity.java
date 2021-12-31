package org.ulearn.packageservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.util.Date;

@Entity
@Table(name = "tbl_inst_package_log")
public class PackageLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PL_ID")
	private Long plId;
	@Column(name = "PK_ID")
	private Long pkId;
	@Column(name = "PL_ACTION")
	private String plAction;
	@Column(name = "PL_ADATE")
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date plAdate;
	@Column(name = "PL_COMMENT")
	private String plComment;
	@Column(name = "PL_STATUS")
	private String plStatus;
	@Column(name = "CREATED_ON")
	private Date plCreat;
	@Column(name = "UPDATED_ON")
	private Date plUpdate;
	public PackageLogEntity(Long plId, Long pkId, String plAction, Date plAdate, String plComment, String plStatus,
			Date plCreat, Date plUpdate) {
		super();
		this.plId = plId;
		this.pkId = pkId;
		this.plAction = plAction;
		this.plAdate = plAdate;
		this.plComment = plComment;
		this.plStatus = plStatus;
		this.plCreat = plCreat;
		this.plUpdate = plUpdate;
	}
	public PackageLogEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getPlId() {
		return plId;
	}
	public void setPlId(Long plId) {
		this.plId = plId;
	}
	public long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	public String getPlAction() {
		return plAction;
	}
	public void setPlAction(String plAction) {
		this.plAction = plAction;
	}
	public Date getPlAdate() {
		return plAdate;
	}
	public void setPlAdate(Date plAdate) {
		this.plAdate = plAdate;
	}
	public String getPlComment() {
		return plComment;
	}
	public void setPlComment(String plComment) {
		this.plComment = plComment;
	}
	public String getPlStatus() {
		return plStatus;
	}
	public void setPlStatus(String plStatus) {
		this.plStatus = plStatus;
	}
	public Date getPlCreat() {
		return plCreat;
	}
	public void setPlCreat(Date plCreat) {
		this.plCreat = plCreat;
	}
	public Date getPlUpdate() {
		return plUpdate;
	}
	public void setPlUpdate(Date plUpdate) {
		this.plUpdate = plUpdate;
	}
	@Override
	public String toString() {
		return "PackageLogEntity [plId=" + plId + ", pkId=" + pkId + ", plAction=" + plAction + ", plAdate=" + plAdate
				+ ", plComment=" + plComment + ", plStatus=" + plStatus + ", plCreat=" + plCreat + ", plUpdate="
				+ plUpdate + "]";
	}
	
}