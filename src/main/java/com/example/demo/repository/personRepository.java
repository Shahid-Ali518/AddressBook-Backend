package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Person;

public interface personRepository extends MongoRepository<Person, String> {

}
