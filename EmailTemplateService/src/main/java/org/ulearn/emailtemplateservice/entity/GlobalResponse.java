package org.ulearn.emailtemplateservice.entity;

public class GlobalResponse {
	private String reason;
	private String message;

	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResponse(String reason, String message) {
		super();
		this.reason = reason;
		this.message = message;
	}

	@Override
	public String toString() {
		return "GlobalResponse [reason=" + reason + ", message=" + message + "]";
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
