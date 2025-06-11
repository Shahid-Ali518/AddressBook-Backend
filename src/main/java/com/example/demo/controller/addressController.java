package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;
import com.example.demo.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.entity.Person;
import com.example.demo.services.personService;



@RestController
@RequestMapping("/person")
public class addressController {

    @Autowired
    private personService personservice;

    @Autowired
    private userService userservice;
    @GetMapping("/home")
    public String getMethodName() {
        return "redirect:/home.html";
    }

    // method to get all persons
    @GetMapping("/all-persons")
    public ResponseEntity<?> getallofUser() { // ? is wild card used for any return type
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userservice.findUserByUsername(username);
        List<Person> all = user.getPersons();
        if(all != null && !all.isEmpty())
            return new ResponseEntity<>( all, HttpStatus.OK);
        return new ResponseEntity<>("No record found", HttpStatus.NOT_FOUND);

    }

    // method to get a person by id using url varibale
    @GetMapping("/person-by-id/{myid}")
    public ResponseEntity<?> getById(@PathVariable String myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userservice.findUserByUsername(username);
        List<Person> persons = user.getPersons();
        if(persons != null && !persons.isEmpty()){
            Optional<Person> person = personservice.getpersonById(myid); // optional if found return else return null
            if (person.isPresent())
                return new ResponseEntity<>(persons, HttpStatus.OK); // return entity with code

        }
        return new ResponseEntity<>("No record Found", HttpStatus.NOT_FOUND); // return only httpstatus code
    }

    // method to delete a person by id using url varibale
    @DeleteMapping("/delete-by-id/{myid}")
    public ResponseEntity<?> deleteById(@PathVariable String myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (personservice.deleteById(myid, username) > 0)
            return new ResponseEntity<>("Person by " + myid + " deleted successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Person does not exist in Database", HttpStatus.NOT_FOUND);
    }

    // method to update person info
    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable String id, @RequestBody Person entity) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userservice.findUserByUsername(username);
        Person old = personservice.getpersonById(id).orElse(null);
        if (old != null) {
            old.setAddress((!entity.getAddress().equals("") && entity.getAddress() != null) ? entity.getAddress()
                    : old.getAddress());
            old.setName((!entity.getName().equals("") && entity.getName() != null) ? entity.getName() : old.getName());
            old.setPhone((!entity.getPhone().equals("") && entity.getPhone() != null) ? entity.getPhone() : old.getPhone());

            personservice.addPerson(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>("No record Found or Invalid Input", HttpStatus.NOT_FOUND);
    }

    // method to add person using json format using postman software
    @PostMapping("/add-person")
    public ResponseEntity<String> addPerson(@RequestBody Person per) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            personservice.addPerson(per, username);
            String message = "Data inserted successfully";
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);

        }
        
    }

}
