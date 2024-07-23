package com.employee.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class PasswordUtility {

	/**
	 * Hashes the given plain text password.
	 *
	 * @param plainPassword The plain text password.
	 * @return The hashed password.
	 */
	public static String hashPassword(String plainPassword) {
		// Hash the plain text password
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] hash = null;
		try {
			hash = digest.digest(plainPassword.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Hex.encodeHexString(hash);
	}

	public static boolean checkPassword(String password, String hashedPassword) {
		// TODO Auto-generated method stub
		if (hashPassword(password).contentEquals(hashedPassword)) {
			return true;
		} else {
			return false;
		}
	}
}
