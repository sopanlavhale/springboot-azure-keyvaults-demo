package com.azure.keyvaluts.service;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.azure.keyvaluts.common.AlgoScheme;
import com.azure.keyvaluts.common.DBConstants;
import com.azure.keyvaluts.common.Encryption;
import com.azure.keyvaluts.common.SupportedEncoding;
import com.azure.keyvaluts.entity.SymmetricUser;
import com.azure.keyvaluts.model.DataValues;
import com.azure.keyvaluts.model.UserRequest;
import com.azure.keyvaluts.repository.SymmetricUserRepository;


@Component("symmtricResources")
public class SymmtricResources {

  @Autowired
  private SymmetricUserRepository symmetricUserRepository;

  @Value("${spring-azure-symmetric-key}")
  private String keys;

  AlgoScheme encryptalgo = AlgoScheme.AES_CBC_PKCS5PADDING;
  String encodealgo = SupportedEncoding.BASE64.toString();
      
  public ResponseEntity saveEncryptedData(UserRequest userRequest) {
    try {
       Encryption encryption = new Encryption();
      System.out.println("encyption key is " + keys);
      String iv=keys;
    List<SymmetricUser> symmetricList = new LinkedList<SymmetricUser>();
      for (DataValues user : userRequest.getDataValues()) { // O(n)
        SymmetricUser userDetails = new SymmetricUser();
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.FIRSTNAME.value())) {
          String rawValue = user.getFirstName();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setFirstName(encyptedValue);
        } else {
          userDetails.setFirstName(user.getFirstName());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.LASTNAME.value())) {
          String rawValue = user.getLastName();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setLastName(encyptedValue);
        } else {
          userDetails.setLastName(user.getLastName());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.EMAIL.value())) {
          String rawValue = user.getEmail();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setEmail(encyptedValue);
        } else {
          userDetails.setEmail(user.getEmail());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.MOBILE.value())) {
          String rawValue = user.getMobile();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setMobile(encyptedValue);
        } else {
          userDetails.setMobile(user.getMobile());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PASSWORD.value())) {
          String rawValue = user.getPassword();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setPassword(encyptedValue);
        } else {
          userDetails.setPassword(user.getPassword());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.ACCOUNTNUMBER.value())) {
          String rawValue = user.getAccountNumber();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setAccountNumber(encyptedValue);
        } else {
          userDetails.setAccountNumber(user.getAccountNumber());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.CARDNUMBER.value())) {
          String rawValue = user.getCardNumber();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setCardNumber(encyptedValue);
        } else {
          userDetails.setCardNumber(user.getCardNumber());
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PIN.value())) {
          String rawValue = user.getPin();
          String encyptedValue = encryption.encryptData(rawValue, keys.getBytes(), iv.getBytes(), encodealgo, encryptalgo);
          userDetails.setPin(encyptedValue);
        } else {
          userDetails.setPin(user.getPin());
        }
        symmetricList.add(userDetails);
        // use batch processing spring for large data to save.
        if (this.symmetricUserRepository.save(userDetails) == null) {
          System.out.println("Failed to save");
        }

      }

      return ResponseEntity.ok().body(symmetricList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public ResponseEntity decryptData(UserRequest userRequest) {
    try {
      Encryption encryption = new Encryption();
      List<SymmetricUser> userDetailsList = new LinkedList<SymmetricUser>();
      for (DataValues user : userRequest.getDataValues()) {
        SymmetricUser userDetails = new SymmetricUser();
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.FIRSTNAME.value())) {
          String encyptedValue = user.getFirstName();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setFirstName(rawValue);
        } else {
          userDetails
              .setFirstName(StringUtils.isNotEmpty(user.getFirstName()) ? user.getFirstName() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.LASTNAME.value())) {
          String encyptedValue = user.getLastName();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setLastName(rawValue);
        } else {
          userDetails
              .setLastName(StringUtils.isNotEmpty(user.getLastName()) ? user.getLastName() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.EMAIL.value())) {
          String encyptedValue = user.getEmail();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setEmail(rawValue);
        } else {
          userDetails.setEmail(StringUtils.isNotEmpty(user.getEmail()) ? user.getEmail() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.MOBILE.value())) {
          String encyptedValue = user.getMobile();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setMobile(rawValue);
        } else {
          userDetails.setMobile(StringUtils.isNotEmpty(user.getMobile()) ? user.getMobile() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PASSWORD.value())) {
          String encyptedValue = user.getPassword();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setPassword(rawValue);
        } else {
          userDetails
              .setPassword(StringUtils.isNotEmpty(user.getPassword()) ? user.getPassword() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.ACCOUNTNUMBER.value())) {
          String encyptedValue = user.getAccountNumber();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setAccountNumber(rawValue);
        } else {
          userDetails.setAccountNumber(
              StringUtils.isNotEmpty(user.getAccountNumber()) ? user.getAccountNumber() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.CARDNUMBER.value())) {
          String encyptedValue = user.getCardNumber();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
          userDetails.setCardNumber(rawValue);
        } else {
          userDetails.setCardNumber(
              StringUtils.isNotEmpty(user.getCardNumber()) ? user.getCardNumber() : "");
        }
        if (userRequest.getEncryptdKey().contains(DBConstants.FieldsName.PIN.value())) {
          String encyptedValue = user.getPin();
          String rawValue = encryption.decryptData(encyptedValue, keys.getBytes(), keys, encodealgo, encryptalgo);
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
