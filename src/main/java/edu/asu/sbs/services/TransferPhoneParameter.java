package edu.asu.sbs.services;

import java.math.BigInteger;

public class TransferPhoneParameter {
	public BigInteger phone;
	public String userName;
	public double amount;
	public BigInteger getPhone() {
		return phone;
	}
	public void setPhone(BigInteger phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	

}
