package com.ritik.journal.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ritik.journal.entity.User;


public interface UserRepository extends MongoRepository<User,ObjectId> {
    User findByUserName(String userName);
    User deleteByUserName(String userName);
}