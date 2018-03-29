/**
 * 
 */
package edu.asu.sbs.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.sbs.dao.OtpDAO;
import edu.asu.sbs.model.Otp;

/**
 * @author SUNIL NEKKANTI
 *
 *         USAGE : To generate OTP invoke the generateOTP method with its
 *         arguments secretcode - sessionID or shared secret between server and
 *         client (not a constant), dynamicNumber - a dynamic number which
 *         changes on a per use basis like time or counter, otpLength - length
 *         of the OTP which can be 8 digits max, checksumFlag - tells if
 *         checksum needs to be added to OTP, terminalOffset - Offset into MAC
 *         to begin truncation. It needs to be dynamic on a per use basis (can
 *         use the dynamicNumber argument value)
 * 
 *         Example invocation : OTPServiceImpl.generateOTP(
 *         "+&*#(?b^&VHJBHJK064842348rdsgdsg&*(%&**&^&((", 20, 8, false, 20);
 * 
 */
@Transactional
@Service("OTPService")
public class OTPServiceImpl implements OTPService {

	@Autowired
	SessionFactory sessionFactory;

	private static final int[] allDigits = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
	private static final int[] EXPONENT = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

	@Autowired
	private OtpDAO otpDao;

	@Autowired
	private BCryptHashService hashService;

	public int computeChecksum(long checksumNumber, int significantDigits) {
		boolean doubleDigit = true;
		int total = 0;
		while (0 < significantDigits--) {
			int digit = (int) (checksumNumber % 10);
			checksumNumber /= 10;
			if (doubleDigit) {
				digit = allDigits[digit];
			}
			total += digit;
			doubleDigit = !doubleDigit;
		}
		int result = total % 10;
		if (result > 0) {
			result = 10 - result;
		}
		return result;
	}

	public byte[] hmac_sha512(byte[] hmacbytes, byte[] authenticateText)
			throws NoSuchAlgorithmException, InvalidKeyException {

		Mac hmacSha512;
		try {
			hmacSha512 = Mac.getInstance("HmacSHA512");
		} catch (NoSuchAlgorithmException nsae) {
			hmacSha512 = Mac.getInstance("HMAC-SHA-512");
		}
		SecretKeySpec macKey = new SecretKeySpec(hmacbytes, "RAW");
		hmacSha512.init(macKey);
		return hmacSha512.doFinal(authenticateText);
	}

	public Otp getOTPByCustomerIDAndType(int customerId, String type) {
		return otpDao.getOTPByCustomerIDAndType(customerId, type);
	}

	public Otp getOTP(String id) {

		if (id == null)
			return null;

		return otpDao.getOTPByID(id);
	}

	public boolean isOTPVerified(Otp dbOTP, String otp, int transactionId, String type) {
		if (otp != null && transactionId != 0 && type != null) {

			// check if OPT and type match and whether the transaction id is the
			// same as the request
			if (dbOTP != null && hashService.checkBCryptHash(otp, dbOTP.getOtp())
					&& dbOTP.getType().equalsIgnoreCase(type) && (dbOTP.getTransactionId() == transactionId)) {

				return true;

			}
		}
		return false;
	}

	@Override
	public Long generateOTP(int length) throws NoSuchAlgorithmException, InvalidKeyException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Random ran = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (ran.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (ran.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	@Override
	public int addOTP(Otp otp) {
		return (int) sessionFactory.getCurrentSession().save(otp);
	}

}