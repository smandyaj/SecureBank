package edu.asu.sbs.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptHashServiceImpl implements BCryptHashService{

	@Override
	public String getBCryptHash(String plaintext) {
		// TODO Auto-generated method stub
		if (plaintext != null && !plaintext.isEmpty()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
	        return encoder.encode(plaintext);
		}
		return plaintext;		
	}

	@Override
	public boolean checkBCryptHash(String plaintext, String hash) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		return encoder.matches(plaintext, hash);
	}

}