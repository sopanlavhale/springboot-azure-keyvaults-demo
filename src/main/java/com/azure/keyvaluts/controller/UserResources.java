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

  // @Autowired
  // private UserRepository userRepository;

  @Value("${server.ssl.key-store-type}")
  private String storeType;

  @Value("${server.ssl.key-store}")
  private String storePath;

  @Value("${server.ssl.key-store-password}")
  private String password;

  @Value("${server.ssl.key-alias}")
  private String alias;

  @Autowired
  private ResourceLoader resourceLoader;
  //
  // @Autowired
  // private AzureConfiguration azureConfiguration;
  //
  @Autowired
  private UserDetailsRepository userDetailsRepository;

  @Value("${encryptionkey}")
  private String keys;
  
  @Value("${symmetric-key}")
  private String symmetrickey;
  
  private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
  private static final String AES = "AES";

  public ResponseEntity saveEncryptedData(UserRequest userRequest) {
    try {
      KeyPair keyPair = RsaEncrytionDecrytion.getKeyPairFromKeyStore(
          resourceLoader.getResource(storePath), password, alias, storeType);
      
      System.out.println("encyption key is " + keys);
      System.out.println("symmetrickey key is " + symmetrickey);
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
          user.setLastName(encyptedValue);
          userDetails.setLastName(encyptedValue);
        } else {
          userDetails.setLastName(user.getLastName());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.EMAIL.value())) {
          String rawValue = user.getEmail();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          user.setEmail(encyptedValue);
          userDetails.setEmail(encyptedValue);
        } else {
          userDetails.setEmail(user.getEmail());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.MOBILE.value())) {
          String rawValue = user.getMobile();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          user.setMobile(encyptedValue);
          userDetails.setMobile(encyptedValue);
        } else {
          userDetails.setMobile(user.getMobile());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PASSWORD.value())) {
          String rawValue = user.getPassword();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          user.setPassword(encyptedValue);
          userDetails.setPassword(encyptedValue);
        } else {
          userDetails.setPassword(user.getPassword());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.ACCOUNTNUMBER.value())) {
          String rawValue = user.getAccountNumber();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          user.setAccountNumber(encyptedValue);
          userDetails.setAccountNumber(encyptedValue);
        } else {
          userDetails.setAccountNumber(user.getAccountNumber());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.CARDNUMBER.value())) {
          String rawValue = user.getCardNumber();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          user.setCardNumber(encyptedValue);
          userDetails.setCardNumber(encyptedValue);
        } else {
          userDetails.setCardNumber(user.getCardNumber());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PIN.value())) {
          String rawValue = user.getPin();
          String encyptedValue = RsaEncrytionDecrytion.encrypt(rawValue, keyPair.getPublic());
          user.setPin(encyptedValue);
          userDetails.setPin(encyptedValue);
        } else {
          userDetails.setPin(user.getPin());
        }


        if (this.userDetailsRepository.save(userDetails) == null) {
          System.out.println("Failed to save");
        }

      }
      // TODO - > we can save to db also

      // n time

      return ResponseEntity.ok().body(userRequest);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  // public ResponseEntity getUsers() {
  // List<User> users = this.userRepository.findAll();
  // if(!CollectionUtils.isEmpty(users)) {
  // return ResponseEntity.ok().body(users);
  // }
  // return null;
  // }

  public ResponseEntity getDecryptedPayloadData(String encryptedpayload) {
    try {
      KeyPair keyPair = RsaEncrytionDecrytion.getKeyPairFromKeyStore(
          resourceLoader.getResource(storePath), password, alias, storeType);
      String plainTextData = RsaEncrytionDecrytion.decrypt(encryptedpayload, keyPair.getPrivate());
      return ResponseEntity.ok().body(plainTextData);
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
