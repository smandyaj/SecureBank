package edu.asu.sbs.services;

import java.math.BigInteger;
import java.util.List;

import edu.asu.sbs.model.Transaction;

public interface TransactionService {

public int addTransaction(Transaction t);
public void updateTransaction(Transaction t);
public List<Transaction> getAllTransaction(Integer type);
public List<Transaction> getTransactions(Integer customerId);
public Transaction get(int transactionId);
public void add(Transaction t);
Transaction getTransactionById(int transaction_id);
public boolean approveTransaction(Transaction transaction);
void debit(int accNumber, String userName, double amount, int acc_type);
void debit_final(int transaction_id);
void credit(int accNumber, String userName, double amount);
void transfer_email(String email_id, String userName, double amount);
void transfer_message(BigInteger phone, String userName, double amount);
public boolean isTransferCritical(double amount);
public void deleteTransaction(Transaction t);



List<Transaction> getTransactionsForAccount(Integer accountId);
List<Transaction> getPendingTransactions(Integer accountId);




public List<Transaction> getTransactionsByPayerId(int Id);

public List<Transaction> getTransactionsByReceiverId(int Id);

public List<Transaction> getTransactionsByPayerOrReceiverId(int Id);

public List<Transaction> getOrderedTransactionsByPayerOrReceiverId(int Id);







}
