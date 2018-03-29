package edu.asu.sbs.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.Query;

import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.Users;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void add(Users user) {
		// TODO Auto-generated method stub
		getCurrentSession().save(user);
	}

	@Override
	public Users findRoleByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users findByUserEmail(String emailId) {
		Criteria criteria = getCurrentSession().createCriteria(Users.class);
		Users user = (Users) criteria.add(Restrictions.eq("emailId", emailId)).uniqueResult();
		return user;
	}

	@Override
	public void updatePassword(Users user) {
		getCurrentSession().update(user);

	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		Users user = findById(id);
		getCurrentSession().delete(user);
	}
	
	@Override
	public Users findById(Integer Id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Users user = (Users) session.get(Users.class, Id);
		return user;
	}

}
