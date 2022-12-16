package com.codedecode.microservices.RegisterService.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenUtil {

	public static final String MAGIC_KEY = "YogendraKey";

	public static String createToken(UserDetails userDetails) {
		long expires = System.currentTimeMillis() + 1000L * 60 * 60;
		return userDetails.getUsername() + ":" + expires + ":" + computeSignature(userDetails, expires);
	}

	public static String computeSignature(UserDetails userDetails, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		//signatureBuilder.append(userDetails.getUsername()).append(":");
		//signatureBuilder.append(expires).append(":");
		signatureBuilder.append(userDetails.getPassword()).append(":");
		signatureBuilder.append(TokenUtil.MAGIC_KEY);

		/*
		 * MessageDigest digest;
		 * 
		 * 
		 * try { digest = MessageDigest.getInstance("MD5"); } catch
		 * (NoSuchAlgorithmException e) { throw new
		 * IllegalStateException("No MD5 algorithm available!"); }
		 */
		return signatureBuilder.toString();
		//return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}

	public static String getUserNameFromToken(String authToken) {
		if (authToken == null) {
			return null;
		}
		String[] parts = authToken.split(":");
		return parts[0];
	}

	public static boolean validateToken(String authToken, UserDetails userDetails) {
		System.out.println("code inside here here"+authToken);
		String[] parts = authToken.split(":");
		for(String s: parts)
		{
			System.out.println(s);
		}
		long expires = Long.parseLong(parts[1]);
		System.out.println("expires-->"+expires);
		String signature = parts[2].concat(":").concat(MAGIC_KEY);
		System.out.println("signature-->"+signature);
		String signatureToMatch = computeSignature(userDetails, expires);
		System.out.println("signatureToMatch-->"+signatureToMatch);
		System.out.println("-->"+System.currentTimeMillis());
		System.out.println(expires >= System.currentTimeMillis());
		boolean value = expires >= System.currentTimeMillis() && signature.equals(signatureToMatch);
		System.out.println("token is valid::::--->"+value);
		return value;
	}
}
