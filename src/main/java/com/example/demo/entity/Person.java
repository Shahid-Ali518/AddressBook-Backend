package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document(collection="person")
@Getter
@Setter // this annotation is used with the help of lombok project
@Data // this contains all annotaions like getter, setter, all constructors and so on..

public class Person 
{
    @Id
    private String id;

    private String name;
    private String phone;
    private String address;

  
    


}
