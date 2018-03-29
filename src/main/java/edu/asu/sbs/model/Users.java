package edu.asu.sbs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private int enabled;
	
	@Column(name = "email_id")
	private String emailId;

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

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Users(String userName, String password, int enabled) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}
	
	public String getEmail() {
		return emailId;
	}

	public void setEmail(String emailId) {
		this.emailId = emailId;
	}
	
	
	
}
