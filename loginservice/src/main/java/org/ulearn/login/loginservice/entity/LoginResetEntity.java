package org.ulearn.login.loginservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_login_reset")
public class LoginResetEntity {
	
	@Column(name = "ID") private Long id;
	@Column(name = "UID") private Long uId;
	@Column(name = "PRTOKEN") private String prToken;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "STATUS") private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getPrToken() {
		return prToken;
	}
	public void setPrToken(String prToken) {
		this.prToken = prToken;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LoginResetEntity [id=" + id + ", uId=" + uId + ", prToken=" + prToken + ", createdOn=" + createdOn
				+ ", status=" + status + "]";
	}
	public LoginResetEntity(Long id, Long uId, String prToken, Date createdOn, String status) {
		super();
		this.id = id;
		this.uId = uId;
		this.prToken = prToken;
		this.createdOn = createdOn;
		this.status = status;
	}
	public LoginResetEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
