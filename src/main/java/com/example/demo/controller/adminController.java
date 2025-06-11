package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.userRepository;
import com.example.demo.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private userService userservice;


    @Autowired
    private userRepository userReop;


    // method to get all persons
    @GetMapping("/all-users")
    public ResponseEntity<?> getallUsers() { // ? is wild card used for any return type
        List<User> all = userservice.getAll();
        if(all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>("No recode found", HttpStatus.NOT_FOUND);

    }

    // method to get a person by id using url varibale
    @GetMapping("/user-by-id/{myid}")
    public ResponseEntity<?> getById(@PathVariable String myid) {
        Optional<User> user = userservice.getuserById(myid); // optional if found return else return null
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK); // return entity with code
        return new ResponseEntity<>("No record Found", HttpStatus.NOT_FOUND); // return only httpstatus code
    }

    // done with spring security
    // method to delete user using username
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userReop.deleteById(id);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
    }


    // method to update person info
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User old = userservice.findUserByUsername(username);

        old.setUsername(user.getUsername());
        old.setPassword(user.getPassword());
        userservice.addUser(old);

        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);

    }


}
