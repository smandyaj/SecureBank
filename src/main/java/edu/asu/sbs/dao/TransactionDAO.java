package edu.asu.sbs.dao;

import java.math.BigInteger;
import java.util.List;

import edu.asu.sbs.model.Transaction;

public interface TransactionDAO {
	
	public List<Transaction> listAll(int internalUserType);
	public List<Transaction> listForCustomer(int customerId);
	public Transaction get(int trasactionId);
	public void add( Transaction t);
	public void update(Transaction t);
	public int addTransaction(Transaction t);
	void debit(int accNumber, String userName, double amount, int acc_type);
	void debit_final(int transaction_id);
	void credit(int accNumber, String userName, double amount);
	void transfer_email(String email_id, String userName, double amount);
	void transfer_message(BigInteger phone, String userName, double amount);
	public void deleteTransaction(Transaction t);
	public Transaction getTransactionById(int transactionId);
	

	public List<Transaction> listForAccount(int accountId);
	public List<Transaction> listForPendingTransactions(int customerId);
	
	
public Transaction getById(int Id);
	
	public List<Transaction> getByPayerId(int Id);
	
	public List<Transaction> getByReceiverId(int Id);
	
	public List<Transaction> getByPayerOrReceiverId(int Id);
	
	public List<Transaction> getByPayerOrReceiverIdOrderedByTime(int Id);


}