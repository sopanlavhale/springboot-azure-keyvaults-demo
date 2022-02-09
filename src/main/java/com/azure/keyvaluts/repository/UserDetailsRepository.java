package com.azure.keyvaluts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.azure.keyvaluts.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails,String> {

}
