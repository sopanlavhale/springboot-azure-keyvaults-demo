package com.azure.keyvaluts.common;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.stereotype.Component;

@Component("encryptedPropertiesUtils")
public class EncryptedPropertiesUtils {

  private static String ALGO = "PBEWithMD5AndDES";
  private PooledPBEStringEncryptor encryptor = null;

  public EncryptedPropertiesUtils() {

  }

  public EncryptedPropertiesUtils(String rootPassword) {
    encryptor = new PooledPBEStringEncryptor();
    encryptor.setPoolSize(4);
    encryptor.setPassword(rootPassword);
    encryptor.setAlgorithm(ALGO);
  }

  public String encrypt(String input) {
    try {
      if (encryptor == null)
        return input;

      return encryptor.encrypt(input);
    } catch (Exception e) {
    }
    return input;
  }

  public String decrypt(String encryptedMessage) {
    try {
      if (encryptor == null)
        return encryptedMessage;

      return encryptor.decrypt(encryptedMessage);
    } catch (Exception e) {
    }
    return encryptedMessage;
  }
}
