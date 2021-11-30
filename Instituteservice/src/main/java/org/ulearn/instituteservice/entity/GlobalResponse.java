package org.ulearn.instituteservice.entity;

public class GlobalResponse {
	private String reason;
	private Integer status;
	private String message;
	
	
	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public GlobalResponse(String reason, Integer status, String message) {
		super();
		this.reason = reason;
		this.status = status;
		this.message = message;
	}


	@Override
	public String toString() {
		return "GlobalResponse [reason=" + reason + ", status=" + status + ", message=" + message + "]";
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}




	
	
	

}
