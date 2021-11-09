package org.ulearn.instituteservice.entity;

public class GlobalResponse {
	private String resion;
	private String message;

	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResponse(String resion, String message) {
		super();
		this.resion = resion;
		this.message = message;
	}

	@Override
	public String toString() {
		return "GlobalResponse [resion=" + resion + ", message=" + message + "]";
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

}
