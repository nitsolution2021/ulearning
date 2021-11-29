package org.ulearn.packageservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tbl_inst_admin")
public class InstituteAdminEntity implements Serializable{

	@Id
	@Column(name="ADM_ID")
	private Long admId;
	@Column(name = "INST_ID")
	private Long instId;
	@Column(name = "ADM_FNAME")
	private String amdFname;
	@Column(name = "ADM_LNAME")
	private String amdLname;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Column(name = "ADM_DOB")
	private Date amdDob;
	@Column(name = "ADM_MNUM")
	private String amdMnum;
	@Column(name = "ADM_EMAIL")
	private String amdEmail;
	@Column(name = "ADM_USERNAME")
	private String amdUsername;
	@Column(name = "ADM_PASSWORD")
	private String amdPassword;
	@Column(name = "ADM_PPIC")
	private String amdPpic;
	@Column(name = "CREATED_ON")
	private Date createdOn;	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	public InstituteAdminEntity(Long admId, Long instId, String amdFname, String amdLname, Date amdDob, String amdMnum,
			String amdEmail, String amdUsername, String amdPassword, String amdPpic, Date createdOn, Date updatedOn) {
		super();
		this.admId = admId;
		this.instId = instId;
		this.amdFname = amdFname;
		this.amdLname = amdLname;
		this.amdDob = amdDob;
		this.amdMnum = amdMnum;
		this.amdEmail = amdEmail;
		this.amdUsername = amdUsername;
		this.amdPassword = amdPassword;
		this.amdPpic = amdPpic;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	public InstituteAdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getAdmId() {
		return admId;
	}
	public void setAdmId(Long admId) {
		this.admId = admId;
	}
	public Long getInstId() {
		return instId;
	}
	public void setInstId(Long instId) {
		this.instId = instId;
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
		return "AdminEntity [admId=" + admId + ", instId=" + instId + ", amdFname=" + amdFname + ", amdLname="
				+ amdLname + ", amdDob=" + amdDob + ", amdMnum=" + amdMnum + ", amdEmail=" + amdEmail + ", amdUsername="
				+ amdUsername + ", amdPassword=" + amdPassword + ", amdPpic=" + amdPpic + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}
	
}
