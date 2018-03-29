package edu.asu.sbs.services;

import java.math.BigDecimal;
import java.util.List;

import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.Transaction;

public interface AccountService {

	List<Account> getAccountByCustomerId(int customerId);
	Account getAccountByNumber(int string);
	public void updateAccount(Account account);
	public Double getBalance(int accountNumber);
	void transferFunds(Transaction sender, Transaction receiver,BigDecimal amount);
	void transferFunds(TransactionService transactionService, AccountService accountService,
			Transaction senderTransaction, Transaction receiverTransaction, double amount);
	Account findByAccountNumber(int i);
public Account getAccountByAccountId(Integer accountId);
	
	public List<Account> getAccountByCustomerId(Integer customerId);

	
	public double getBalance(Integer accountId);
	
	public List<Account> getAccountByAccountType(Integer customerId, int type);
	
	/** IMPORTANT **/
	public void add(Account account);
	
	
}
