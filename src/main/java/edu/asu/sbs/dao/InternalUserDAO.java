package edu.asu.sbs.dao;

import java.util.List;

import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.ModifiedUser;

public interface InternalUserDAO {

	InternalUser findById(Integer Id);
	
	List<InternalUser> findAll();
	
	void add(InternalUser user);
	
	void update(InternalUser user);
	
	void delete(Integer id);
	
	void update(ModifiedUser user);
	
	InternalUser findByUserName(String userName);
}
