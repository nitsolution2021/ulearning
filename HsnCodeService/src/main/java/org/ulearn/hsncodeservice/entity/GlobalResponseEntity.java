package org.ulearn.hsncodeservice.entity;

public class GlobalResponseEntity {
	private String reason;
	private String message;
	private int status;
	
	public GlobalResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResponseEntity(String reason, String message, int status) {
		super();
		this.reason = reason;
		this.message = message;
		this.status = status;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GlobalResponseEntity [reason=" + reason + ", message=" + message + ", status=" + status + "]";
	}
}
