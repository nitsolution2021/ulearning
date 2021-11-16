package org.ulearn.smstemplateservice.entity;

public class GlobalResponseEntity {
	private String reason;
	private String message;
	private int statuss;

	public GlobalResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResponseEntity(String reason, String message, int statuss) {
		super();
		this.reason = reason;
		this.message = message;
		this.statuss = statuss;
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

	public int getStatuss() {
		return statuss;
	}

	public void setStatuss(int statuss) {
		this.statuss = statuss;
	}

}
