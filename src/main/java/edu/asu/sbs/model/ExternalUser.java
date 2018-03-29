package edu.asu.sbs.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "external_users")
public class ExternalUser
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "customer_address")
	private String customerAddress;
	
	@Column(name = "phone")
	private BigInteger phone;
	
	@Column(name = "customer_type")
	private int customerType;
	
	@Column(name = "user_name")
	private String userName;

	@Column(name = "password_hash")
	private String passwordHash;
	
	/** IMPORTANT **/
	@Transient
	String accountType;
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public int getCustomerId(){
		return customerId;
	}

	public void setCustomerId(int customerId){
		this.customerId=customerId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setEmailId(String emailId){
		this.emailId=emailId;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName=firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName=lastName;
	}

	public String getCustomerAddress(){
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress){
		this.customerAddress=customerAddress;
	}


	public BigInteger getPhone(){
		return phone;
	}

	public void setPhone(BigInteger phone){
		this.phone=phone;
	}


	public int getCustomerType(){
		return customerType;
	}

	public void setCustomerType(int customerType){
		this.customerType=customerType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
