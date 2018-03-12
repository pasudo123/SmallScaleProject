package edu.doubler.login.service;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

class SHA256Encrytor {
	
	// SHA256 암호화
	// reference : https://www.quickprogrammingtips.com/java/how-to-generate-sha256-hash-in-java.html
	/**
     * Returns a hexadecimal encoded SHA-256 hash for the input String.
     * @param data
     * @return 
     */
	private String makeEncryptOnSHA256(String plainText) {
		
		String result = null;
		
		try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainText.getBytes("UTF-8"));
            return bytesToHex(hash); // make it printable
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
	}
	
	
	/**
     * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
     * to a hexadecimal string. Note that this generates hexadecimal in upper case.
     * @param hash
     * @return 
     */
	private String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}
