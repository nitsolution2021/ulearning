package org.ulearn.licenseservice.entity;

public class GlobalResponse {
	private String resion;
	private String message;
	private int status;
	
	
	@Override
	public String toString() {
		return "GlobalResponse [resion=" + resion + ", message=" + message + ", statuss=" + status + "]";
	}
	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GlobalResponse(String resion, String message, int status) {
		super();
		this.resion = resion;
		this.message = message;
		this.status = status;
	}
	public String getResion() {
		return resion;
	}
	public void setResion(String resion) {
		this.resion = resion;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatuss() {
		return status;
	}
	public void setStatuss(int status) {
		this.status = status;
	}
	

	
}
