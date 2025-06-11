package com.example.demo.entity;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection="user")
@Data // this contains all annotaions like getter, setter, all constructors and so on..
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User
{

    @Id
    private String id;

    @NonNull // username should not null, provided by project lombok
    @Indexed(unique = true) // username should unique, fast the database search
    private String username;

    @NonNull // username should not null, provided by project lombok
    private String password;
  
    @DBRef
    private List<Person> persons = new ArrayList<>();

    private List<String> role;
  
}
