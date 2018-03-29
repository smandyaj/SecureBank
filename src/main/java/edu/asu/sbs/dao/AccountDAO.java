package edu.asu.sbs.dao;

import java.math.BigDecimal;
import java.util.List;

import edu.asu.sbs.model.Account;

public interface AccountDAO {
	List<Account> findByCustomerID(int customerId);
	Account findByAccountNumber(int accNumber);
	public Double getBalance(int accNumber);
	public void updateAccount(Account account);
	
	
Account findByAccountId(Integer Id);
	
	List<Account> findByCustomerId(Integer Id);
	
	List<Account> findByAccountType(Integer customerId, Integer type);
	
	void add(Account account);
	
	void update(Account account);
	
	void delete(Integer accountId);
	
	
	
	
}
