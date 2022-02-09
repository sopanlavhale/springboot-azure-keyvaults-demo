package com.azure.keyvaluts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.azure.keyvaluts.entity.SymmetricUser;

@Repository
public interface SymmetricUserRepository extends MongoRepository<SymmetricUser,String> {

}
