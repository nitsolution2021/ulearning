package org.ulearn.login.loginservice.entity;

public class GlobalEntity {
	private int uid;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private String ppic;
	private String accessToken;
	private String oldPass;
	private String newPass;

	public GlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalEntity(int uid, String firstName, String lastName, String email, String userName, String password,
			String ppic, String accessToken, String oldPass, String newPass) {
		super();
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.ppic = ppic;
		this.accessToken = accessToken;
		this.oldPass = oldPass;
		this.newPass = newPass;
	}

	@Override
	public String toString() {
		return "GlobalEntity [uid=" + uid + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", userName=" + userName + ", password=" + password + ", ppic=" + ppic + ", accessToken="
				+ accessToken + ", oldPass=" + oldPass + ", newPass=" + newPass + "]";
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPpic() {
		return ppic;
	}

	public void setPpic(String ppic) {
		this.ppic = ppic;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

}
