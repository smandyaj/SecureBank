package edu.asu.sbs.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "modified_users")
public class ModifiedUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mod_id")
	private int modId;
	
	@Column(name = "id")
	private int userId;
	
	@Column(name = "user_name")
	private String userName;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getModId() {
		return modId;
	}

	public void setModId(int modId) {
		this.modId = modId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigInteger getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(BigInteger phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatus_quo() {
		return status_quo;
	}

	public void setStatus_quo(String status_quo) {
		this.status_quo = status_quo;
	}

	@Column(name = "user_type")
	private int userType;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "mail_id")
	private String emailId;
	
	@Column(name = "phone_number")
	private BigInteger phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "status_quo")
	private String status_quo;

	public ModifiedUser(int userId, String firstName, String lastName, String emailId, BigInteger phoneNumber,
			String address, int status, String status_quo, int userType, String userName) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.status = status;
		this.status_quo = status_quo;
		this.userType = userType;
		this.userName = userName;
	}
	
	
	public ModifiedUser() {
		
	}

	
	
}