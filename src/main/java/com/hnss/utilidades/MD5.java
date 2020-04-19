package com.hnss.utilidades;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MD5 {

	private static final Logger logger = LogManager.getLogger(MD5.class);

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			// LOG
			logger.error("Error md5", e);
			throw new RuntimeException(e);
		}
	}

	public static String SHA1(String S) throws NoSuchAlgorithmException {
		byte[] B = S.getBytes();
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(B);
		byte[] H = md.digest();
		String result = "";
		for (byte h : H) {
			int b = h & 0xff;
			if (b < 16)
				result += "0";
			result += Integer.toHexString(b);
		}
		return result;
	}

}
