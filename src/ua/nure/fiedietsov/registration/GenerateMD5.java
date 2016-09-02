/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
* Support class help's generate MD5 for passwords.
* @author Fiedietsov V.
* @version 1.0
* @param pass is the String password.
* @return MD5 password.
*/
public class GenerateMD5 {
	static String generate(String pass){


		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(pass.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}

		return sb.toString();
	}

}
