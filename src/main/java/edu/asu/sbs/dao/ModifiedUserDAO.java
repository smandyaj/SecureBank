package edu.asu.sbs.dao;

import java.util.List;

import edu.asu.sbs.model.ModifiedUser;


public interface ModifiedUserDAO {

	ModifiedUser findById(Integer Id);
	
	List<ModifiedUser> findAll(int userType);
	
	void add(ModifiedUser user);
	
	void update(ModifiedUser user);
	
	void delete(Integer id);
}