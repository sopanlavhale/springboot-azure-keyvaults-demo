package com.azure.keyvaluts.common;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	Encoding encoding = new Encoding();

	/**
	 * Check for the correct lenght of Iv. Encrypt the input data using key and
	 * Iv(required) and return the encrypted data after encoding it.
	 * 
	 * @param unencryptedData
	 * @param key
	 * @param ivInput
	 * @param encodealgo
	 * @param encryptalgo
	 * @return
	 * @throws Exception
	 */
	public String encryptData(String unencryptedData, byte[] key, byte[] ivInput, String encodealgo,
			AlgoScheme encryptalgo) throws Exception {

		// For CBC
		if (encryptalgo.toString().contains("CBC")) {
			SecureRandom random = new SecureRandom();
			byte[] unencryptedDataByte = unencryptedData.getBytes();

			// key lenght check
			if (key == null || key.length != encryptalgo.getKeyLen()) {
				System.out.println("key bit length is not " + encryptalgo.getKeyLen());
				return null;
			}

			// Iv lenght check
			if (ivInput == null || ivInput.length != encryptalgo.getIvLen()) {
				System.out.println("Iv's bit length is not " + encryptalgo.getIvLen());
				return null;
			}

			// Generating IV
			int ivSize = ivInput.length;
			byte[] iv = new byte[ivSize];
			random.nextBytes(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

			try {
				SecretKeySpec skeySpec = new SecretKeySpec(key, encryptalgo.getAlgoName());
				Cipher cipher = Cipher.getInstance(encryptalgo.getScheme());
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
				byte[] encryptedData = cipher.doFinal(unencryptedDataByte);

				// Combine IV and encrypted part
				byte[] encryptedDataIVAndRequest = new byte[ivSize + encryptedData.length];
				System.arraycopy(iv, 0, encryptedDataIVAndRequest, 0, ivSize);
				System.arraycopy(encryptedData, 0, encryptedDataIVAndRequest, ivSize, encryptedData.length);
				return encoding.encode(encryptedDataIVAndRequest, encodealgo);
			} catch (Exception ex) {
				System.out.println("Exception found while encrypting data");
				ex.printStackTrace();
			}
			// For ECB
		} else if (encryptalgo.toString().contains("ECB")) {

			// key lenght check
			if (key == null || key.length != encryptalgo.getKeyLen()) {
				System.out.println("key bit length is not " + encryptalgo.getKeyLen());
				return null;
			}

			byte[] unencryptedDataByte = unencryptedData.getBytes();
			try {
				SecretKeySpec skeySpec = new SecretKeySpec(key, encryptalgo.getAlgoName());
				Cipher cipher = Cipher.getInstance(encryptalgo.getScheme());
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				byte[] encryptedData = cipher.doFinal(unencryptedDataByte);

				return encoding.encode(encryptedData, encodealgo);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Wrong parameters in for encryption algorithm");
		}
		return null;
	}

	/**
	 * Check for the correct lenght of Iv. Decrypt the input data using key and
	 * Iv(required) and return the decrypted data. Before decrypting data it will
	 * decode the value
	 * 
	 * @param encryptedData
	 * @param key
	 * @param initvector
	 * @param encodealgo
	 * @param encryptalgo
	 * @return
	 */
	public String decryptData(String encryptedData, byte[] key, String initvector, String encodealgo,
			AlgoScheme encryptalgo) {

		// For CBC
		if (encryptalgo.toString().contains("CBC")) {

			// key lenght check
			if (key == null || key.length != encryptalgo.getKeyLen()) {
				System.out.println("key bit length is not " + encryptalgo.getKeyLen());
				return null;
			}

			// Iv lenght check
			if (initvector == null || initvector.length() != encryptalgo.getIvLen()) {
				System.out.println("Iv's bit length is not " + encryptalgo.getIvLen());
				return null;
			}

			try {
				byte[] encryptedDatabytes = encoding.decode(encryptedData, encodealgo);
				int ivSize = encryptalgo.getIvLen();

				// Extract IV
				byte[] iv = new byte[ivSize];
				System.arraycopy(encryptedDatabytes, 0, iv, 0, iv.length);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
				SecretKeySpec skeySpec = new SecretKeySpec(key, encryptalgo.getAlgoName());

				// Extract encrypted part
				int encryptedSize = encryptedDatabytes.length - ivSize;
				byte[] encryptedBytes = new byte[encryptedSize];
				System.arraycopy(encryptedDatabytes, ivSize, encryptedBytes, 0, encryptedSize);

				Cipher cipher = Cipher.getInstance(encryptalgo.getScheme());
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
				byte[] decryptedDatabytes = cipher.doFinal(encryptedBytes);

				return new String(decryptedDatabytes);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// For ECB
		} else if (encryptalgo.toString().contains("ECB")) {

			// key lenght check
			if (key == null || key.length != encryptalgo.getKeyLen()) {
				System.out.println("key bit length is not " + encryptalgo.getKeyLen());
				return null;
			}

			try {
				byte[] encryptedDatabytes = encoding.decode(encryptedData, encodealgo);

				SecretKeySpec skeySpec = new SecretKeySpec(key, encryptalgo.getAlgoName());

				// Extract encrypted part
				int encryptedSize = encryptedDatabytes.length;
				byte[] encryptedBytes = new byte[encryptedSize];
				System.arraycopy(encryptedDatabytes, 0, encryptedBytes, 0, encryptedSize);

				Cipher cipher = Cipher.getInstance(encryptalgo.getScheme());
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				byte[] decryptedDatabytes = cipher.doFinal(encryptedBytes);

				return new String(decryptedDatabytes);
			} catch (Exception ex) {
				System.out.println("Exception found while decryption data");
				ex.printStackTrace();
			}
		} else {
			System.out.println("Wrong parameter for encryption algorithm");
		}
		return null;
	}

}
