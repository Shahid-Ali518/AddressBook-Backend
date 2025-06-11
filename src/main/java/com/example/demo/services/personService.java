package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Person;
import com.example.demo.repository.personRepository;

@Component
public class personService {

    @Autowired
    private personRepository personrepo ;

    @Autowired
    private userService userservice;

    // service method to add a person on mongoDB
//     @Transactional
    public void addPerson(Person person, String username)
    {
       try{
           User user = userservice.findUserByUsername(username);
           user.getPersons().add(person);
           userservice.addUser(user);
       }
       catch (Exception e){
           System.out.printf(e.getMessage());
           throw new RuntimeException("An error occurs while saving person", e);
       }

    }

    // service method to add a person on mongoDB
    public void addPerson(Person person)
    {
        personrepo.save(person);
    }

    // service method to retrieve all persons in person collections
    public List<Person> getAll()
    {
        return personrepo.findAll(); // pre-defined method in MongoRepository 
    }

    // service method to find a person by id
    public Optional<Person> getpersonById(String id)
    {
        return personrepo.findById(id);
       
    }

    // service method to delete a person by id
    public int deleteById(String id, String username)
    {
        User user = userservice.findUserByUsername(username);
        user.getPersons().removeIf(x -> x.getId().equals(id)); //lamda experssion

            userservice.addUser(user);
            personrepo.deleteById(id);
        return personrepo.existsById(id) ? -1 : 1;
    }

    // service method to update a person by id
    public Optional<Person> updateById(String id)
    {
        return personrepo.findById(id);

    }


}
