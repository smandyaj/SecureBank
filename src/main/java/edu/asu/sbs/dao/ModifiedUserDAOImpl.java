package edu.asu.sbs.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.ModifiedUser;

@Repository
public class ModifiedUserDAOImpl implements ModifiedUserDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public ModifiedUser findById(Integer Id) {
		// TODO Auto-generated method stub
		return (ModifiedUser) getCurrentSession().get(ModifiedUser.class, Id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModifiedUser> findAll(int userType) {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from ModifiedUser where status = ? and userType=?")
				.setParameter(0, 0)
				.setParameter(1, userType)
				.list();
	}

	@Override
	public void add(ModifiedUser user) {
		// TODO Auto-generated method stub
		System.out.println("Saving user");
		getCurrentSession().save(user);
		
	}

	@Override
	public void update(ModifiedUser user) {
		// TODO Auto-generated method stub
		System.out.println("updating the status to approved");
		ModifiedUser modUser = findById(user.getModId());
		modUser.setStatus(user.getStatus());
		modUser.setStatus_quo(user.getStatus_quo());
		getCurrentSession().update(modUser);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}