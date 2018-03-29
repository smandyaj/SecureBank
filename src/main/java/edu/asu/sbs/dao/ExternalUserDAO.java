package edu.asu.sbs.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.ModifiedUser;

public interface ExternalUserDAO {

	ExternalUser findById(Integer Id);

	List<ExternalUser> findAll();

	void update(ModifiedUser user);

	void delete(Integer id);

	void debit(int accNumber, String userName, double amount, int acc_type);

	void debit_final(int transaction_id);

	void credit(int accNumber, String userName, double amount);

	void transfer_email(String email_id, String userName, double amount);

	void transfer_message(BigInteger phone, String userName, double amount);

	ExternalUser findByUserName(String currentUserName);

	ExternalUser findByPhone(BigInteger phone);

	ExternalUser findByEmail(String email_id);

	void add(ModifiedUser user);

	public ExternalUser findByCustomerId(Integer customerId);
	
	/** IMPORTANT **/
	int add(ExternalUser user);
	
	void update(ExternalUser user);

	ExternalUser findByPhoneNumber(BigInteger phoneNumber);

	ExternalUser findByEmailId(String emailId);

}
