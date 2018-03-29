package edu.asu.sbs.services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.sbs.dao.TransactionDAO;
import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.Transaction;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionDAO transactionDAO;
	
	@Autowired
	AccountService accountService;
	
	@Override
	public int addTransaction(Transaction t) {
		// TODO Auto-generated method stub
		return transactionDAO.addTransaction(t);
	}

	@Override
	public void updateTransaction(Transaction t) {
		// TODO Auto-generated method stub
		transactionDAO.update(t);
	}

	@Override
	public List<Transaction> getAllTransaction(Integer type) {
		// TODO Auto-generated method stub
		return transactionDAO.listAll(type);
	}

	@Override
	public List<Transaction> getTransactions(Integer customerId) {
		// TODO Auto-generated method stub
		return transactionDAO.listForCustomer(customerId);
	}

	@Override
	public Transaction get(int transactionId) {
		// TODO Auto-generated method stub
		return transactionDAO.get(transactionId);
	}

	@Override
	public Transaction getTransactionById(int transaction_id) {
		return transactionDAO.getTransactionById(transaction_id);
	}

	@Override
	public void debit(int accNumber, String userName, double amount, int acc_type) {
		// TODO Auto-generated method stub
		transactionDAO.debit(accNumber, userName, amount, acc_type);
	}

	@Override
	public void debit_final(int transaction_id) {
		// TODO Auto-generated method stub
		transactionDAO.debit_final(transaction_id);
	}

	@Override
	public void credit(int accNumber, String userName, double amount) {
		// TODO Auto-generated method stub
		transactionDAO.credit(accNumber, userName, amount);
	}

	@Override
	public void transfer_email(String email_id, String userName, double amount) {
		// TODO Auto-generated method stub
		transactionDAO.transfer_email(email_id, userName, amount);

	}

	@Override
	public void transfer_message(BigInteger phone, String userName, double amount) {
		// TODO Auto-generated method stub
		transactionDAO.transfer_message(phone, userName, amount);
	}

	@Override
	public boolean isTransferCritical(double amount) {
		// TODO Auto-generated method stub
		if (amount >= 500) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteTransaction(Transaction t) {
		// TODO Auto-generated method stub

		transactionDAO.deleteTransaction(t);

	}

	@Override
	public void add(Transaction t) {
		// TODO Auto-generated method stub

		transactionDAO.add(t);

	}

	@Override
	public boolean approveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
				System.out.println("inside approve transaction");

				if (transaction.getStatus() != 0) {
					return false;
				}

				System.out.println("Transaction is pending and its parameters are "+transaction.getSenderAccNumber()+" "+transaction.getReceiverAccNumber());

				Account senderAcc = accountService.findByAccountNumber(transaction
						.getSenderAccNumber());
				System.out.println("sender account        "+senderAcc.getAccountId());
				Account recieverAcc = accountService.findByAccountNumber(transaction
						.getReceiverAccNumber());
				
				System.out.println("receiver account        "+recieverAcc.getAccountId());

				System.out.println("Accounts retreived");

				double amount = transaction.getTransactionAmount();

				// Means credit debit type of transactions
				// 0 means debit
				// 1 means credit
				if (senderAcc.getAccountId() == recieverAcc.getAccountId()) {
					if (transaction.getTransactionType() == 1) {
						senderAcc.setAccountBalance(senderAcc.getAccountBalance() + amount);
						transaction.setStatus(1);
						transaction.setStatus_quo("approved");
						transactionDAO.update(transaction);
					} else {
						senderAcc.setAccountBalance(senderAcc.getAccountBalance() - amount);
						transaction.setStatus(1);
						transaction.setStatus_quo("approved");
						transactionDAO.update(transaction);
					}
					return true;
				}else {
					System.out.println("updating the account balances for sender/reciever");
					senderAcc.setAccountBalance(senderAcc.getAccountBalance() - amount);
					recieverAcc.setAccountBalance(recieverAcc.getAccountBalance() + amount);
					accountService.updateAccount(senderAcc);
					accountService.updateAccount(recieverAcc);
					transaction.setStatus(1);
					transaction.setStatus_quo("approved");
					transactionDAO.update(transaction);
					System.out.println("updating is done");
					return true;

				}
	}
	
	


	@Override
	public List<Transaction> getPendingTransactions(Integer customerId) {
		// TODO Auto-generated method stub
		System.out.println("customer id   "+customerId);
		return transactionDAO.listForPendingTransactions(customerId);
	}

	@Override
	public List<Transaction> getTransactionsForAccount(Integer accountId) {
		// TODO Auto-generated method stub
		return transactionDAO.listForAccount(accountId);
	}

	@Override
	public List<Transaction> getTransactionsByPayerId(int Id) {
		// TODO Auto-generated method stub
		return this.transactionDAO.getByPayerId(Id);
	}

	@Override
	public List<Transaction> getTransactionsByReceiverId(int Id) {
		// TODO Auto-generated method stub
		return this.transactionDAO.getByReceiverId(Id);
	}

	@Override
	public List<Transaction> getTransactionsByPayerOrReceiverId(int Id) {
		// TODO Auto-generated method stub
		return this.transactionDAO.getByPayerOrReceiverId(Id);
	}

	@Override
	public List<Transaction> getOrderedTransactionsByPayerOrReceiverId(int Id) {
		// TODO Auto-generated method stub
		return this.transactionDAO.getByPayerOrReceiverIdOrderedByTime(Id);
	}
	
	

}
