package org.ulearn.packageservice.entity;

public class GlobalResponse {
	private String reason;
	private String message;

	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "GlobalResponse [reason=" + reason + ", message=" + message + "]";
	}

	public GlobalResponse(String reason, String message) {
		super();
		this.reason = reason;
		this.message = message;
	}

	public GlobalResponse(String string, int i, String string2) {
		// TODO Auto-generated constructor stub
	}

}
