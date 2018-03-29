package edu.asu.sbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.sbs.dao.UserDAO;
import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.Users;
import edu.asu.sbs.services.BCryptHashService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	BCryptHashService bCryptHashService;
	
	@Autowired
	UserDAO userDAO;

	@Override
	public Users findByEmail(String email_id) {
		// TODO Auto-generated method stub
		return userDAO.findByUserEmail(email_id);
	}

	public void updatePassword(UserService userService,String email, String password) {
		System.out.println("One Time Password" + password);
		password = bCryptHashService.getBCryptHash(password);
		Users user = userService.findByEmail(email);
		user.setPassword(password);
		userDAO.updatePassword(user);
	}

}
