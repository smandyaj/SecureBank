package edu.asu.sbs.dao;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.Transaction;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	AccountDAO as;
	@Autowired
	ExternalUserDAO ts;
	@Autowired
	TransactionDAO t1;

	private static double critical = 500;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> listAll(int internalUserType) {
		// TODO Auto-generated method stub
		// 0 - pending, 1 - approved, 2- declined
		if (internalUserType == 0) {
			System.out.println("Tier - 1 employee");
			return getCurrentSession().createQuery("from Transaction where auth = ? and status=? and isCritical=?")
					.setParameter(0, 0)
					.setParameter(1, 0)
					.setParameter(2, 0)
					.list();
		}
		System.out.println("Tier - 2 employee");
		return getCurrentSession().createQuery("from Transaction where auth=? and status=?")
				.setParameter(0, 0)
				.setParameter(1, 0)
				.list();
	}

	@Override
	public List<Transaction> listForCustomer(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction get(int transactionId) {
		// TODO Auto-generated method stub
		return (Transaction) getCurrentSession().get(Transaction.class, transactionId);
	}

	@Override
	public void add(Transaction t) {
		// TODO Auto-generated method stub
		getCurrentSession().save(t);
	}

	@Override
	public void update(Transaction t) {
		// TODO Auto-generated method stub
		getCurrentSession().update(t);
	}

	@Override
	public void debit(int accNumber, String userName, double amount, int acc_type) {
		// TODO Auto-generated method stub
		int isCritical = 0;
		if (amount > critical) {
			isCritical = 1;
		}
		ExternalUser e = ts.findByUserName(userName);

		// Add userName to Account Table
		int customer_id = e.getCustomerId();

		// Account number and amount belongs to the person who is receiving the money
		List<Account> merchantAccounts = as.findByCustomerID(customer_id);

		Account merchantAcc = null;
		for (Account ai : merchantAccounts) {
			if (ai.getAccountType() == acc_type) {
				merchantAcc = ai;

				break;
			}
		}
		if (merchantAcc == null) {
			return;
		}
		int merchantAccount = merchantAcc.getAccountId();

		double merchantBalance = as.getBalance(merchantAccount);

		if (merchantBalance >= amount) {
			Transaction t;

			Account acc = as.findByAccountNumber(accNumber);

			Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t = new Transaction(0, currentTimestamp, customer_id, acc.getCustomerId(), amount, 0, 1, "pending",
					merchantAccount, accNumber, 0, isCritical);

			t1.add(t); // Need to add from SAM
		}
	}

	@Override
	public void debit_final(int transaction_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction t = t1.get(transaction_id); // the function is added by SAM
		int payer_id = t.getSenderAccNumber();
		int receiver_id = t.getReceiverAccNumber();

		double transaction_amount = t.getTransactionAmount();
		Account a = as.findByAccountNumber(payer_id);

		double balance = a.getAccountBalance();

		balance -= transaction_amount;

		Account a1 = as.findByAccountNumber(receiver_id);
		double rbalance = a1.getAccountBalance();
		rbalance += transaction_amount;

		a.setAccountBalance(balance);
		a1.setAccountBalance(rbalance);

		as.updateAccount(a1);
		as.updateAccount(a);
	}

	@Override
	public void credit(int accNumber, String userName, double amount) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		ExternalUser e = ts.findByUserName(userName);
		// Add userName to Account Table
		int customer_id = e.getCustomerId();
		List<Account> accounts = as.findByCustomerID(customer_id);

		Account merchantAcc = null;
		for (Account ai : accounts) {
			if (ai.getAccountType() == 0) {
				merchantAcc = ai;
				break;
			}
		}
		if (merchantAcc == null) {
			return;
		}
		int merchantAccount = merchantAcc.getAccountId();
		double merchantBalance = as.getBalance(merchantAccount);
		// Assumption that transaction has been approved by Merchant - we are getting
		// the above parameters using JSP
		// accNumber & amount - user paying to merchant
		double payyeBalance = as.getBalance(accNumber);
		if (payyeBalance >= amount) {
			merchantBalance += amount;
			payyeBalance = payyeBalance - amount;
			Account a1 = as.findByAccountNumber(accNumber);
			merchantAcc.setAccountBalance(merchantBalance);
			a1.setAccountBalance(payyeBalance);
			as.updateAccount(a1);
			as.updateAccount(merchantAcc);
		}
	}

	// Create model for modified user
	// Transfer through email and message
	// Transfer through email
	@Override
	public void transfer_email(String email_id, String userName, double amount) {
		// ADD ACCOUNT_NUMBER accNumber FIELD IN EXTERNAL USER TABLE
		ExternalUser e1 = ts.findByEmail(email_id);
		int customerId = e1.getCustomerId(); // Define Modifieduser model with getter and setter

		System.out.println(customerId + "is customer id");

		List<Account> accounts = as.findByCustomerID(customerId);

		Account a = null;
		for (Account ai : accounts) {
			if (ai.getAccountType() == 0) {
				a = ai;
				break;
			}
		}
		if (a == null) {
			return;
		}
		int accountNo = a.getAccountId();
		debit(accountNo, userName, amount, 0);
	}

	// Transfer through message
	@Override
	public void transfer_message(BigInteger phone, String userName, double amount) {
		ExternalUser e1 = ts.findByPhone(phone);
		int customerId = e1.getCustomerId(); // Define Modifieduser model with getter and setter
		List<Account> accounts = as.findByCustomerID(customerId);

		Account a = null;
		for (Account ai : accounts) {
			if (ai.getAccountType() == 1) {
				a = ai;
				break;
			}
		}
		if (a == null) {
			return;
		}
		int accountNo = a.getAccountId();
		debit(accountNo, userName, amount, 1);
	}

	@Override
	public int addTransaction(Transaction t) {
		// TODO Auto-generated method stub
		return (int) getCurrentSession().save(t);
	}

	@Override
	public void deleteTransaction(Transaction t) {
		// TODO Auto-generated method stub
		getCurrentSession().delete(t);
	}

	@Override
	public Transaction getTransactionById(int transactionId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction t = (Transaction) session.get(Transaction.class, transactionId);
		return t;
	}
	
	@Override
	public List<Transaction> listForPendingTransactions(int customerId) {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from Transaction where auth=? and status=? "
				+ "and payerId=? and receiverId!=? and transactionType=?")
				.setParameter(0, 0)
				.setParameter(1, 0).setParameter(2, customerId).setParameter(3, customerId).setParameter(4, 4).list();
	}

	@Override
	public List<Transaction> listForAccount(int accountId) {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from Transaction where auth=? and status=? "
				+ "and (senderAccNumber=? or receiverAccNumber=?)")
				.setParameter(0, 1)
				.setParameter(1, 1).setParameter(2, accountId).setParameter(3, accountId).list();
	}

	@Override
	public Transaction getById(int Id) {
		// TODO Auto-generated method stub
		return (Transaction) this.getCurrentSession().get(Transaction.class, Id);
	}

	@Override
	public List<Transaction> getByPayerId(int Id) {
		// TODO Auto-generated method stub
		return this.getCurrentSession().createQuery("from Transaction t where t.senderAccNumber = ?").setParameter(0, Id).list();
	}

	@Override
	public List<Transaction> getByReceiverId(int Id) {
		// TODO Auto-generated method stub
		return this.getCurrentSession().createQuery("from Transaction t where t.receiverAccNumber = ?").setParameter(0, Id).list();
	}

	@Override
	public List<Transaction> getByPayerOrReceiverId(int Id) {
		// TODO Auto-generated method stub
		return this.getCurrentSession().createQuery("from Transaction t where t.senderAccNumber = ? or t.receiverAccNumber= ?")
				.setParameter(0, Id)
				.setParameter(1, Id)
				.list();
	}

	@Override
	public List<Transaction> getByPayerOrReceiverIdOrderedByTime(int Id) {
		// TODO Auto-generated method stub
		return this.getCurrentSession().createQuery("from Transaction t where t.senderAccNumber = ? or t.receiverAccNumber = ? order by t.transactionCreateTime")
				.setParameter(0, Id)
				.setParameter(1, Id)
				.list();
	}
	
	
	
	

}