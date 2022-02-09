package com.azure.keyvaluts.controller;


import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component("rsaEncrytionDecrytion")
public class RsaEncrytionDecrytion {

	
	public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
		byte[] bytes = Base64.getDecoder().decode(cipherText);

		Cipher decriptCipher = Cipher.getInstance("RSA");
		decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		return new String(decriptCipher.doFinal(bytes), UTF_8);
	}

	
	public static KeyPair getKeyPairFromKeyStore(Resource resource, String password, String alias,
			String keystoreType) throws Exception {
		// Generated with:
		// keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg
		// RSA -keystore keystore.jks

		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(resource.getInputStream(), password.toCharArray());

		// Check - key exists in keystore
		if (!keyStore.containsAlias(alias)) {
			System.out.println("Alias: " + alias + " is not present in the keystore");
			return null;
		}

//  InputStream ins = RsaExample.class.getResourceAsStream("/keystore.jks");
//
//  KeyStore keyStore = KeyStore.getInstance("PKCS12");
//  keyStore.load(ins, "Password@1".toCharArray());   //Keystore password
		KeyStore.PasswordProtection keyPassword = // Key password
				new KeyStore.PasswordProtection(password.toCharArray());

		KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, keyPassword);

		java.security.cert.Certificate cert = keyStore.getCertificate(alias);
		PublicKey publicKey = cert.getPublicKey();
		PrivateKey privateKey = privateKeyEntry.getPrivateKey();

		return new KeyPair(publicKey, privateKey);
	}

}
