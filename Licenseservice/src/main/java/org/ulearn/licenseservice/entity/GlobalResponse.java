package org.ulearn.licenseservice.entity;

public class GlobalResponse {
	private String resion;
	private String message;
	private int statuss;
	
	
	@Override
	public String toString() {
		return "GlobalResponse [resion=" + resion + ", message=" + message + ", statuss=" + statuss + "]";
	}
	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GlobalResponse(String resion, String message, int statuss) {
		super();
		this.resion = resion;
		this.message = message;
		this.statuss = statuss;
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
		return statuss;
	}
	public void setStatuss(int statuss) {
		this.statuss = statuss;
	}
	

	
}
