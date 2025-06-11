package com.example.demo.entity;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;

// this is class not related to address book for practice of mongotemplate
@Component
@Document(collection ="employee")
@Getter
@Setter
public class Employee {

    @Id
    ObjectId id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int age;
    private String department;
    private Date joiningDate;

}
