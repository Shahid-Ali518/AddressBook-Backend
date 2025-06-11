package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRespository extends MongoRepository<Employee, ObjectId> {

}
