package com.example.demo.repository;

import com.example.demo.entity.ConfigEntity;
import com.example.demo.entity.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppConfigRepository extends MongoRepository<ConfigEntity, ObjectId> {

}
