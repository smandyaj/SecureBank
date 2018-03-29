package edu.asu.sbs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private int accountId;
	
	@Column(name="customer_id")
	private int customerId;
	
	public int getCustomerId() {
		return customerId;
	}

	public Account(int customerId, int accountType, double accountBalance, String accountName, Double accountLimit,
			Integer accountDue) {
		super();
		this.customerId = customerId;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
		this.accountName = accountName;
		this.accountLimit = accountLimit;
		this.accountDue = accountDue;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Column(name = "account_type")
	private int accountType;
	
	@Column(name = "account_balance")
	private double accountBalance;
	
	@Column(name = "account_name")
	private String accountName;
	

	
	@Column(name = "account_limit")
	private Double accountLimit;
	
	@Column(name = "account_due")
	private Integer accountDue;
	

	public Double getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(Double accountLimit) {
		this.accountLimit = accountLimit;
	}

	public Integer getAccountDue() {
		return accountDue;
	}
	
	public void setAccountDue(Integer accountDue) {
		this.accountDue = accountDue;
	}
	

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountId(){
		return accountId;
	}

	public void setAccountId(int accountId){
		this.accountId=accountId;
	}

	public int getAccountType(){
		return accountType;
	}

	public void setAccountType(int accountType){
		this.accountType=accountType;
	}

	public double getAccountBalance(){
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance){
		this.accountBalance=accountBalance;
	}
}
