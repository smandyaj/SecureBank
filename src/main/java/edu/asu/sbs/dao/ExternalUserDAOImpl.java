package edu.asu.sbs.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.ModifiedUser;
import edu.asu.sbs.model.Role;
import edu.asu.sbs.model.Transaction;
import edu.asu.sbs.model.Users;
import edu.asu.sbs.services.AccountService;
import edu.asu.sbs.services.BCryptHashService;
import edu.asu.sbs.services.TransactionService;

@Repository

public class ExternalUserDAOImpl implements ExternalUserDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	AccountDAO as;
	
	@Autowired
	TransactionDAO ts;
	
	@Autowired
	BCryptHashService bCryptHashService;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ExternalUser findById(Integer Id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		ExternalUser externalUser = (ExternalUser) session.get(ExternalUser.class, Id);
		return externalUser;
	}

	@Override
	public ExternalUser findByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		ExternalUser externalUser = (ExternalUser) session.get(ExternalUser.class, customerId);
		return externalUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalUser> findAll() {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from ExternalUser").list();
	}

	@Override
	public void add(ModifiedUser user) {
		// TODO Auto-generated method stub
		getCurrentSession().save(user);
	}

	@Override
	public void update(ModifiedUser user) {
		// TODO Auto-generated method stub
		ExternalUser externalUser = findById(user.getUserId());
		externalUser.setCustomerAddress(user.getAddress());
		externalUser.setEmailId(user.getEmailId());
		externalUser.setPhone(user.getPhoneNumber());
		getCurrentSession().update(externalUser);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		ExternalUser externalUser = findById(id);
		System.out.println("External user " + externalUser.getEmailId());
		if (externalUser != null)
			getCurrentSession().delete(externalUser);
			
	}

	@Override
	public void debit(int accNumber, String userName, double amount, int acc_type) {
		ts.debit(accNumber, userName, amount, acc_type);
	}

	@Override
	public void debit_final(int transaction_id) {
		ts.debit_final(transaction_id);
	}

	@Override
	public void credit(int accNumber, String userName, double amount) {
		// TODO Auto-generated method stub
		ts.credit(accNumber, userName, amount);
	}

	// Create model for modified user
	// Transfer through email and message
	// Transfer through email
	@Override
	public void transfer_email(String email_id, String userName, double amount) {
		// ADD ACCOUNT_NUMBER accNumber FIELD IN EXTERNAL USER TABLE
		ts.transfer_email(email_id, userName, amount);
	}

	// Transfer through message
	@Override
	public void transfer_message(BigInteger phone, String userName, double amount) {
		ts.transfer_message(phone, userName, amount);
	}

	@Override
	public ExternalUser findByUserName(String currentUserName) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = getCurrentSession().createCriteria(ExternalUser.class);
		ExternalUser externalUser = (ExternalUser) criteria.add(Restrictions.eq("userName", currentUserName))
				.uniqueResult();
		return externalUser;
	}

	@Override

	public ExternalUser findByEmail(String email_Id) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalUser.class);
		ExternalUser externalUser = (ExternalUser) criteria.add(Restrictions.eq("emailId", email_Id)).uniqueResult();
		return externalUser;
	}

	@Override
	public ExternalUser findByPhone(BigInteger phone) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalUser.class);
		ExternalUser externalUser = (ExternalUser) criteria.add(Restrictions.eq("phone", phone)).uniqueResult();
		return externalUser;

	}
	
	/** IMPORTANT **/
	@Override
	public int add(ExternalUser externalUser) {
		// TODO Auto-generated method stub
		String encodedPwd = bCryptHashService.getBCryptHash(externalUser.getPasswordHash());
		externalUser.setPasswordHash(encodedPwd);
		// add to users table
		System.out.println("Adding to users table");
		Users user = new Users(externalUser.getUserName(),encodedPwd,1);
		user.setEmail(externalUser.getEmailId());
		userDAO.add(user);
		// add to user_roles table with appropriate role
		System.out.println("Adding to roles table");
		String userRole = externalUser.getCustomerType() == 0 ? "ROLE_CUSTOMER" : "ROLE_MERCHANT";
		Role role = new Role(externalUser.getUserName(),userRole);
		roleDAO.add(role);
		getCurrentSession().save(user);
		return (int)getCurrentSession().save(externalUser);
		
	}

	@Override
	public void update(ExternalUser user) {
		// TODO Auto-generated method stub
		ExternalUser externalUser = findById(user.getCustomerId());
		externalUser.setCustomerAddress(user.getCustomerAddress());
		externalUser.setEmailId(user.getEmailId());
		externalUser.setPhone(user.getPhone());
		getCurrentSession().update(externalUser);
	}
	
	
	@Override
	public ExternalUser findByPhoneNumber(BigInteger phoneNumber) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalUser.class);
		ExternalUser externalUser = (ExternalUser) criteria.add(Restrictions.eq("phone", phoneNumber)).uniqueResult();
		return externalUser;
	}

	@Override
	public ExternalUser findByEmailId(String emailId) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalUser.class);
		ExternalUser externalUser = (ExternalUser) criteria.add(Restrictions.eq("emailId", emailId)).uniqueResult();
		return externalUser;
	}

}
