package edu.asu.sbs.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.sbs.dao.AccountDAO;
import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.Transaction;
@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountDAO accountDAO;
	
	@Override
	public List<Account> getAccountByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		return accountDAO.findByCustomerID(customerId);
	}

	@Override
	public Account getAccountByNumber(int accountNumber) {
		// TODO Auto-generated method stub
		return accountDAO.findByAccountNumber(accountNumber);
	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		accountDAO.updateAccount(account);
		
	}

	@Override
	public Double getBalance(int accountNumber) {
		// TODO Auto-generated method stub
		return accountDAO.getBalance(accountNumber);
	}

	@Override
	public void transferFunds(Transaction sender, Transaction receiver, BigDecimal amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferFunds(TransactionService transactionService, AccountService accountService,
			Transaction senderTransaction, Transaction receiverTransaction, double amount) {
		Account senderAccount = accountService.getAccountByNumber(senderTransaction.getSenderAccNumber());
		Account receiverAccount = accountService.getAccountByNumber(senderTransaction.getReceiverAccNumber());
		System.out.println("senderAccount: " + senderAccount);
		System.out.println("receiverAccount: " + receiverAccount);
		
		// update account balances
		senderAccount.setAccountBalance(senderAccount.getAccountBalance() - amount);
		receiverAccount.setAccountBalance(receiverAccount.getAccountBalance() + amount);
		
		System.out.println("senderAccount after updating balance: " + senderAccount);
		System.out.println("receiverAccount after updating balance: " + receiverAccount);
		
		// create transactions
		transactionService.addTransaction(senderTransaction);
		transactionService.addTransaction(receiverTransaction);
		
	}

	@Override
	public Account findByAccountNumber(int i) {
		// TODO Auto-generated method stub
		return accountDAO.findByAccountNumber(i);
	}

	@Override
	public Account getAccountByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		return this.accountDAO.findByAccountId(accountId);
	}

	@Override
	public List<Account> getAccountByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		return this.accountDAO.findByCustomerId(customerId);
	}
	
    /*
	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		this.accountDAO.update(account);
		
	}*/

	@Override
	public double getBalance(Integer accountId) {
		// TODO Auto-generated method stub
		Account account = this.accountDAO.findByAccountId(accountId);
		return account.getAccountBalance();
	}

	@Override
	public List<Account> getAccountByAccountType(Integer customerId, int type) {
		// TODO Auto-generated method stub
		return this.accountDAO.findByAccountType(customerId, type);
	}

	/** IMPORTANT **/
	@Override
	public void add(Account account) {
		// TODO Auto-generated method stub
		System.out.println("Adding account1");
		accountDAO.add(account);
	}

}
