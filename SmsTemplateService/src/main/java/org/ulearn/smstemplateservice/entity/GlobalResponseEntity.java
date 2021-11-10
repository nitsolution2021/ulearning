package org.ulearn.smstemplateservice.entity;

public class GlobalResponseEntity {
	private String reason;
	private String message;

	public GlobalResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResponseEntity(String reason, String message) {
		super();
		this.reason = reason;
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
