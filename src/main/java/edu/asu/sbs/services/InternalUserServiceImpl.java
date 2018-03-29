package edu.asu.sbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.sbs.dao.InternalUserDAO;
import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.ModifiedUser;

@Service
@Transactional
public class InternalUserServiceImpl implements InternalUserService{

	@Autowired
	InternalUserDAO internalUserDAO;
	
	@Override
	public InternalUser findUserById(Integer Id) {
		// TODO Auto-generated method stub
		return internalUserDAO.findById(Id);
	}

	@Override
	public List<InternalUser> findAllUsers() {
		// TODO Auto-generated method stub
		return internalUserDAO.findAll();
	}

	@Override
	public void addUser(InternalUser user) {
		// TODO Auto-generated method stub
		internalUserDAO.add(user);
	}

	@Override
	public void updateUser(InternalUser user) {
		// TODO Auto-generated method stub
		internalUserDAO.update(user);
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		internalUserDAO.delete(id);
	}

	@Override
	public void updateUser(ModifiedUser user) {
		// TODO Auto-generated method stub
		internalUserDAO.update(user);
		
	}

	@Override
	public InternalUser findByUserName() {
		// TODO Auto-generated method stub
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		    System.out.println("Current logged in user" + currentUserName);
		}
        
        return internalUserDAO.findByUserName(currentUserName);
	}


}
