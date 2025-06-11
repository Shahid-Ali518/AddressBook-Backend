package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.entity.User;

public interface userRepository extends MongoRepository<User, String> {

    User findByUsername (String username);

    User deleteUserByUsername(String username);
}
