package edu.asu.sbs.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
        System.out.println(encoder.encode("manager"));
        //System.out.println(encoder.encode("smandyaj4"));
	}

}
