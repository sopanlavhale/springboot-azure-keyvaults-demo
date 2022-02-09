package com.azure.keyvaluts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.azure.keyvaluts.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

}
