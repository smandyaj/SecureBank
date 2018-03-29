package edu.asu.sbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.sbs.dao.ModifiedUserDAO;
import edu.asu.sbs.model.ModifiedUser;

@Service
@Transactional
public class ModifiedUserServiceImpl implements ModifiedUserService{
	
	@Autowired
	ModifiedUserDAO modifiedUserDAO;
	
	@Override
	public ModifiedUser findUserById(Integer Id) {
		// TODO Auto-generated method stub
		return modifiedUserDAO.findById(Id);
	}

	@Override
	public List<ModifiedUser> findAllUsers(int userType) {
		// TODO Auto-generated method stub
		return modifiedUserDAO.findAll(userType);
	}

	@Override
	public void addUser(ModifiedUser user) {
		// TODO Auto-generated method stub
		modifiedUserDAO.add(user);
		
	}

	@Override
	public void updateUser(ModifiedUser user) {
		// TODO Auto-generated method stub
		modifiedUserDAO.update(user);
		
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

}