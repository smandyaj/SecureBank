package edu.asu.sbs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class CreditCard{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_balance")
	private double balance;
	private double fee;
	private double credit_limit;

	public double getBalance() {
		
		return balance ;
	}
	
	public void setBalance(int balance){
		this.balance=balance;
	}

		
   public double getLatePaymentFee() {
	   
	   return fee;
   }
	
   public void setLatePaymentFee(double fee) {
	   
	    this.fee=fee;
   }

   public  double getCreditLimit(){
	   
	      return credit_limit;
   }


   public  void setCreditLimit(double credit_limit){
	   
	     this.credit_limit=credit_limit;
}

	
}