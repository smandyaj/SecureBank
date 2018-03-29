package edu.asu.sbs.services;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import edu.asu.sbs.model.Users;

public interface UserService {

	public void updatePassword(UserService userService,String email, String password);

	Users findByEmail(String email_id);
}
