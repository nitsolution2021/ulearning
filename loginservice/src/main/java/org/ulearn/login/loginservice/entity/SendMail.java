package org.ulearn.login.loginservice.entity;

public class SendMail {
	
	String senderMailId;
	String subject;
	String body;
	public String getSenderMailId() {
		return senderMailId;
	}
	public void setSenderMailId(String senderMailId) {
		this.senderMailId = senderMailId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "SendMail [senderMailId=" + senderMailId + ", subject=" + subject + ", body=" + body + "]";
	}
	public SendMail(String senderMailId, String subject, String body) {
		super();
		this.senderMailId = senderMailId;
		this.subject = subject;
		this.body = body;
	}
	public SendMail() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
