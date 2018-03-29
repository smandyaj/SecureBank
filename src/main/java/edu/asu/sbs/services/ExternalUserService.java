package edu.asu.sbs.services;

import java.math.BigInteger;
import java.util.List;

import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.ModifiedUser;

public interface ExternalUserService {

	ExternalUser findUserById(Integer Id);

	List<ExternalUser> findAll();

	void add(ModifiedUser user);

	void update(ModifiedUser user);

	void delete(Integer id);

	void debit(int accNumber, String userName, double amount, int acc_type);

	void debit_final(int transaction_id);

	void credit(int accNumber, String userName, double amount);

	void transfer_email(String email_id, String userName, double amount);

	void transfer_message(BigInteger phone, String userName, double amount);

	ExternalUser findByUserName();

	ExternalUser findByPhone(BigInteger phone);

	ExternalUser findByEmail(String email_id);

	ExternalUser findByCustomerId(Integer customerId);

	List<ExternalUser> findAllUsers();
	
	void addUser(ExternalUser user);
	
	void updateUser(ExternalUser user);
	
	void updateUser(ModifiedUser user);
	
	void deleteUser(Integer id);
	
	
	

	
	ExternalUser findByPhoneNumber(BigInteger phoneNumber);
	
	ExternalUser findByEmailId(String emailId);
	
	
	
	
}
