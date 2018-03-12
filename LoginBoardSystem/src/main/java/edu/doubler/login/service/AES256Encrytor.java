package edu.doubler.login.service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * reference : https://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example
 * 
 * **/
class EncryptorOnAES256 {
	
	private String key = "12345678901234567890123456789012";	// 
	private String initVector = "RandomInitVector";				// 16 bytes IV	
	
	// 암호화
	public String getEncryptText(String plainText){
		return encrypt(key, initVector, plainText);
	}
	
	// 복호화
	public String getDecryptText(String chiperText){
		return decrypt(key, initVector, chiperText);
	}
	
	// -- [AES256 암호화]
	private String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
	
	// -- [AES256 복호화]
	private String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
