package edu.asu.sbs.services;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.asu.sbs.dao.ExternalUserDAO;
import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.ModifiedUser;

@Service
@Transactional
public class ExternalUserServiceImpl implements ExternalUserService {
	@Autowired
	ExternalUserDAO externalUserDAO;
	
	/** IMPORTANT **/
	@Autowired
	AccountService accountService;
	
	@Override
	public ExternalUser findUserById(Integer Id) {
		// TODO Auto-generated method stub
		return externalUserDAO.findById(Id);
	}

	@Override
	public void add(ModifiedUser externalUser) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		externalUserDAO.add(externalUser);
		
	}

	@Override
	public List<ExternalUser> findAll() {
		// TODO Auto-generated method stub
		return externalUserDAO.findAll();
	}

	/*
	 * @Override public void add(ModifiedUser user) { // TODO Auto-generated method
	 * stub externalUserDAO.add(user); }
	 */

	@Override
	public void update(ModifiedUser user) {
		// TODO Auto-generated method stub
		externalUserDAO.update(user);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		externalUserDAO.delete(id);
	}

	@Override
	public ExternalUser findByUserName() {
		// TODO Auto-generated method stub
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
			System.out.println("Current logged in user" + currentUserName);
		}

		return externalUserDAO.findByUserName(currentUserName);
	}

	@Override
	public void debit(int accNumber, String userName, double amount, int acc_type) {

		externalUserDAO.debit(accNumber, userName, amount, acc_type);
	}

	@Override
	public void debit_final(int transaction_id) {
		externalUserDAO.debit_final(transaction_id);
	}

	@Override
	public void credit(int accNumber, String userName, double amount) {
		externalUserDAO.credit(accNumber, userName, amount);
	}

	@Override
	public void transfer_email(String email_id, String userName, double amount) {
		externalUserDAO.transfer_email(email_id, userName, amount);
	}

	@Override
	public void transfer_message(BigInteger phone, String userName, double amount) {
		externalUserDAO.transfer_message(phone, userName, amount);
	}

	@Override
	public ExternalUser findByPhone(BigInteger phone) {
		// TODO Auto-generated method stub

		return externalUserDAO.findByPhone(phone);
	}

	@Override
	public ExternalUser findByEmail(String email_id) {
		// TODO Auto-generated method stub
		return externalUserDAO.findByEmail(email_id);
	}

	@Override
	public ExternalUser findByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		return externalUserDAO.findByCustomerId(customerId);
	}

	@Override
	public List<ExternalUser> findAllUsers() {
		// TODO Auto-generated method stub
		return externalUserDAO.findAll();
	}

	@Override
	public void addUser(ExternalUser user) {
		// TODO Auto-generated method stub
		int userId = externalUserDAO.add(user);
		/** IMPORTANT **/
		String accountName = "Savings";
		if( user.getAccountType().equals("0")) {
			accountName = "Savings";
		}else {
			accountName = user.getAccountType().equals("1")?"Checking":"Credit";
		}
		Account account = new Account(userId, Integer.parseInt(user.getAccountType()), Double.valueOf("1000"), accountName,  Double.valueOf("1000"), 0);
		accountService.add(account);
	}

	@Override
	public void updateUser(ExternalUser user) {
		// TODO Auto-generated method stub
		externalUserDAO.update(user);
		
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(ModifiedUser user) {
		// TODO Auto-generated method stub
		externalUserDAO.update(user);
	}
	
	
	@Override
	public ExternalUser findByPhoneNumber(BigInteger phoneNumber) {
		// TODO Auto-generated method stub
		return externalUserDAO.findByPhoneNumber(phoneNumber);
	}

	@Override
	public ExternalUser findByEmailId(String emailId) {
		// TODO Auto-generated method stub
		return externalUserDAO.findByEmailId(emailId);
	}
	
	

}