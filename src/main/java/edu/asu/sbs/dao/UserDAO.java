package edu.asu.sbs.dao;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.Users;

public interface UserDAO {

	void add(Users user);

	Users findRoleByUserName(String userName);

	public void updatePassword(Users user);

	Users findByUserEmail(String email);
	
	public void deleteUser(int id);
	
	public Users findById(Integer Id);
}
