package com.azure.keyvaluts.controller;


import java.security.KeyPair;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.azure.keyvaluts.common.DBConstants;
import com.azure.keyvaluts.entity.UserDetails;
import com.azure.keyvaluts.model.DataValues;
import com.azure.keyvaluts.model.UserRequest;
import com.azure.keyvaluts.repository.UserDetailsRepository;

@Component("userResources")
public class UserResources {

  @Value("${com.azure.keyvaults.keyStoreType}")
  private String storeType;

  @Value("${com.azure.keyvaults.keyStorePath}")
  private String storePath;

  @Value("${com.azure.keyvaults.password}")
  private String password;

  @Value("${com.azure.keyvaults.alias}")
  private String alias;

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private UserDetailsRepository userDetailsRepository;

  public ResponseEntity saveEncryptedData(UserRequest userRequest) {
    try {
      KeyPair keyPair = RsaEncrytionDecrytion.getKeyPairFromKeyStore(
          resourceLoader.getResource(storePath), password, alias, storeType);
      List<UserDetails> userList = new LinkedList<UserDetails>();
      for (DataValues user : userRequest.getDataValues()) { // O(n)
        UserDetails userDetails = new UserDetails();
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.FIRSTNAME.value())) {
          String rawValue = user.getFirstName();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setFirstName(encyptedValue);
        } else {
          userDetails.setFirstName(user.getFirstName());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.LASTNAME.value())) {
          String rawValue = user.getLastName();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setLastName(encyptedValue);
        } else {
          userDetails.setLastName(user.getLastName());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.EMAIL.value())) {
          String rawValue = user.getEmail();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setEmail(encyptedValue);
        } else {
          userDetails.setEmail(user.getEmail());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.MOBILE.value())) {
          String rawValue = user.getMobile();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setMobile(encyptedValue);
        } else {
          userDetails.setMobile(user.getMobile());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PASSWORD.value())) {
          String rawValue = user.getPassword();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setPassword(encyptedValue);
        } else {
          userDetails.setPassword(user.getPassword());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.ACCOUNTNUMBER.value())) {
          String rawValue = user.getAccountNumber();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setAccountNumber(encyptedValue);
        } else {
          userDetails.setAccountNumber(user.getAccountNumber());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.CARDNUMBER.value())) {
          String rawValue = user.getCardNumber();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setCardNumber(encyptedValue);
        } else {
          userDetails.setCardNumber(user.getCardNumber());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PIN.value())) {
          String rawValue = user.getPin();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          userDetails.setPin(encyptedValue);
        } else {
          userDetails.setPin(user.getPin());
        }

        userList.add(userDetails);
        if (this.userDetailsRepository.save(userDetails) == null) {
          System.out.println("Failed to save");
        }

      }
      return ResponseEntity.ok().body(userList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public ResponseEntity decryptData(UserRequest userRequest) {
    try {
      KeyPair keyPair = RsaEncrytionDecrytion.getKeyPairFromKeyStore(
          resourceLoader.getResource(storePath), password, alias, storeType);
      List<UserDetails> userDetailsList = new LinkedList<UserDetails>();
      for (DataValues user : userRequest.getDataValues()) {
        UserDetails userDetails = new UserDetails();
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.FIRSTNAME.value())) {
          String decryptedValue = user.getFirstName();
          String rawValue = RsaEncrytionDecrytion.decrypt(decryptedValue, keyPair.getPrivate());
          userDetails.setFirstName(rawValue);
        } else {
          userDetails
              .setFirstName(StringUtils.isNotEmpty(user.getFirstName()) ? user.getFirstName() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.LASTNAME.value())) {
          String encyptedValue = user.getLastName();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setLastName(rawValue);
        } else {
          userDetails
              .setLastName(StringUtils.isNotEmpty(user.getLastName()) ? user.getLastName() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.EMAIL.value())) {
          String encyptedValue = user.getEmail();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setEmail(rawValue);
        } else {
          userDetails.setEmail(StringUtils.isNotEmpty(user.getEmail()) ? user.getEmail() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.MOBILE.value())) {
          String encyptedValue = user.getMobile();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setMobile(rawValue);
        } else {
          userDetails.setMobile(StringUtils.isNotEmpty(user.getMobile()) ? user.getMobile() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PASSWORD.value())) {
          String encyptedValue = user.getPassword();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setPassword(rawValue);
        } else {
          userDetails
              .setPassword(StringUtils.isNotEmpty(user.getPassword()) ? user.getPassword() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.ACCOUNTNUMBER.value())) {
          String encyptedValue = user.getAccountNumber();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setAccountNumber(rawValue);
        } else {
          userDetails.setAccountNumber(
              StringUtils.isNotEmpty(user.getAccountNumber()) ? user.getAccountNumber() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.CARDNUMBER.value())) {
          String encyptedValue = user.getCardNumber();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setCardNumber(rawValue);
        } else {
          userDetails.setCardNumber(
              StringUtils.isNotEmpty(user.getCardNumber()) ? user.getCardNumber() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PIN.value())) {
          String encyptedValue = user.getPin();
          String rawValue = RsaEncrytionDecrytion.decrypt(encyptedValue, keyPair.getPrivate());
          userDetails.setPin(rawValue);
        } else {
          userDetails.setPin(StringUtils.isNotEmpty(user.getPin()) ? user.getPin() : "");
        }
        userDetailsList.add(userDetails);
      }
      return ResponseEntity.ok().body(userDetailsList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

}
