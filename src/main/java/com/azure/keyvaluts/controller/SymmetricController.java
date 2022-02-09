package com.azure.keyvaluts.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.azure.keyvaluts.entity.SymmetricUser;
import com.azure.keyvaluts.model.UserRequest;
import com.azure.keyvaluts.repository.SymmetricUserRepository;
import com.azure.keyvaluts.service.SymmtricResources;

@RestController
@RequestMapping("/v1/symmetric")
public class SymmetricController {

  @Autowired
  private SymmtricResources symmtricResources;
  
  @Autowired
  private SymmetricUserRepository symmetricUserRepository;
  
  @PostMapping("/encrypt")
  public ResponseEntity userencryptRequest(@RequestBody UserRequest userRequest) {
      if (userRequest == null) {
          return ResponseEntity.badRequest().body("Empty request payload");
      }
      return this.symmtricResources.saveEncryptedData(userRequest);

  }

  @PostMapping("/decrypt")
  public ResponseEntity userdecryptRequest(@RequestBody UserRequest userRequest) {
      if (userRequest == null) {
          return ResponseEntity.badRequest().body("Empty request payload");
      }
      return this.symmtricResources.decryptData(userRequest);
  }
  
  @GetMapping("/users")
  public List<SymmetricUser> getUserDetails() {
    return this.symmetricUserRepository.findAll();
  }
  
  @GetMapping("/user")
  public Optional<SymmetricUser> getUser(@RequestParam String userId) {
    return this.symmetricUserRepository.findById(userId);
  }
  
}
