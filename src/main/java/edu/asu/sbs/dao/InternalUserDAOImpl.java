package edu.asu.sbs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.ModifiedUser;
import edu.asu.sbs.model.Role;
import edu.asu.sbs.model.Users;
import edu.asu.sbs.services.BCryptHashService;

@Repository
public class InternalUserDAOImpl implements InternalUserDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	BCryptHashService bCryptHashService;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public InternalUser findById(Integer Id) {
		// TODO Auto-generated method stub
		InternalUser internalUser = (InternalUser) getCurrentSession().get(InternalUser.class, Id);
		return internalUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternalUser> findAll() {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from InternalUser").list();
	}

	@Override
	public void add(InternalUser internalUser) {
		// TODO Auto-generated method stub
		String encodedPwd = bCryptHashService.getBCryptHash(internalUser.getPasswordHash());
		getCurrentSession().save(internalUser);
		// add to users table
		System.out.println("Adding to users table");
		Users user = new Users(internalUser.getUserName(),encodedPwd,1);
		user.setEmail(internalUser.getEmailId());
		userDAO.add(user);
		// add to user_roles table with appropriate role
		System.out.println("Adding to roles table");
		String userRole = internalUser.getEmployeeType() == 0 ? "ROLE_REGULAR" 
				: internalUser.getEmployeeType() == 1 ? "ROLE_MANAGER": "ROLE_ADMIN";
		Role role = new Role(internalUser.getUserName(),userRole);
		roleDAO.add(role);
	}

	@Override
	public void update(InternalUser user) {
		// TODO Auto-generated method stub
		System.out.println("$$Updating the user with modified details$$");
		InternalUser internalUser = findById(user.getEmployeeId());
		System.out.println("$$Updating the user with modified details$$" + internalUser.getEmailId());
		internalUser.setAddress(user.getAddress());
		internalUser.setEmailId(user.getEmailId());
		internalUser.setPhoneNumber(user.getPhoneNumber());
		getCurrentSession().update(internalUser);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		InternalUser internalUser = findById(id);
		System.out.println("Internal user "+ internalUser.getEmailId());
		if( internalUser != null) getCurrentSession().delete(internalUser);		
	}

	@Override
	public void update(ModifiedUser user) {
		// TODO Auto-generated method stub
		System.out.println("Updating the values with modified user");
		InternalUser internalUser = findById(user.getUserId());
		internalUser.setFirstName(user.getFirstName());
		internalUser.setLastName(user.getLastName());
		internalUser.setPhoneNumber(user.getPhoneNumber());
		getCurrentSession().update(internalUser);
	}

	@Override
	public InternalUser findByUserName(String currentUserName){
		Criteria criteria = getCurrentSession().createCriteria(InternalUser.class);
		InternalUser internalUser = (InternalUser) criteria.add(Restrictions.eq("userName", currentUserName)).uniqueResult();
		return internalUser;
	}
	

}