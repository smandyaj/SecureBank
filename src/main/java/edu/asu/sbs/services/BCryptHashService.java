package edu.asu.sbs.services;

import org.springframework.stereotype.Service;


public interface BCryptHashService {

	String getBCryptHash(String plaintext);
	
	boolean checkBCryptHash(String plaintext, String hash);
}