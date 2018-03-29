/**
 * 
 */
package edu.asu.sbs.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import edu.asu.sbs.model.Otp;

/**
 * @author SUNIL NEKKANTI
 *
 */
public interface OTPService {

	Long generateOTP(int length)
			throws NoSuchAlgorithmException, InvalidKeyException;

	int computeChecksum(long checksumNumber, int significantDigits)
			throws NoSuchAlgorithmException, InvalidKeyException;

	byte[] hmac_sha512(byte[] hmacbytes, byte[] authenticateText)
			throws NoSuchAlgorithmException, InvalidKeyException;

	Otp getOTPByCustomerIDAndType(int customerId, String type);

	public Otp getOTP(String id);

	boolean isOTPVerified(Otp dbOTP, String otp, int transactionId,
			String type);
	
	
	public int addOTP(Otp otp);
	

}