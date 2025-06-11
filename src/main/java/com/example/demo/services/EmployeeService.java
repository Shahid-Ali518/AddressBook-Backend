package com.example.demo.services;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRespository employeeRespository;

    // provided by spring to interact with mongodb with flexible requirements
    @Autowired
    private MongoTemplate mongoTemplate;

    List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees (){
        Query query = new Query();
        // find employees with lastname is zafar
//        query.addCriteria(Criteria.where("lastName").is("zafar"));

        query.addCriteria(Criteria.where("age").gt(18));
        query.addCriteria(Criteria.where("email").is(null));
        employees = mongoTemplate.find(query, Employee.class);
//        employees = employeeRespository.findAll();
        return employees;
    }

    // method to save employee data
    public void AddEmployee(Employee employee){
        try{
            employeeRespository.save(employee);
        }
        catch(Exception e){
            throw new RuntimeException("Error While Saving Employee");
        }

    }
}
