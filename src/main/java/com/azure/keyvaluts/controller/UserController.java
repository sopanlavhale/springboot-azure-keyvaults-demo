package com.azure.keyvaluts.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.azure.keyvaluts.entity.User;
import com.azure.keyvaluts.entity.UserDetails;
import com.azure.keyvaluts.model.UserRequest;
import com.azure.keyvaluts.repository.UserDetailsRepository;
import com.azure.keyvaluts.repository.UserRepository;

@RestController
@RequestMapping("/v1/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserResources userResources;
  
  @Autowired
  private UserDetailsRepository userDetailsRepository;

  @PostMapping("/addUser")
  public User save(@RequestBody User userRequest) {
    return this.userRepository.save(userRequest);
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    return this.userRepository.findAll();
  }
  
  @PostMapping("/encrypt")
  public ResponseEntity userencryptRequest(@RequestBody UserRequest userRequest) {
      if (userRequest == null) {
          return ResponseEntity.badRequest().body("Empty request payload");
      }
      return this.userResources.saveEncryptedData(userRequest);

  }

  @PostMapping("/decrypt")
  public ResponseEntity userdecryptRequest(@RequestBody UserRequest userRequest) {
      if (userRequest == null) {
          return ResponseEntity.badRequest().body("Empty request payload");
      }
      return this.userResources.decryptData(userRequest);

  }
  
  @GetMapping("/userDetails")
  public List<UserDetails> getUserDetails() {
    return this.userDetailsRepository.findAll();
  }
  
}
