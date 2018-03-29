package edu.asu.sbs.services;

import java.util.List;

import edu.asu.sbs.model.ModifiedUser;


public interface ModifiedUserService {
	
	ModifiedUser findUserById(Integer Id);
	
	List<ModifiedUser> findAllUsers(int userType);
	
	void addUser(ModifiedUser user);
	
	void updateUser(ModifiedUser user);
	
	void deleteUser(Integer id);
}
