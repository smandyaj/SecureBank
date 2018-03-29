package edu.asu.sbs.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.sbs.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findByCustomerID(int customerId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts = new ArrayList<Account>();
		accounts = session.createQuery("from Account where customerId=?").setParameter(0, customerId).list();
		return accounts;
	}

	@Override
	public Account findByAccountNumber(int accNumber) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Account account = new Account();
		account = (Account) session.get(Account.class,accNumber);
		return account;
	}
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	

	@Override
	public Double getBalance(int accNumber) {
		// TODO Auto-generated method stub
		
		Account acc = findByAccountNumber(accNumber);
		
		return acc.getAccountBalance();
	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(account);
		
	}

	@Override
	public Account findByAccountId(Integer Id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Account account = (Account)session.get(Account.class, Id);
		return account;
	}

	@Override
	public List<Account> findByCustomerId(Integer Id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts = new ArrayList<Account>();
		accounts = session.createQuery("from Account a where a.customerId=?").setParameter(0, Id).list();
		return accounts;
	}

	@Override
	public void add(Account account) {
		// TODO Auto-generated method stub
		System.out.println("Adding account");
		getCurrentSession().save(account);
	}

	@Override
	public void update(Account account) {
		// TODO Auto-generated method stub
		Account oldAccount = this.findByAccountId(account.getAccountId());
		oldAccount.setAccountBalance(account.getAccountBalance());
		oldAccount.setAccountDue(account.getAccountDue());
		oldAccount.setAccountLimit(account.getAccountLimit());
		oldAccount.setAccountType(account.getAccountType());
		oldAccount.setCustomerId(account.getCustomerId());
		this.getCurrentSession().update(oldAccount);
		
	}

	@Override
	public void delete(Integer accountId) {
		// TODO Auto-generated method stub
		Account account = this.findByAccountId(accountId);
		if (account != null)
			this.getCurrentSession().delete(account);
	}


	@Override
	public List<Account> findByAccountType(Integer customerId, Integer type) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts = new ArrayList<Account>();
		accounts = session.createQuery("from Account a where a.customerId=? and a.accountType=?")
				.setParameter(0, customerId)
				.setParameter(1,type)
				.list();
		return accounts;
		
	}

}
