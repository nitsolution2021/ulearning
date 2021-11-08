package org.ulearn.login.loginservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_login")
public class Login {

	@Id
	@Column(name = "UID")
	private int uId;
	@Column(name = "FNAME")
	private String firstName;
	@Column(name = "LNAME")
	private String lastName;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "USERNAME")
	private String userName;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "PPIC")
	private String pPic;
	@Column(name = "ATOKEN")
	private String accessToken;

	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Login(int uId, String firstName, String lastName, String email, String userName, String password,
			String pPic, String accessToken) {
		super();
		this.uId = uId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.pPic = pPic;
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "Login [uId=" + uId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", userName=" + userName + ", password=" + password + ", pPic=" + pPic + ", accessToken="
				+ accessToken + "]";
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
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

	public String getpPic() {
		return pPic;
	}

	public void setpPic(String pPic) {
		this.pPic = pPic;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
