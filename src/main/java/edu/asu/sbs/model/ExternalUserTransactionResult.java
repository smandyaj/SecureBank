package edu.asu.sbs.model;

public class ExternalUserTransactionResult {
public String userName;
public int accNumber;
public int acc_type;
public double amount;
public String getUserName() {
return userName;
}
public void setUserName(String userName) {
this.userName = userName;
}
public int getAccNumber() {
return accNumber;
}
public void setAccNumber(int accNumber) {
this.accNumber = accNumber;
}
public int getAcc_type() {
return acc_type;
}
public void setAcc_type(int acc_type) {
this.acc_type = acc_type;
}
public double getAmount() {
return amount;
}
public void setAmount(double amount) {
this.amount = amount;
}
}
